package com.lcx.xml.serialize;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeReference<T> {

    private final Type type;
    private volatile Constructor<?> constructor;

    protected TypeReference() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    protected T newInstance() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {
        if (constructor == null) {
            Class<?> rawType = type instanceof Class<?> ? (Class<?>) type : (Class<?>) ((ParameterizedType) type)
                    .getRawType();
            constructor = rawType.getConstructor();
        }
        return (T) constructor.newInstance();
    }

    protected Class<?> getRawType() {
        return type instanceof Class<?> ? (Class<?>) type : (Class<?>) ((ParameterizedType) type).getRawType();
    }

}
