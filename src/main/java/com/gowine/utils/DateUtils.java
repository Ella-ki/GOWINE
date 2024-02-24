package com.gowine.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    public static String getRelativeDate(LocalDate regDate) {
        LocalDate currentDate = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(regDate, currentDate);

        if (daysBetween == 0) {
            return "오늘";
        } else if (daysBetween == 1) {
            return "1일 전";
        } else if (daysBetween == 2) {
            return "2일 전";
        } else if (daysBetween >= 3) {
            return regDate.toString(); // 3일 이후일 경우에는 날짜로 표시
        } else {
            return daysBetween + "일 전";
        }
    }
}
