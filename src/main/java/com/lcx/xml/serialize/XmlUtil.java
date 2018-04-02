package com.lcx.xml.serialize;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtil {

    private static final String arraySuffix = "Array";

    public static void main(String[] args) throws Exception {
        // List<String> item = new ArrayList<String>();
        // for (int i = 0; i < 10; i++) {
        // item.add("item" + i);
        // }
        //
        // List<List<String>> list = new ArrayList<List<String>>();
        // for (int i = 0; i < 10; i++) {
        // list.add(item);
        // }
        //
        // String xml = toXml(list);
        // println(xml);
        //
        // List<String[]> objList = from(xml, new
        // TypeReference<List<String[]>>() {
        // });
        // println(toXml(objList));

        Map<String, Integer> item = new HashMap<String, Integer>();
        for (int i = 0; i < 10; i++) {
            item.put("item" + i, 1111 * i);
        }

        Map<String, Map<String, Integer>> map = new HashMap<String, Map<String, Integer>>();
        for (int i = 0; i < 5; i++) {
            map.put("iiii" + i, item);
        }
        println(toXml(map));

        Map<String, Map<String, String>> tempMap = from(toXml(map),
                new TypeReference<Map<String, Map<String, String>>>() {
                });
        println(toXml(tempMap));
    }

    public static String toXml(Object object) throws Exception {
        Document document = DocumentHelper.createDocument();

        if (isSingle(object.getClass())) {
            Single.formatValue(object);
        } else if (isArray(object.getClass())) {
            makeArray(document.addElement(object.getClass().getComponentType().getSimpleName() + arraySuffix), object);
        } else if (isCollection(object.getClass())) {
            makeCollection(document.addElement(object.getClass().getSimpleName()), (Collection<?>) object);
        } else if (isMap(object.getClass())) {
            makeMap(document.addElement(object.getClass().getSimpleName()), (Map<?, ?>) object);
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
        return parse(message, null, Generic.getClassList(type.getType()));
    }

    public static <T> T from(String message, String nodeName, TypeReference<T> type) throws Exception {
        return parse(message, nodeName, Generic.getClassList(type.getType()));
    }

    @SuppressWarnings("unchecked")
    private static <T> T parse(String message, String nodeName, List<Class<?>> list) throws Exception {
        Element element = getElement(message, nodeName);

        Class<?> cls = list.get(0);
        if (isSingle(cls)) {
            return (T) Single.parseValue(cls, element.getText());
        } else if (isArray(cls)) {
            return (T) parseArray(element, cls.getComponentType());
        } else if (isCollection(cls)) {
            return (T) parseCollection(element, list, 1);
        } else if (isMap(cls)) {
            return (T) parseMap(element, list, 1);
        } else {
            return (T) parseObject(element, cls);
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
                Reflex.setFieldValue(obj, fields[i], Single.parseValue(fields[i].getType(), attribute.getValue()));
                continue;
            }

            Element element = parentElement.element(fields[i].getName());
            if (element == null) {
                continue;
            }

            Object value = null;
            if (isSingle(fields[i].getType())) {
                value = Single.parseValue(fields[i].getType(), element.getText());
            } else if (isArray(fields[i].getType())) {
                value = parseArray(element, fields[i].getType().getComponentType());
            } else if (isCollection(fields[i].getType())) {
                List<Class<?>> list = Generic.getClassList(fields[i].getGenericType());
                value = parseCollection(element, list, 1);
            } else if (isMap(fields[i].getType())) {
                List<Class<?>> list = Generic.getClassList(fields[i].getGenericType());
                value = parseMap(element, list, 1);
            } else {
                value = parseObject(element, fields[i].getType());
            }

            Reflex.setFieldValue(obj, fields[i], value);
        }

        return obj;
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
                list.add(Single.parseValue(cls, itemElement.getText()));
            } else if (isArray(cls)) {
                list.add((T) parseArray(itemElement, cls.getComponentType()));
            } else if (isCollection(cls)) {
                List<Class<?>> clsList = Generic.getClassList(cls.getGenericSuperclass());
                list.add((T) parseCollection(itemElement, clsList, 1));
            } else if (isMap(cls)) {
                List<Class<?>> clsList = Generic.getClassList(cls.getGenericSuperclass());
                list.add((T) parseMap(itemElement, clsList, 1));
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
                list.add(Single.parseValue(cls, itemElement.getText()));
            } else if (isArray(cls)) {
                list.add(parseArray(itemElement, cls.getComponentType()));
            } else if (isCollection(cls)) {
                list.add(parseCollection(itemElement, clsList, index + 1));
            } else if (isMap(cls)) {
                list.add(parseMap(itemElement, clsList, index + 1));
            } else {
                list.add(parseObject(itemElement, cls));
            }
        }

        return list;
    }

    private static Map<?, ?> parseMap(Element element, List<Class<?>> clsList, int index) throws Exception {
        if (element == null || clsList.size() == 0) {
            return null;
        }

        Class<?> keyCls = clsList.get(index);
        Class<?> valueCls = clsList.get(index + 1);
        Map<Object, Object> map = new HashMap<Object, Object>();
        for (Iterator<?> iterator = element.elementIterator(); iterator.hasNext();) {
            Element itemElement = (Element) iterator.next();

            Object key = Single.parseValue(keyCls, itemElement.getName());
            if (isSingle(valueCls)) {
                map.put(key, Single.parseValue(valueCls, itemElement.getText()));
            } else if (isArray(valueCls)) {
                map.put(key, parseArray(itemElement, valueCls));
            } else if (isCollection(valueCls)) {
                map.put(key, parseCollection(itemElement, clsList, index + 2));
            } else if (isMap(valueCls)) {
                map.put(key, parseMap(itemElement, clsList, index + 2));
            } else {
                map.put(key, parseObject(itemElement, valueCls));
            }
        }

        return map;
    }

    private static void makeObject(Element element, Object object) throws Exception {
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Object item = Reflex.getFieldValue(object, fields[i]);

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

    private static <K, V> void makeMap(Element element, Map<K, V> map) throws Exception {
        if (map == null || map.size() == 0) {
            return;
        }

        for (K key : map.keySet()) {
            addElement(element, Single.formatValue(key), map.get(key));
        }
    }

    private static void addElement(Element element, String name, Object value) throws Exception {
        if (value != null) {
            if (isSingle(value.getClass())) {
                element.addElement(name).setText(Single.formatValue(value));
            } else if (isArray(value.getClass())) {
                makeArray(element.addElement(name), value);
            } else if (isCollection(value.getClass())) {
                makeCollection(element.addElement(name), (Collection<?>) value);
            } else if (isMap(value.getClass())) {
                makeMap(element.addElement(name), (Map<?, ?>) value);
            } else {
                makeObject(element.addElement(name), value);
            }
        } else {
            if (XmlConfig.isShowNull()) {
                element.addElement(name);
            }
        }
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

    private static boolean isSingle(Class<?> cls) {
        return Single.getSingleSet().contains(cls);
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

    private static void println(String message) {
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
