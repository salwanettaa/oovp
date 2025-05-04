package com.library.service;

import com.library.model.Book;
import java.util.List;

/**
 * Service interface for Book operations
 */
public interface BookService {
    
    /**
     * Add a new book
     * @param book Book to add
     * @param quantity Initial quantity
     * @return true if successful, false otherwise
     */
    boolean addBook(Book book, int quantity);
    
    /**
     * Update an existing book
     * @param book Book to update
     * @return true if successful, false otherwise
     */
    boolean updateBook(Book book);
    
    /**
     * Delete a book
     * @param id Book ID
     * @return true if successful, false otherwise
     */
    boolean deleteBook(int id);
    
    /**
     * Get a book by ID
     * @param id Book ID
     * @return Book if found, null otherwise
     */
    Book getBookById(int id);
    
    /**
     * Get all books
     * @return List of all books
     */
    List<Book> getAllBooks();
    
    /**
     * Search books by title
     * @param title Title to search
     * @return List of books matching the title
     */
    List<Book> searchByTitle(String title);
    
    /**
     * Search books by author
     * @param author Author to search
     * @return List of books by the author
     */
    List<Book> searchByAuthor(String author);
    
    /**
     * Search books by field/category
     * @param field Field to search
     * @return List of books in the field
     */
    List<Book> searchByField(String field);
    
    /**
     * Search books by year of publication
     * @param year Year to search
     * @return List of books published in that year
     */
    List<Book> searchByYear(int year);
    
    /**
     * Count books by author
     * @param author Author name
     * @return Number of books by the author
     */
    int countBooksByAuthor(String author);
}