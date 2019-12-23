package com.ruan.yuanyuan;

import java.time.LocalDate;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.parse("2019-11-23");
        localDate = localDate.plusMonths(-1);
        localDate=localDate.withDayOfMonth(1);
        System.out.println(localDate);
    }
}
