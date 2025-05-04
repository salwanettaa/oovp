package com.library.dao;

import com.library.model.Book;
import java.util.List;

/**
 * DAO interface for Book entity
 */
public interface BookDAO extends BaseDAO<Book> {
    
    /**
     * Find books by title
     * @param title Title to search
     * @return List of books matching the title
     */
    List<Book> findByTitle(String title);
    
    /**
     * Find books by author
     * @param author Author to search
     * @return List of books by the author
     */
    List<Book> findByAuthor(String author);
    
    /**
     * Find books by field/category
     * @param field Field to search
     * @return List of books in the field
     */
    List<Book> findByField(String field);
    
    /**
     * Find books by year of publication
     * @param year Year to search
     * @return List of books published in that year
     */
    List<Book> findByYear(int year);
    
    /**
     * Find a book by ISBN
     * @param isbn ISBN to search
     * @return Book with matching ISBN or null
     */
    Book findByISBN(String isbn);
    
    /**
     * Count books by author
     * @param author Author name
     * @return Number of books by the author
     */
    int countBooksByAuthor(String author);
}