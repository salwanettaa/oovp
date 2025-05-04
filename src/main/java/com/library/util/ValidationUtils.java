
package com.library.util;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Utility class for validation operations
 */
public class ValidationUtils {
    
    private static final Pattern ISBN_PATTERN = Pattern.compile("^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");
    
    /**
     * Validate if a string is a valid ISBN
     * @param isbn ISBN to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidISBN(String isbn) {
        return ISBN_PATTERN.matcher(isbn).matches();
    }
    
    /**
     * Validate if a year is valid (not in the future)
     * @param year Year to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPublishYear(int year) {
        int currentYear = LocalDate.now().getYear();
        return year > 0 && year <= currentYear;
    }
    
    /**
     * Check if a string is empty or null
     * @param str String to check
     * @return true if string is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Validate if quantity is valid (positive)
     * @param qty Quantity to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidQuantity(int qty) {
        return qty >= 0;
    }
}
