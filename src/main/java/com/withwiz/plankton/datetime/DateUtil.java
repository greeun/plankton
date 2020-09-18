package com.withwiz.plankton.datetime;

import com.withwiz.plankton.util.StringUtil;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Date utility class.<BR>
 * Created by uni4love on 2008. 6. 24..
 */
public class DateUtil {
    /**
     * 현 시간 Calendar instance를 리턴한다.
     *
     * @return Calendar
     */
    public static Calendar getCurrentCalendar() {
        return Calendar.getInstance(TimeZone.getDefault());
    }

    /**
     * Date를 입력한 format에 맞도록 돌려준다.<BR>
     * ex) yyyy-mm-dd : 2007-12-27 2007-12-27 오후 8:35:00
     *
     * @param c      Calendar
     * @param format format string
     * @return formatted date string
     */
    public static String getDate(Calendar c, String format) {
        int year = c.get(Calendar.YEAR);
        // Calendar는 month를 1 작게 return한다.
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        format = format.toLowerCase();
        // format = format.replaceAll("yyyy", String.valueOf(year));
        format = StringUtil.replace(format, "yyyy", String.valueOf(year));
        // format = format.replaceAll("yy", String.valueOf(year).substring(2,
        // 4));
        format = StringUtil.replace(format, "yy", String.valueOf(year)
                .substring(2, 4));
        if (month < 10) {
            // 한자리 숫자일 경우 0을 붙여준다.
            // format = format.replaceAll("mm", "0" + String.valueOf(month));
            format = StringUtil.replace(format, "mm",
                    "0" + String.valueOf(month));
        } else {
            // format = format.replaceAll("mm", String.valueOf(month));
            format = StringUtil.replace(format, "mm", String.valueOf(month));
        }
        if (day < 10) {
            // 한자리 숫자일 경우 0을 붙여준다.
            // format = format.replaceAll("dd", "0" + String.valueOf(day));
            format = StringUtil
                    .replace(format, "dd", "0" + String.valueOf(day));
        } else {
            // format = format.replaceAll("dd", String.valueOf(day));
            format = StringUtil.replace(format, "dd", String.valueOf(day));
        }
        // 한자리 숫자일 경우에도 0을 붙이지 않는다.
        // format = format.replaceAll("m", String.valueOf(month));
        format = StringUtil.replace(format, "m", String.valueOf(month));
        // 한자리 숫자일 경우에도 0을 붙이지 않는다.
        // format = format.replaceAll("d", String.valueOf(day));
        format = StringUtil.replace(format, "d", String.valueOf(day));
        return format;
    }

    /**
     * Date를 입력한 format에 맞도록 돌려준다.<BR>
     * ex) yyyy-mm-dd : 2007-12-27 2007-12-27 오후 8:35:00
     *
     * @param format format string
     * @return formatted date string
     */
    public static String getDate(String format) {
        return getDate(getCurrentCalendar(), format);
    }

    /**
     * 형태에 맞는 시간을 리턴한다.
     *
     * @param c      Calendar
     * @param format format string
     * @return formatted time string
     */
    public static String getTime(Calendar c, String format) {
        int hour_of_day = c.get(Calendar.HOUR_OF_DAY);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        int ampm = c.get(Calendar.AM_PM);
        format = format.toLowerCase();
        String am_pm = "";
        if (0 == ampm) {
            am_pm = "AM";
        } else if (1 == ampm) {
            am_pm = "PM";
        }
        if (hour_of_day < 10) {
            // 24시로 표시한다.
            // format = format.replaceAll("24hh", "0" +
            // String.valueOf(hour_of_day));
            format = StringUtil.replace(format, "24hh",
                    "0" + String.valueOf(hour_of_day));
        } else {
            // format = format.replaceAll("24hh", String.valueOf(hour_of_day));
            format = StringUtil.replace(format, "24hh",
                    String.valueOf(hour_of_day));
        }
        if (hour < 10) {
            // 한자리 숫자일 경우 0을 붙여준다.
            // format = format.replaceAll("hh", "0" + String.valueOf(hour));
            // format = UtilString.replace(format, "hh", "0" +
            // String.valueOf(hour));
            String LocalHour = String.valueOf(hour);
            if (1 == ampm && LocalHour.equals("0")) {
                LocalHour = "12";
            }
            format = StringUtil.replace(format, "hh", LocalHour);
        } else {
            // format = format.replaceAll("hh", String.valueOf(hour));
            format = StringUtil.replace(format, "hh", String.valueOf(hour));
        }
        if (minute < 10) {
            // 한자리 숫자일 경우 0을 붙여준다.
            // format = format.replaceAll("mm", "0" + String.valueOf(minute));
            format = StringUtil.replace(format, "mm",
                    "0" + String.valueOf(minute));
        } else {
            // format = format.replaceAll("mm", String.valueOf(minute));
            format = StringUtil.replace(format, "mm", String.valueOf(minute));
        }
        if (second < 10) {
            // 한자리 숫자일 경우 0을 붙여준다.
            // format = format.replaceAll("ss", "0" + String.valueOf(second));
            format = StringUtil.replace(format, "ss",
                    "0" + String.valueOf(second));
        } else {
            // format = format.replaceAll("ss", String.valueOf(second));
            format = StringUtil.replace(format, "ss", String.valueOf(second));
        }
        // 한자리 숫자일 경우에도 0을 붙이지 않는다.
        // format = format.replaceAll("24h", String.valueOf(hour_of_day));
        format = StringUtil.replace(format, "24h", String.valueOf(hour_of_day));
        // 한자리 숫자일 경우에도 0을 붙이지 않는다.
        // format = format.replaceAll("h", String.valueOf(hour));
        format = StringUtil.replace(format, "a", String.valueOf(am_pm));
        format = StringUtil.replace(format, "h", String.valueOf(hour));
        // 한자리 숫자일 경우에도 0을 붙이지 않는다.
        // format = format.replaceAll("m", String.valueOf(minute));
        format = StringUtil.replace(format, "m", String.valueOf(minute));
        // 한자리 숫자일 경우에도 0을 붙이지 않는다.
        // format = format.replaceAll("s", String.valueOf(second));
        format = StringUtil.replace(format, "s", String.valueOf(second));
        return format;
    }

    /**
     * 형태에 맞는 시간을 리턴한다.
     *
     * @param format format string
     * @return formatted time string
     */
    public static String getTime(String format) {
        return getTime(getCurrentCalendar(), format);
    }

    /**
     * 형태에 맞는 시간을 리턴한다.
     *
     * @param c        Calendar
     * @param format   format string
     * @param useAM_PM true or false
     * @return formatted time string
     */
    public static String getTime(Calendar c, String format, boolean useAM_PM) {
        int hour_of_day = c.get(Calendar.HOUR_OF_DAY);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        int ampm = c.get(Calendar.AM_PM);
        format = format.toLowerCase();
        String am_pm = "";
        if (useAM_PM) {
            if (0 == ampm) {
                am_pm = "AM";
            } else if (1 == ampm) {
                am_pm = "PM";
            }
        }
        if (hour_of_day < 10) {
            // 24시로 표시한다.
            // format = format.replaceAll("24hh", "0" +
            // String.valueOf(hour_of_day));
            format = StringUtil.replace(format, "24hh",
                    "0" + String.valueOf(hour_of_day));
        } else {
            // format = format.replaceAll("24hh", String.valueOf(hour_of_day));
            format = StringUtil.replace(format, "24hh",
                    String.valueOf(hour_of_day));
        }
        if (hour < 10) {
            // 한자리 숫자일 경우 0을 붙여준다.
            // format = format.replaceAll("hh", "0" + String.valueOf(hour));
            if (useAM_PM) {
                String LocalHour = String.valueOf(hour);
                if (1 == ampm && LocalHour.equals("0")) {
                    LocalHour = "12";
                }
                format = StringUtil.replace(format, "hh", LocalHour);
            } else {
                format = StringUtil.replace(format, "hh",
                        "0" + String.valueOf(hour));
            }
        } else {
            // format = format.replaceAll("hh", String.valueOf(hour));
            format = StringUtil.replace(format, "hh", String.valueOf(hour));
        }
        if (minute < 10) {
            // 한자리 숫자일 경우 0을 붙여준다.
            // format = format.replaceAll("mm", "0" + String.valueOf(minute));
            format = StringUtil.replace(format, "mm",
                    "0" + String.valueOf(minute));
        } else {
            // format = format.replaceAll("mm", String.valueOf(minute));
            format = StringUtil.replace(format, "mm", String.valueOf(minute));
        }
        if (second < 10) {
            // 한자리 숫자일 경우 0을 붙여준다.
            // format = format.replaceAll("ss", "0" + String.valueOf(second));
            format = StringUtil.replace(format, "ss",
                    "0" + String.valueOf(second));
        } else {
            // format = format.replaceAll("ss", String.valueOf(second));
            format = StringUtil.replace(format, "ss", String.valueOf(second));
        }
        // 한자리 숫자일 경우에도 0을 붙이지 않는다.
        // format = format.replaceAll("24h", String.valueOf(hour_of_day));
        format = StringUtil.replace(format, "24h", String.valueOf(hour_of_day));
        // 한자리 숫자일 경우에도 0을 붙이지 않는다.
        // format = format.replaceAll("h", String.valueOf(hour));
        format = StringUtil.replace(format, "a", String.valueOf(am_pm));
        format = StringUtil.replace(format, "h", String.valueOf(hour));
        // 한자리 숫자일 경우에도 0을 붙이지 않는다.
        // format = format.replaceAll("m", String.valueOf(minute));
        format = StringUtil.replace(format, "m", String.valueOf(minute));
        // 한자리 숫자일 경우에도 0을 붙이지 않는다.
        // format = format.replaceAll("s", String.valueOf(second));
        format = StringUtil.replace(format, "s", String.valueOf(second));
        return format;
    }

    /**
     * 형태에 맞는 시간을 리턴한다.
     *
     * @param format time format string
     * @return formatted time string
     */
    public String getTime(String format, boolean useAM_PM) {
        return getTime(getCurrentCalendar(), format, useAM_PM);
    }

    /**
     * 00:00 형식의 시간을 분(minute)으로 리턴한다.
     *
     * @param str time string
     * @return int  int value for minute
     */
    public static int getMinutes(String str) {
        if (str.startsWith("-")) {
            String sHour = str.substring(1, 3);
            String sMinute = str.substring(4, 6);
            int hour = -(Integer.parseInt(sHour));
            int minute = -(Integer.parseInt(sMinute));
            return (hour * 60) + minute;
        } else {
            String sHour = str.substring(0, 2);
            String sMinute = str.substring(3, 5);
            int hour = Integer.parseInt(sHour);
            int minute = Integer.parseInt(sMinute);
            return (hour * 60) + minute;
        }
    }

    /**
     * 0000 형식의 분 표현 시간을 00:00 형식으로 리턴한다.
     *
     * @param minutes int value for time
     * @return time string
     */
    public static String getHourMinute(int minutes) {
        int hour = minutes / 60;
        int minute = minutes % 60;
        String sHour = "" + hour;
        String sMinute = "" + minute;
        if (sHour.length() == 1)
            sHour = "0" + sHour;
        if (sMinute.length() == 1) {
            sMinute = "0" + sMinute;
        }
        return new String(sHour + ":" + sMinute);
    }

    /**
     * YYYYMMDD 형태의 날짜를 YYYY.MM.DD 형태로 리턴한다.
     *
     * @param sDate YYYYMMDD 형태의 날짜
     * @return YYYY.MM.DD 형태의 날짜
     */
    public static String convertToYYYY_MM_DD(String sDate) {
        String sRet = "";
        StringBuffer sb = new StringBuffer();
        if (sDate.length() >= 8) {
            sRet = sb.append(sDate.substring(0, 4)).append(".")
                    .append(sDate.substring(4, 6)).append(".")
                    .append(sDate.substring(6, 8)).toString();
        } else {
            sRet = sDate;
        }
        return sRet;
    }

    /**
     * 입력된 시간(초)을 시간:분:초 형태로 리턴한다.
     *
     * @param nSeconds 시간(초)
     * @return 처리된 문자열
     */
    public static String convertToHH_mm_ss(long nSeconds) {
        String hours = "" + nSeconds / 3600;
        long temp = nSeconds % 3600;
        String minutes = "" + temp / 60;
        String seconds = "" + temp % 60;
        if (Integer.parseInt(hours) < 10) {
            hours = "0" + hours;
        }
        if (Integer.parseInt(minutes) < 10) {
            minutes = "0" + minutes;
        }
        if (Integer.parseInt(seconds) < 10) {
            seconds = "0" + seconds;
        }
        return hours + ":" + minutes + ":" + seconds;
    }

    /**
     * 입력된 시간(초)을 시간:분:초 형태로 리턴한다.
     *
     * @param sSeconds 시간(초) - 문자열일 경우
     * @return
     */
    public static String convertToHH_mm_ss(String sSeconds) {
        return convertToHH_mm_ss(Long.parseLong(sSeconds));
    }

    public static String getDayOfWeek(Calendar c, boolean isUseKR) {
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                if (isUseKR) {
                    return "일";
                } else {
                    return "SUN";
                }
            case Calendar.MONTH:
                if (isUseKR) {
                    return "월";
                } else {
                    return "MON";
                }
            case Calendar.TUESDAY:
                if (isUseKR) {
                    return "화";
                } else {
                    return "TUE";
                }
            case Calendar.WEDNESDAY:
                if (isUseKR) {
                    return "수";
                } else {
                    return "WED";
                }
            case Calendar.THURSDAY:
                if (isUseKR) {
                    return "목";
                } else {
                    return "THU";
                }
            case Calendar.FRIDAY:
                if (isUseKR) {
                    return "금";
                } else {
                    return "FRI";
                }
            case Calendar.SATURDAY:
                if (isUseKR) {
                    return "토";
                } else {
                    return "SAT";
                }
            default:
                break;
        }
        return null;
    }

    /**
     * test main
     *
     * @param args
     */
    public static void main(String[] args) {
        String str = "20070809";
        long l = 67455;
        String t = "000133";
        System.out.println("Date " + str + " -> " + convertToYYYY_MM_DD(str));
        System.out.println("Time " + l + " -> " + convertToHH_mm_ss(l));
        System.out.println("Time " + t + " -> " + convertToHH_mm_ss(t));
        System.out.println("current data time: " + DateUtil.getDate("yyyy-mm-dd")
                + " " + DateUtil.getTime("24hh:mm:ss"));

    }
}
