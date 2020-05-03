/**
 *
 */
package com.center.microflow.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author Administrator
 *
 */
public abstract class ServiceLoaderUtils {

    public static <T> List<T> getServiceList(Class<T> claz) {
        // 使用系统的类加载器
        ServiceLoader<T> services = ServiceLoader.load(claz);

        Iterator<T> it = services.iterator();

        List<T> result = new ArrayList<>();

        while (it.hasNext()) {
            result.add(it.next());
        }

        return result;

    }

    public static <T> T getService(Class<T> claz) {
        List<T> serviceList = getServiceList(claz);

        if (serviceList.isEmpty()) {
            return null;
        }

        return serviceList.get(0);
    }

}
