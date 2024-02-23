package com.rivaldy.reservationservice.util;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormatMapper {

    public static LocalDateTime convert(LocalDate date, LocalTime time){
        return LocalDateTime.of(date, time);
    }

    public static String convert(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
