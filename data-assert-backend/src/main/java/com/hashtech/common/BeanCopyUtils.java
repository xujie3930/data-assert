package com.hashtech.common;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    /**
     * 将对象属性拷贝到目标类型的同名属性字段中
     *
     * @param <T>
     * @param source
     * @param targetClazz
     * @return
     */
    public static <T> T copyProperties(Object source, Class<T> targetClazz) {

        T target = null;
        try {
            target = targetClazz.newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return target;
    }

    /**
     * 将对象属性拷贝到目标类型的同名属性字段中
     *
     * @param source
     * @param target
     * @return
     */
    public static <T> T copyProperties(Object source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 将list的对象拷贝到目标类型对象中
     *
     * @param list
     * @param clazz
     * @return
     */
    public static <V, E> List<E> copy(Collection<V> list, Class<E> clazz) {
        List<E> result = new ArrayList<>(12);

        if (!CollectionUtils.isEmpty(list)) {
            for (V source : list) {
                E target = null;
                try {
                    target = (E) clazz.newInstance();
                    BeanUtils.copyProperties(source, target);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                result.add(target);
            }
        }

        return result;
    }
}

