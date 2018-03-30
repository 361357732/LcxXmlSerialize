package com.lcx.xml.serialize;

import java.util.HashSet;
import java.util.UUID;

public class Single {

    private static final HashSet<Class<?>> singleSet = new HashSet<Class<?>>() {

        private static final long serialVersionUID = 1L;

        {
            add(String.class);

            add(boolean.class);
            add(Boolean.class);
            add(byte.class);
            add(Byte.class);
            add(char.class);
            add(Character.class);
            add(short.class);
            add(Short.class);
            add(int.class);
            add(Integer.class);
            add(long.class);
            add(Long.class);
            add(float.class);
            add(Float.class);
            add(double.class);
            add(Double.class);

            add(UUID.class);
        }

    };

    protected static HashSet<Class<?>> getSingleSet() {
        return singleSet;
    }

    @SuppressWarnings("unchecked")
    protected static <T> T parseValue(Class<T> cls, Object value) {
        if (cls == boolean.class || cls == Boolean.class) {
            return (T) Boolean.valueOf((String) value);
        } else if (cls == byte.class || cls == Byte.class) {
            return (T) Byte.valueOf((String) value);
        } else if (cls == char.class || cls == Character.class) {
            return (T) Character.valueOf((Character) value);
        } else if (cls == short.class || cls == Short.class) {
            return (T) Short.valueOf((String) value);
        } else if (cls == int.class || cls == Integer.class) {
            return (T) Integer.valueOf((String) value);
        } else if (cls == long.class || cls == Long.class) {
            return (T) Long.valueOf((String) value);
        } else if (cls == float.class || cls == Float.class) {
            return (T) Float.valueOf((String) value);
        } else if (cls == double.class || cls == Double.class) {
            return (T) Double.valueOf((String) value);
        } else if (cls == UUID.class) {
            return (T) UUID.fromString((String) value);
        } else {
            return (T) value;
        }
    }

    protected static String formatValue(Object value) {
        Class<?> cls = value.getClass();
        if (cls == boolean.class || cls == Boolean.class) {
            return String.valueOf(value);
        } else if (cls == byte.class || cls == Byte.class) {
            return String.valueOf(value);
        } else if (cls == char.class || cls == Character.class) {
            return String.valueOf(value);
        } else if (cls == short.class || cls == Short.class) {
            return String.valueOf(value);
        } else if (cls == int.class || cls == Integer.class) {
            return String.valueOf(value);
        } else if (cls == long.class || cls == Long.class) {
            return String.valueOf(value);
        } else if (cls == float.class || cls == Float.class) {
            return String.valueOf(value);
        } else if (cls == double.class || cls == Double.class) {
            return String.valueOf(value);
        } else if (cls == UUID.class) {
            return value.toString();
        } else {
            return String.valueOf(value);
        }
    }

}
