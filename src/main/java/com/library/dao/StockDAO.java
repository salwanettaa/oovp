package com.library.dao;

import com.library.model.Stock;

/**
 * DAO interface for Stock entity
 */
public interface StockDAO extends BaseDAO<Stock> {
    
    /**
     * Find stock by book ID
     * @param bookId Book ID
     * @return Stock record for the book
     */
    Stock findByBookId(int bookId);
    
    /**
     * Update quantity of a book
     * @param bookId Book ID
     * @param newQty New quantity
     * @return true if successful, false otherwise
     */
    boolean updateQuantity(int bookId, int newQty);
    
    /**
     * Increase quantity of a book
     * @param bookId Book ID
     * @param amount Amount to increase
     * @return true if successful, false otherwise
     */
    boolean increaseQuantity(int bookId, int amount);
    
    /**
     * Decrease quantity of a book
     * @param bookId Book ID
     * @param amount Amount to decrease
     * @return true if successful, false otherwise
     */
    boolean decreaseQuantity(int bookId, int amount);
}