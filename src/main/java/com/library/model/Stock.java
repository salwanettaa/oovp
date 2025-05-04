package com.library.model;

/**
 * Represents the stock of a book
 */
public class Stock {
    private int id;
    private int bookId;
    private int qty;
    
    // Additional property for displaying information
    private String bookTitle;
    
    // Default constructor
    public Stock() {
    }
    
    // Parameterized constructor
    public Stock(int id, int bookId, int qty) {
        this.id = id;
        this.bookId = bookId;
        this.qty = qty;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getBookId() {
        return bookId;
    }
    
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    public int getQty() {
        return qty;
    }
    
    public void setQty(int qty) {
        this.qty = qty;
    }
    
    public String getBookTitle() {
        return bookTitle;
    }
    
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    
    @Override
    public String toString() {
        return "Stock{" + "id=" + id + ", bookId=" + bookId + ", qty=" + qty + '}';
    }
}