package com.example.myserver.util;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.google.common.collect.Lists;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public final class DateUtil {

    public static final List<String> DEFAULT_PATTERNS;

    public static final int DAY_TO_WEEK_CONVERT = 7;

    static {
        final List<String> defaultDatePatterns = Lists.newArrayList("yyyy-MM-dd", "yyyy/MM/dd",
                "yyyy年MM月dd日", "yyyyMMdd", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyy年MM月dd日 HH时mm分ss秒");
        DEFAULT_PATTERNS = Collections.unmodifiableList(defaultDatePatterns);
    }

    private DateUtil() {

    }

    public static Date parse(final String source, final String... patterns) throws Exception {
        Assert.hasText(source, "source must not be empty");
        final SimpleDateFormat sdf = new SimpleDateFormat();
        Date date = null;
        if (ArrayUtils.isEmpty(patterns)) {
            for (String pattern : DEFAULT_PATTERNS) {
                sdf.applyPattern(pattern);
                try {
                    date = sdf.parse(source);
                    break;
                } catch (ParseException ignored) {
                    // ignore
                }
            }
        } else {
            for (String pattern : patterns) {
                sdf.applyPattern(pattern);
                try {
                    date = sdf.parse(source);
                    break;
                } catch (ParseException ignored) {
                    // ignore
                }
            }
        }

        if (Objects.isNull(date)) {
            throw new Exception("解析日期字符串失败");
        }
        return date;
    }

    public static String format(final Date date, final String pattern) {
        Assert.notNull(date, "date must not be null");
        Assert.hasText(pattern, "pattern must not be empty");
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date subtractDays(final Date date, final int amount) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -amount);
        return calendar.getTime();
    }

    public static LocalDate dateToLocalDate(final Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    public static int betweenDays(final Date startDate, final Date endDate) {
        return (int) ChronoUnit.DAYS.between(dateToLocalDate(startDate), dateToLocalDate(endDate));
    }

    public static int dayToWeek(final int days) {
        return Math.floorDiv(days, DAY_TO_WEEK_CONVERT) + (Math.floorMod(days, DAY_TO_WEEK_CONVERT) == 0 ? 0 : 1);
    }

}
