package com.lcx.xml.serialize;

import java.lang.reflect.Array;
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

    private static final String arraySuffix = "Array";

    public static void main(String[] args) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read("E:\\work\\test.txt");

        ListOrdersResponse response = from(document.asXML(), new TypeReference<ListOrdersResponse>() {
        });
        println(toXml(response));
    }

    public static String toXml(Object object) throws Exception {
        Document document = DocumentHelper.createDocument();

        if (isSingle(object.getClass())) {
            formatSingleValue(object);
        } else if (isArray(object.getClass())) {
            makeArray(document.addElement(object.getClass().getComponentType().getSimpleName() + arraySuffix), object);
        } else if (isCollection(object.getClass())) {
            makeCollection(document.addElement(object.getClass().getSimpleName()), (Collection<?>) object);
        } else if (isMap(object.getClass())) {

        } else {
            makeObject(document.addElement(object.getClass().getSimpleName()), object);
        }

        return document.getRootElement().asXML();
    }

    public static <T> T from(String message, Class<T> cls) throws Exception {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(cls);

        return parse(message, null, list);
    }

    public static <T> T from(String message, String nodeName, Class<T> cls) throws Exception {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(cls);

        return parse(message, nodeName, list);
    }

    public static <T> T from(String message, TypeReference<T> type) throws Exception {
        return parse(message, null, getClassList(type.getType()));
    }

    public static <T> T from(String message, String nodeName, TypeReference<T> type) throws Exception {
        return parse(message, nodeName, getClassList(type.getType()));
    }

    @SuppressWarnings("unchecked")
    private static <T> T parse(String message, String nodeName, List<Class<?>> list) throws Exception {
        Element element = getElement(message, nodeName);

        Class<?> cls = list.get(0);
        if (isSingle(cls)) {
            return (T) parseSingleValue(cls, element.getText());
        } else if (isArray(cls)) {
            return (T) parseArray(element, cls.getComponentType());
        } else if (isCollection(cls)) {
            return (T) parseCollection(element, list, 1);
        } else if (isMap(cls)) {
            return null;
        } else {
            return (T) parseObject(element, cls);
        }
    }

    private static void makeObject(Element element, Object object) throws Exception {
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Object item = getFieldValueByName(object, fields[i]);

            addElement(element, fields[i].getName(), item);
        }
    }

    private static void makeArray(Element element, Object array) throws Exception {
        if (array == null) {
            return;
        }

        for (int i = 0; i < Array.getLength(array); ++i) {
            Object item = Array.get(array, i);

            addElement(element, item.getClass().getSimpleName(), item);
        }
    }

    private static void makeCollection(Element element, Collection<?> collection) throws Exception {
        if (collection == null || collection.size() == 0) {
            return;
        }

        for (Object item : collection) {
            if (isArray(item.getClass())) {
                addElement(element, item.getClass().getComponentType().getSimpleName() + arraySuffix, item);
            } else {
                addElement(element, item.getClass().getSimpleName(), item);
            }
        }
    }

    private static void addElement(Element element, String name, Object value) throws Exception {
        if (value != null) {
            if (isSingle(value.getClass())) {
                element.addElement(name).setText(formatSingleValue(value));
            } else if (isArray(value.getClass())) {
                makeArray(element.addElement(name), value);
            } else if (isCollection(value.getClass())) {
                makeCollection(element.addElement(name), (Collection<?>) value);
            } else if (isMap(value.getClass())) {

            } else {
                makeObject(element.addElement(name), value);
            }
        }
    }

    private static <T> T parseObject(Element parentElement, Class<T> cls) throws Exception {
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
                setFieldValueByName(obj, fields[i], parseSingleValue(fields[i].getType(), attribute.getValue()));
                continue;
            }

            Element element = parentElement.element(fields[i].getName());
            if (element == null) {
                continue;
            }

            Object value = parseElement(element, fields[i]);

            setFieldValueByName(obj, fields[i], value);
        }

        return obj;
    }

    private static Object parseElement(Element element, Field field) throws Exception {
        if (isSingle(field.getType())) {
            return element.getText();
        } else if (isArray(field.getType())) {
            return parseArray(element, field.getType().getComponentType());
        } else if (isCollection(field.getType())) {
            List<Class<?>> list = getClassList(field.getGenericType());
            return parseCollection(element, list, 1);
        } else if (isMap(field.getType())) {
            return null;
        } else {
            return parseObject(element, field.getType());
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] parseArray(Element element, Class<T> cls) throws Exception {
        if (element == null) {
            return null;
        }

        List<T> list = new ArrayList<T>();
        for (Iterator<?> iterator = element.elementIterator(); iterator.hasNext();) {
            Element itemElement = (Element) iterator.next();

            if (isSingle(cls)) {
                list.add(parseSingleValue(cls, itemElement.getText()));
            } else if (isArray(cls)) {
                list.add((T) parseArray(itemElement, cls.getComponentType()));
            } else if (isCollection(cls)) {
                List<Class<?>> clsList = getClassList(cls.getGenericSuperclass());
                list.add((T) parseCollection(itemElement, clsList, 1));
            } else if (isMap(cls)) {

            } else {
                list.add(parseObject(itemElement, cls));
            }
        }

        T[] array = (T[]) Array.newInstance(cls, list.size());
        return (T[]) list.toArray(array);
    }

    private static Collection<?> parseCollection(Element element, List<Class<?>> clsList, int index) throws Exception {
        if (element == null || clsList.size() == 0) {
            return null;
        }

        Class<?> cls = clsList.get(index);
        Collection<Object> list = new ArrayList<Object>();
        for (Iterator<?> iterator = element.elementIterator(); iterator.hasNext();) {
            Element itemElement = (Element) iterator.next();

            if (isSingle(cls)) {
                list.add(parseSingleValue(cls, itemElement.getText()));
            } else if (isArray(cls)) {
                list.add(parseArray(itemElement, cls.getComponentType()));
            } else if (isCollection(cls)) {
                list.add(parseCollection(itemElement, clsList, index + 1));
            } else if (isMap(cls)) {

            } else {
                list.add(parseObject(itemElement, cls));
            }
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

    private static List<Class<?>> getClassList(Type type) {
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
        Object object = parseSingleValue(field.getType(), value);
        method.invoke(obj, object);
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

    protected static void println(String message) {
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
