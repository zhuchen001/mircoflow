package com.center.microflow.domain;

import com.center.microflow.api.ParallelVertex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 并行执行结果（本质上记录一个并行点，同时保存多个ParallelInfo）
 */
public class ParallelExecute<T extends Serializable> {
    private String point;

    private List<ParallelInfo<T>> infoList = new ArrayList<>();

    private Exception mergeException;

    public ParallelExecute() {
    }

    public void clear() {
        this.point = null;
        this.infoList.clear();
        this.mergeException = null;
    }

    /**
     * 是否同样的并行执行
     */
    public boolean isSameParallel(String point) {
        return point.equals(this.point);
    }

    public boolean canMerge() {
        return !infoList.isEmpty();
    }

    public void addParallelInfo(AsynEvent<T> event, ParallelVertex<T> vertex, Future<T> future) {
        infoList.add(new ParallelInfo(event, vertex, future));
    }

    public void merge(T context) {
        if (infoList.isEmpty()) {
            return;
        }

        // 这里多线程获取执行结果,但是并行算子较多的时候timeout实现不是很严格
        infoList.parallelStream().forEach(next -> {
            try {
                next.getFuture().get(next.getVertex().timeout(), TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                setMergeException(e);
                return;
            }

            // merger的时候加context锁
            synchronized (context) {
                next.getVertex().merge(next.getEvent().getBo(), context);
            }
        });
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Exception getMergeException() {
        return mergeException;
    }

    public void setMergeException(Exception mergeException) {
        this.mergeException = mergeException;
    }

    static class ParallelInfo<T extends Serializable> {
        private AsynEvent<T> event;
        private ParallelVertex<T> vertex;
        private Future<T> future;

        public ParallelInfo(AsynEvent<T> event, ParallelVertex<T> vertex, Future<T> future) {
            this.event = event;
            this.vertex = vertex;
            this.future = future;
        }

        public AsynEvent<T> getEvent() {
            return event;
        }

        public void setEvent(AsynEvent<T> event) {
            this.event = event;
        }

        public ParallelVertex<T> getVertex() {
            return vertex;
        }

        public void setVertex(ParallelVertex<T> vertex) {
            this.vertex = vertex;
        }

        public Future<T> getFuture() {
            return future;
        }

        public void setFuture(Future<T> future) {
            this.future = future;
        }
    }
}
