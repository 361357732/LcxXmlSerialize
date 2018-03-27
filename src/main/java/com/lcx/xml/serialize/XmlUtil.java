package com.lcx.xml.serialize;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lcx.xml.serialize.model.ListOrdersResponse;

public class XmlUtil {

    public static void main(String[] args) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read("E:\\work\\test.txt");
        // println(document.asXML());

        // ListOrdersResponse response = from(document.asXML(),
        // ListOrdersResponse.class);
        //
        // String xml = toXml(response);
        // println(xml);
        //
        // ListOrdersResponse obj = from(xml, new
        // TypeReference<ListOrdersResponse>() {
        // });
        // println(toXml(obj));

        // ListOrdersResponse response = from(document.asXML(),
        // ListOrdersResponse.class);
        ListOrdersResponse response = from(document.asXML(), new TypeReference<ListOrdersResponse>() {
        });
        println(toXml(response));
    }

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

    @SuppressWarnings("unchecked")
    private static <T> T parseSingleValue(Class<T> cls, Object value) {
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

    private static String formatSingleValue(Object value) {
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

    public static String toXml(Object object) throws Exception {
        Document document = DocumentHelper.createDocument();

        if (isSingle(object.getClass())) {
            formatSingleValue(object);
        } else {
            makeObject(document.addElement(object.getClass().getSimpleName()), object);
        }

        return document.getRootElement().asXML();
    }

    public static <T> T from(String message, Class<T> cls) throws Exception {
        return parse(message, null, cls);
    }

    public static <T> T from(String message, String nodeName, Class<T> cls) throws Exception {
        return parse(message, nodeName, cls);
    }

    public static <T> T from(String message, TypeReference<T> type) throws Exception {
        return parse(message, null, type.getRawType());
    }

    public static <T> T from(String message, String nodeName, TypeReference<T> type) throws Exception {
        return parse(message, nodeName, type.getRawType());
    }

    @SuppressWarnings("unchecked")
    public static <T> T parse(String message, String nodeName, Class<?> cls) throws Exception {
        Element element = getElement(message, nodeName);

        if (isSingle(cls)) {
            return (T) parseSingleValue(cls, element.getText());
        } else if (isArray(cls)) {
            return null;
        } else if (isCollection(cls)) {
            return null;
        } else if (isMap(cls)) {
            return null;
        } else {
            return (T) parseObj(element, cls);
        }
    }

    private static void makeObject(Element element, Object object) throws Exception {
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (isSingle(fields[i].getType())) {
                addElement(element, object, fields[i]);
            } else if (isArray(fields[i].getType())) {

            } else if (isCollection(fields[i].getType())) {
                Collection<?> value = (Collection<?>) getFieldValueByName(object, fields[i]);
                makeCollection(element, fields[i], value);
            } else if (isMap(fields[i].getType())) {

            } else {
                Object value = getFieldValueByName(object, fields[i]);
                if (value != null) {
                    makeObject(element.addElement(fields[i].getName()), value);
                }
            }
        }
    }

    private static <T> T parseObj(Element parentElement, Class<T> cls) throws Exception {
        if (parentElement == null || cls == null) {
            return null;
        }

        T obj = cls.newInstance();
        if (obj == null) {
            return null;
        }

        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Attribute attribute = parentElement.attribute(fields[i].getName());
            if (attribute != null) {
                setFieldValueByName(obj, fields[i], attribute.getValue());
                continue;
            }

            Element element = parentElement.element(fields[i].getName());
            if (element == null) {
                continue;
            }

            if (isSingle(fields[i].getType())) {
                setFieldValueByName(obj, fields[i], element.getText());
            } else if (isArray(fields[i].getType())) {

            } else if (isCollection(fields[i].getType())) {
                setFieldValueByName(obj, fields[i], parseCollection(element, getGenericType(fields[i])));
            } else if (isMap(fields[i].getType())) {

            } else {
                setFieldValueByName(obj, fields[i], parseObj(element, fields[i].getType()));
            }
        }

        return obj;
    }

    private static void makeArray() {

    }

    private static void makeCollection(Element listElement, Field field, Collection<?> collection) throws Exception {
        if (collection == null || collection.size() == 0) {
            return;
        }

        Element element = listElement.addElement(field.getName());

        for (Object item : collection) {
            Element itemElement = element.addElement(item.getClass().getSimpleName());
            makeObject(itemElement, item);
        }
    }

    private static <T> List<T> parseCollection(Element element, Class<T> cls) throws Exception {
        if (element == null) {
            return null;
        }

        List<T> list = new ArrayList<T>();

        for (Iterator<?> iterator = element.elementIterator(); iterator.hasNext();) {
            Element itemElement = (Element) iterator.next();
            list.add(parseObj(itemElement, cls));
        }

        return list;
    }

    private static Element getElement(String message, String nodeName) throws DocumentException {
        if (message == null) {
            return null;
        }

        Document document = DocumentHelper.parseText(message);
        if (document == null) {
            return null;
        }

        Element rootElement = document.getRootElement();
        if (nodeName == null) {
            return rootElement;
        } else {
            return rootElement.element(nodeName);
        }
    }

    private static void addElement(Element element, Object object, Field field) throws Exception {
        Object value = getFieldValueByName(object, field);
        if (value != null) {
            element.addElement(field.getName()).setText(String.valueOf(value));
        }
    }

    private static Class<?> getGenericType(Field f) {
        Class<?> c = null;
        Type gType = f.getGenericType();
        if (gType instanceof ParameterizedType) {
            ParameterizedType ptype = (ParameterizedType) gType;
            Type[] targs = ptype.getActualTypeArguments();
            if (targs != null && targs.length > 0) {
                Type t = targs[0];
                c = (Class<?>) t;
            }
        }
        return c;
    }

    private static Object getFieldValueByName(Object obj, Field field) throws Exception {
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

    private static void setFieldValueByName(Object obj, Field field, Object value) throws Exception {
        String firstLetter = field.getName().substring(0, 1).toUpperCase();
        String setter = String.format("set%s%s", firstLetter, field.getName().substring(1));
        Method method = obj.getClass().getMethod(setter, field.getType());
        method.invoke(obj, parseSingleValue(field.getType(), value));
    }

    private static boolean isSingle(Class<?> cls) {
        return singleSet.contains(cls);
    }

    private static boolean isArray(Class<?> cls) {
        return cls.isArray();
    }

    private static boolean isCollection(Class<?> cls) {
        return Collection.class.isAssignableFrom(cls);
    }

    private static boolean isMap(Class<?> cls) {
        return Map.class.isAssignableFrom(cls);
    }

    public static void println(String message) {
        StringBuffer sb = new StringBuffer();

        StackTraceElement[] stacks = new Throwable().getStackTrace();
        sb.append(stacks[1].getClassName());
        sb.append(".");
        sb.append(stacks[1].getMethodName());
        sb.append("(");
        sb.append(stacks[1].getLineNumber());
        sb.append(")");

        System.out.println(sb.toString() + " : " + message);
    }

}
