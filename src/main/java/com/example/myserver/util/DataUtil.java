package com.example.myserver.util;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
public final class DataUtil {
    private DataUtil() {
    }
    
    public static <T> T mapToObject(final Map<String, Object> map, final Class<T> clazz) {
        if (map == null || clazz == null) {
            throw new IllegalArgumentException("Map or Class cannot be null");
        }

        T object = null;
        try {
            final Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            object = constructor.newInstance();

            final Field[] fields = clazz.getDeclaredFields();
            for (final Field field : fields) {
                field.setAccessible(true);
                setValue(map, field, object);
            }
        } catch (InstantiationException 
                 | IllegalAccessException 
                 | NoSuchMethodException 
                 | InvocationTargetException e) {
            throw new RuntimeException("Failed to create or set object", e);
        }
        return object;
    }

    private static <T> void setValue(final Map<String, Object> map, 
                                     final Field field, 
                                     final T object) throws IllegalAccessException {
        final String upperCase = field.getName().toUpperCase();
        if (map.containsKey(upperCase) || map.containsKey(field.getName())) {
            final Object value = map.containsKey(upperCase) ? map.get(upperCase) : map.get(field.getName());
            if (value != null) {
                setValueByType(field, object, value);
            }
        }
    }

    private static <T> void setValueByType(final Field field, 
                                           final T object, 
                                           final Object value) throws IllegalAccessException {
        if (value instanceof BigDecimal) {
            field.set(object, ((BigDecimal) value).intValue());
        } else if (value instanceof Long) {
            field.set(object, Math.toIntExact((Long) value));
        } else {
            field.set(object, value);
        }
    }
}
