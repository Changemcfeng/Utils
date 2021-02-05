import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.Date;
/**
 * java8 时间工具类 <br>
 * localDateTime localDate转化成date，string <br>
 * 统计本周内的日期，本月的日期，前n天到今天的日期，后n天的到今天的日期 <br>
 * 日期的增加n天或者减少n天 <br>
 * @author mcfeng
 * @version 1.0 创建时间2021-2-5
 * */
public class DateUtils {
    /**
     *  DATE_PATTERN 格式化字符串类型{"yyyy-MM-dd"}
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    /**
     *  DATETIME_PATTERN 格式化字符串类型{"yyyy-MM-dd HH:mm:ss"}
     */
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     *  DateTimeFormatter 格式化对象{"yyyy-MM-dd"}
     */
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_PATTERN);
    /**
     *  DATE_PATTERN 格式化对象{"yyyy-MM-dd HH:mm:ss"}
     */
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
    /**
     *  LocalDate => Date
     * @param localDate LocalDate
     * @return Date
     */
    public static Date convertLocal(LocalDate localDate) {
        return convertLocal(localDate.atStartOfDay());
    }
    /**
     *  LocalDateTime => Date
     * @param localDateTime LocalDateTime
     * @return Date
     */
    public static Date convertLocal(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    /**
     *  LocalDate 增加天数或者减少天数
     * @param numbers LocalDateTime
     * @param localDate LocalDate
     * @return LocalDate
     */
    public static LocalDate add(long numbers, LocalDate localDate) {
        if (numbers > 0L) {
            return localDate.plus(numbers, ChronoUnit.DAYS);
        } else {
            return localDate.minus(Math.abs(numbers), ChronoUnit.DAYS);
        }
    }
    /**
     *  LocalDateTime 增加天数或者减少天数
     * @param numbers LocalDateTime
     * @param localDateTime LocalDate
     * @return LocalDateTime
     */
    public static LocalDateTime add(long numbers, LocalDateTime localDateTime) {
        if (numbers > 0L) {
            return localDateTime.plus(numbers, ChronoUnit.DAYS);
        } else {
            return localDateTime.minus(Math.abs(numbers), ChronoUnit.DAYS);
        }
    }
    /**
     *  LocalDateTime 增加天数或者减少天数
     * @param numbers LocalDateTime
     * @param localDate LocalDate
     * @param unit ChronoUnit
     * @return LocalDate
     */
    public static LocalDate add(long numbers, LocalDate localDate, TemporalUnit unit) {
        if (numbers > 0L) {
            return localDate.plus(numbers, unit);
        } else {
            return localDate.minus(Math.abs(numbers), unit);
        }

    }
    /**
     *  LocalDateTime 增加天数或者减少天数
     * @param numbers LocalDateTime
     * @param localDateTime LocalDateTime
     * @param unit ChronoUnit
     * @return LocalDateTime
     */
    public static LocalDateTime add(long numbers, LocalDateTime localDateTime, TemporalUnit unit) {
        if (numbers > 0L) {
            return localDateTime.plus(numbers, unit);
        } else {
            return localDateTime.minus(Math.abs(numbers), unit);
        }

    }
    /**
     *  将LocalDate转化成字符串
     * @param localDate LocalDate
     * @param st String
     * @return String
     */
    public static String parseString(LocalDate localDate, String st) {
        dateFormat = DateTimeFormatter.ofPattern(st);
        return localDate.format(dateFormat);
    }
    /**
     *  将LocalDate转化成字符串
     * @param localDate LocalDate
     * @return String
     */
    public static String parseString(LocalDate localDate) {
        return localDate.format(dateFormat);
    }
    /**
     *  将LocalDatetime转化成字符串
     * @param localDatetime LocalDate
     * @param st String
     * @return String
     */
    public static String parseString(LocalDateTime localDatetime, String st) {
        dateTimeFormat = DateTimeFormatter.ofPattern(st);
        return localDatetime.format(dateTimeFormat);
    }
    /**
     *  将LocalDatetime转化成字符串
     * @param localDatetime LocalDate
     * @return String
     */
    public static String parseString(LocalDateTime localDatetime) {
        return localDatetime.format(dateTimeFormat);
    }
    /**
     *  获取本周的日期数组
     * @param localDate LocalDate
     * @return String[]
     */
    public static String[] weeksString(LocalDate localDate) {
        String[] weeksString = new String[7];
        int temp = 0;
        for (DayOfWeek week : DayOfWeek.values()) {
            LocalDate tempDate = localDate.with(week);
            weeksString[temp] = dateFormat.format(tempDate);
            temp++;
        }
        return weeksString;
    }
    /**
     *  获取本周的日期数组
     * @param localDateTime LocalDateTime
     * @return String[]
     */
    public static String[] weeksString(LocalDateTime localDateTime) {

        return weeksString(localDateTime.toLocalDate());
    }
    /**
     *  获取本月的日期数组
     * @param localDate LocalDate
     * @return String[]
     */
    public static String[] monthsString(LocalDate localDate) {
        LocalDate lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate firstDayOfMonth = localDate.with(TemporalAdjusters.firstDayOfMonth());
        String[] monthsString = new String[lastDayOfMonth.getDayOfMonth()];
        for (int i = 0; i < lastDayOfMonth.getDayOfMonth(); i++) {
            monthsString[i] = parseString(add(i, firstDayOfMonth));
        }
        return monthsString;

    }
    /**
     *  获取本月的日期数组
     * @param localDateTime LocalDateTime
     * @return String[]
     */
    public static String[] monthsString(LocalDateTime localDateTime) {

        return monthsString(localDateTime.toLocalDate());
    }
    /**
     *  获取n天前/后到今天的日期数组
     * @param localDate LocalDate
     * @param interval int
     * @return String[]
     */
    public static String[] intervalTimeString(LocalDate localDate, int interval) {
        if (interval == 0) {
            return new String[0];
        }
        String[] intervalTimeString = new String[Math.abs(interval)];
        int temp = 0;
        if (interval >= 1) {
            for (int i = 0; i < interval; i++) {
                intervalTimeString[i] = parseString(add(i, localDate));
            }
        }
        if (interval <= -1) {
            for (int i = interval; i < 0; i++) {
                intervalTimeString[temp] = parseString(add(i, localDate));
                temp++;
            }
        }
        return intervalTimeString;
    }
    /**
     *  获取n天前/后到今天的日期数组
     * @param localDateTime LocalDateTime
     * @param interval int
     * @return String[]
     */
    public static String[] intervalTimeString(LocalDateTime localDateTime, int interval) {
        return intervalTimeString(localDateTime.toLocalDate(),interval);
    }
}
