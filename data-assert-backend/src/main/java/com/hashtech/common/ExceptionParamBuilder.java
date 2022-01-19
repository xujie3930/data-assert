package com.hashtech.common;


import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public final class ExceptionParamBuilder {
    private static ExceptionParamBuilder builder;
    private ThreadLocal<LinkedList<String>> keys = new ThreadLocal();
    private ThreadLocal<LinkedList<Object>> values = new ThreadLocal();

    private ExceptionParamBuilder() {
    }

    public static ExceptionParamBuilder getInstance() {
        if (builder == null) {
            builder = new ExceptionParamBuilder();
        }

        return builder;
    }

    public ExceptionParamBuilder put(String key, Object value) {
        if (this.keys.get() == null) {
            this.keys.set(new LinkedList());
        }

        if (this.values.get() == null) {
            this.values.set(new LinkedList());
        }

        if (((LinkedList) this.keys.get()).contains(key)) {
            throw new IllegalArgumentException("param redefine：" + key);
        } else {
            ((LinkedList) this.keys.get()).add(key);
            ((LinkedList) this.values.get()).add(value);
            return this;
        }
    }

    private String[] getKeys() {
        LinkedList<String> keys = (LinkedList) this.keys.get();
        String[] result;
        if (keys != null && keys.size() > 0) {
            result = (String[]) keys.toArray(new String[0]);
        } else {
            result = new String[0];
        }

        this.keys.remove();
        return result;
    }

    private Object[] getValues() {
        LinkedList<Object> values = (LinkedList) this.values.get();
        Object[] result;
        if (values != null && values.size() > 0) {
            result = values.toArray();
        } else {
            result = new Object[0];
        }

        this.values.remove();
        return result;
    }

    public Map<String, Object> getAll() {
        Map<String, Object> all = new LinkedHashMap();
        String[] keys = this.getKeys();
        Object[] values = this.getValues();

        for (int i = 0; i < keys.length; ++i) {
            all.put(keys[i], values[i]);
        }

        return all;
    }

    public ExceptionParamBuilder empty() {
        this.keys.set(new LinkedList());
        this.values.set(new LinkedList());
        return this;
    }
}