package com.lcx.xml.serialize;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflex {

    protected static Object getFieldValue(Object obj, Field field) throws Exception {
        String firstLetter = field.getName().substring(0, 1).toUpperCase();
        Method method = null;
        try {
            String getter = String.format("get%s%s", firstLetter, field.getName().substring(1));
            method = obj.getClass().getMethod(getter, new Class[] {});
        } catch (NoSuchMethodException e) { // 当是boolean类型时，可能会出现is获取值的情况
            String getter = String.format("is%s%s", firstLetter, field.getName().substring(1));
            method = obj.getClass().getMethod(getter, new Class[] {});
        }
        Object value = method.invoke(obj, new Object[] {});
        return value;
    }

    protected static void setFieldValue(Object obj, Field field, Object value) throws Exception {
        String firstLetter = field.getName().substring(0, 1).toUpperCase();
        String setter = String.format("set%s%s", firstLetter, field.getName().substring(1));
        Method method = obj.getClass().getMethod(setter, field.getType());
        method.invoke(obj, value);
    }

}
