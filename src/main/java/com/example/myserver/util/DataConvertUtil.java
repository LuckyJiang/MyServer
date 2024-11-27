package com.example.myserver.util;


import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.util.StringUtils;
import com.example.myserver.enums.TimeFormatEnum;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;


/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
public final class DataConvertUtil {
    private DataConvertUtil() {
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

    /**
     * 1.适用于：两个实体的属性名和属性类型完全相同时。属性个数可以不同。
     * MonitorPointVo map = modelMapper.map(excel, MonitorPointVo.class);
     * 2.当前方法为自定义类型转换
     */
    public static ModelMapper modelMapper() {

        final ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(new AbstractConverter<String, Date>() {
            @Override
            protected Date convert(final String source) {
                return StringUtils.isEmpty(source)
                        ? null : DateUtil.parse(source, TimeFormatEnum.DATE_POINT.getFormat());
            }
        });

        modelMapper.addConverter(new AbstractConverter<Date, String>() {
            @Override
            protected String convert(final Date source) {
                return Objects.isNull(source) ? null : DateUtil.format(source, TimeFormatEnum.DATE_POINT.getFormat());
            }
        });

        modelMapper.addConverter(new AbstractConverter<String, Double>() {
            @Override
            protected Double convert(final String source) {
                if (StringUtils.isEmpty(source)) {
                    return null;
                }
                final String value = handleNumberStr(source);
                if (isNumeric(value)) {
                    return Double.parseDouble(value);
                }
                return null;
            }
        });

        modelMapper.addConverter(new AbstractConverter<Double, String>() {
            @Override
            protected String convert(final Double source) {
                return Objects.isNull(source) ? null : String.valueOf(source);
            }
        });

        return modelMapper;
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


    private static String handleNumberStr(final String str) {
        if (str.contains("%")) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }

    private static boolean isNumeric(final String str) {
        final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return pattern.matcher(str).matches() && !str.contains(" ");
    }

}
