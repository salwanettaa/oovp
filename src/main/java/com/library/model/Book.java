// Book.java
package com.library.model;

/**
 * Represents a book in the library
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String field;
    private String isbn;
    private int yearOfPublish;
    private boolean availability;
    
    // Default constructor
    public Book() {
    }
    
    // Parameterized constructor
    public Book(int id, String title, String author, String field, String isbn, int yearOfPublish, boolean availability) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.field = field;
        this.isbn = isbn;
        this.yearOfPublish = yearOfPublish;
        this.availability = availability;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getField() {
        return field;
    }
    
    public void setField(String field) {
        this.field = field;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public int getYearOfPublish() {
        return yearOfPublish;
    }
    
    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }
    
    public boolean isAvailability() {
        return availability;
    }
    
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
    
    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title=" + title + ", author=" + author + ", field=" + field + 
               ", isbn=" + isbn + ", yearOfPublish=" + yearOfPublish + ", availability=" + availability + '}';
    }
}

