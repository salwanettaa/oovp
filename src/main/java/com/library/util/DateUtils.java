/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.util;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
/**
 *
 * @author Salwanetta
 */
public class DateUtils {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * Convert String to java.sql.Date
     * @param dateString Date in string format (yyyy-MM-dd)
     * @return java.sql.Date object
     */
    public static Date convertStringToSqlDate(String dateString) throws ParseException {
        java.util.Date parsed = DATE_FORMAT.parse(dateString);
        return new Date(parsed.getTime());
    }
    
    /**
     * Get current date as java.sql.Date
     * @return Current date
     */
    public static Date getCurrentDate() {
        return Date.valueOf(LocalDate.now());
    }
    
    /**
     * Add days to a date
     * @param date Base date
     * @param days Number of days to add
     * @return New date
     */
    public static Date addDays(Date date, int days) {
        LocalDate localDate = date.toLocalDate();
        LocalDate newDate = localDate.plusDays(days);
        return Date.valueOf(newDate);
    }
    
    /**
     * Format a date to string representation
     * @param date Date to format
     * @return Formatted date string
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }
}

