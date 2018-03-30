package com.lcx.xml.serialize;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Generic {

    protected static List<Class<?>> getClassList(Type type) {
        List<Class<?>> list = new ArrayList<Class<?>>();

        list.add(getRawType(type));
        getGenericType(list, type);

        return list;
    }

    private static Class<?> getRawType(Type type) {
        return type instanceof Class<?> ? (Class<?>) type : (Class<?>) ((ParameterizedType) type).getRawType();
    }

    private static void getGenericType(List<Class<?>> list, Type type) {
        if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
            for (Type t : ((ParameterizedType) type).getActualTypeArguments()) {
                list.add(getRawType(t));
                getGenericType(list, t);
            }
        }
    }

}
