/**
 * 
 */
package com.center.microflow.domain;

import com.center.microflow.api.BaseDefined;
import com.center.microflow.api.IVertex;
import com.center.microflow.api.MicroFlowExceptionChange;
import com.center.microflow.utils.MicroFlowExceptionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class ExecuteResult implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 执行异常
	 */
	private Throwable exception;

	/**
	 * 中断信息
	 */
	private BreakBean breakBean;

	/**
	 * 执行轨迹
	 */
	private List<MicroFlowTrack> vertexTrackList = new ArrayList<>();

	/**
	 * 并行执行结果
	 */
	private ParallelExecute parallelExecute = new ParallelExecute();

	/**
	 * fork执行结果
	 */
	private ParallelExecute forkExecute = new ParallelExecute();

	public ExecuteResult() {
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public BreakBean getBreakBean() {
		return breakBean;
	}

	public void setBreakBean(BreakBean breakBean) {
		this.breakBean = breakBean;
	}

	public List<MicroFlowTrack> getVertexTrackList() {
		return vertexTrackList;
	}

	public void setVertexTrackList(List<MicroFlowTrack> vertexTrackList) {
		this.vertexTrackList = vertexTrackList;
	}

	public ExecuteResult addVertexTrack(MicroFlowTrack track) {
		this.vertexTrackList.add(track);
		return this;
	}

	public ExecuteResult addVertexTrack(IVertex<?> vertex) {
		MicroFlowTrack track = new MicroFlowTrack(vertex.id());
		return addVertexTrack(track);
	}

	public ExecuteResult addVertexTrack(IVertex<?> vertex, long begin) {
		MicroFlowTrack track = new MicroFlowTrack(begin, vertex.id());
		return addVertexTrack(track);
	}

	public ExecuteResult addVertexTrack(IVertex<?> vertex, long begin, String context) {
		MicroFlowTrack track = new MicroFlowTrack(begin, vertex.id(), context);
		return addVertexTrack(track);
	}

	public ExecuteResult initVertexTrack(String context) {
		MicroFlowTrack track = new MicroFlowTrack("init", context);
		return addVertexTrack(track);
	}

	public ExecuteResult addVertexTrack(ExecuteResult result) {
		this.vertexTrackList.addAll(result.getVertexTrackList());
		return this;
	}

	public ExecuteResult addVertexTrack(BaseDefined<?> vertex, Serializable context) {
		MicroFlowTrack track = new MicroFlowTrack(vertex.id(), context.toString());
		return addVertexTrack(track);
	}

	public ParallelExecute getForkExecute() {
		return forkExecute;
	}

	public boolean isFail() {
		return this.exception != null;
	}

	/**
	 * 执行成功的条件是无异常且没有中断
	 */
	public boolean isSuccess()
	{
		return !isFail() && !this.breakBean.isBreak();
	}

	/**
	 * 执行成功或者中断（即无异常）
	 */
	public boolean isSuccessOrBreak()
	{
		return isSuccess() || isBreak();
	}

	/**
	 * 是否执行中中断
	 */
	public boolean isBreak()
	{
		return this.breakBean.isBreak();
	}

	public ParallelExecute getParallelExecute() {
		return parallelExecute;
	}
	
	public FailInfo getFailInfo()
	{
		if (!isFail())
		{
			return null;
		}
		
		FailInfo info = new FailInfo();
		
		if (this.exception instanceof MicroFlowRuntimeException)
		{
			MicroFlowRuntimeException e = (MicroFlowRuntimeException)this.exception;
			
			info.setCode(e.getCode());
			info.setMessage(e.getMessage());
			
			return info;
		}
		
		List<MicroFlowExceptionChange> microflowexceptionprocesslist = MicroFlowExceptionUtils.getMicroFlowExceptionChangeList();
		
		for (MicroFlowExceptionChange microFlowExceptionChange : microflowexceptionprocesslist) {
			
			if (microFlowExceptionChange.exceptionClass().isAssignableFrom(this.exception.getClass()))
			{
				return microFlowExceptionChange.exceptionToFailInfo(this.exception);
			}
		}
		
		info.setCode("FAIL_001");
		
		info.setMessage(this.exception.getMessage());
		
		return info;
		
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExecuteResult [exception=");
		builder.append(exception);
		builder.append(", breakBean=");
		builder.append(breakBean);
		builder.append("]");
		return builder.toString();
	}

	public String toString2() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExecuteResult [exception=");
		builder.append(exception);
		builder.append(", breakBean=");
		builder.append(breakBean);
		builder.append(", vertexTrackList=");
		builder.append(vertexTrackList);
		builder.append("]");
		return builder.toString();
	}

	public static class FailInfo implements Serializable {

		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;

		private String code;
		private String message;

		public FailInfo() {
		}

		public FailInfo(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("FailInfo [code=");
			builder.append(code);
			builder.append(", message=");
			builder.append(message);
			builder.append("]");
			return builder.toString();
		}

	}

}
