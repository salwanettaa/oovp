package com.library.service;

import com.library.model.Book;
import com.library.model.IssuedBook;
import java.util.List;
import java.util.Map;

/**
 * Service interface for Report operations
 */
public interface ReportService {
    
    /**
     * Get books that are not returned yet
     * @return List of non-returned books with details
     */
    List<IssuedBook> getBooksNotReturned();
    
    /**
     * Get count of books by author
     * @return Map of author name to book count
     */
    Map<String, Integer> getBookCountByAuthor();
    
    /**
     * Get books by field/category
     * @param field Field/category
     * @return List of books in the field
     */
    List<Book> getBooksByField(String field);
    
    /**
     * Get most active members (based on book borrowings)
     * @param limit Number of members to return
     * @return Map of member name to borrow count
     */
    Map<String, Integer> getMostActiveMembers(int limit);
    
    /**
     * Get most popular books (based on borrow frequency)
     * @param limit Number of books to return
     * @return Map of book title to borrow count
     */
    Map<String, Integer> getMostPopularBooks(int limit);
}