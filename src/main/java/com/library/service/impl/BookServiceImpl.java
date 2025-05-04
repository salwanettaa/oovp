package com.library.service.impl;

import com.library.dao.BookDAO;
import com.library.dao.StockDAO;
import com.library.dao.impl.BookDAOImpl;
import com.library.dao.impl.StockDAOImpl;
import com.library.model.Book;
import com.library.model.Stock;
import com.library.service.BookService;
import com.library.util.ValidationUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of BookService interface
 */
public class BookServiceImpl implements BookService {

    private static final Logger LOGGER = Logger.getLogger(BookServiceImpl.class.getName());
    private final BookDAO bookDAO;
    private final StockDAO stockDAO;
    
    public BookServiceImpl() {
        this.bookDAO = new BookDAOImpl();
        this.stockDAO = new StockDAOImpl();
    }
    
    @Override
    public boolean addBook(Book book, int quantity) {
        // Validate input
        if (book == null) {
            LOGGER.log(Level.WARNING, "Cannot add null book");
            return false;
        }
        
        if (ValidationUtils.isEmpty(book.getTitle())) {
            LOGGER.log(Level.WARNING, "Book title cannot be empty");
            return false;
        }
        
        if (ValidationUtils.isEmpty(book.getAuthor())) {
            LOGGER.log(Level.WARNING, "Book author cannot be empty");
            return false;
        }
        
        if (ValidationUtils.isEmpty(book.getIsbn())) {
            LOGGER.log(Level.WARNING, "Book ISBN cannot be empty");
            return false;
        }
        
        if (!ValidationUtils.isValidISBN(book.getIsbn())) {
            LOGGER.log(Level.WARNING, "Invalid ISBN format");
            return false;
        }
        
        if (!ValidationUtils.isValidPublishYear(book.getYearOfPublish())) {
            LOGGER.log(Level.WARNING, "Invalid publish year");
            return false;
        }
        
        if (!ValidationUtils.isValidQuantity(quantity)) {
            LOGGER.log(Level.WARNING, "Invalid quantity");
            return false;
        }
        
        // Check if ISBN already exists
        Book existingBook = bookDAO.findByISBN(book.getIsbn());
        if (existingBook != null) {
            LOGGER.log(Level.WARNING, "Book with ISBN {0} already exists", book.getIsbn());
            return false;
        }
        
        // Save book
        boolean bookSaved = bookDAO.save(book);
        
        if (bookSaved) {
            // Create stock entry
            Stock stock = new Stock();
            stock.setBookId(book.getId());
            stock.setQty(quantity);
            
            boolean stockSaved = stockDAO.save(stock);
            if (!stockSaved) {
                LOGGER.log(Level.SEVERE, "Failed to save stock for book ID {0}", book.getId());
                // Rollback book creation
                bookDAO.delete(book.getId());
                return false;
            }
            
            return true;
        }
        
        return false;
    }

    @Override
    public boolean updateBook(Book book) {
        // Validate input
        if (book == null) {
            LOGGER.log(Level.WARNING, "Cannot update null book");
            return false;
        }
        
        if (book.getId() <= 0) {
            LOGGER.log(Level.WARNING, "Invalid book ID");
            return false;
        }
        
        if (ValidationUtils.isEmpty(book.getTitle())) {
            LOGGER.log(Level.WARNING, "Book title cannot be empty");
            return false;
        }
        
        if (ValidationUtils.isEmpty(book.getAuthor())) {
            LOGGER.log(Level.WARNING, "Book author cannot be empty");
            return false;
        }
        
        if (ValidationUtils.isEmpty(book.getIsbn())) {
            LOGGER.log(Level.WARNING, "Book ISBN cannot be empty");
            return false;
        }
        
        if (!ValidationUtils.isValidISBN(book.getIsbn())) {
            LOGGER.log(Level.WARNING, "Invalid ISBN format");
            return false;
        }
        
        if (!ValidationUtils.isValidPublishYear(book.getYearOfPublish())) {
            LOGGER.log(Level.WARNING, "Invalid publish year");
            return false;
        }
        
        // Check if another book already has this ISBN
        Book existingBook = bookDAO.findByISBN(book.getIsbn());
        if (existingBook != null && existingBook.getId() != book.getId()) {
            LOGGER.log(Level.WARNING, "Another book with ISBN {0} already exists", book.getIsbn());
            return false;
        }
        
        // Update book
        return bookDAO.update(book);
    }

    @Override
    public boolean deleteBook(int id) {
        if (id <= 0) {
            LOGGER.log(Level.WARNING, "Invalid book ID");
            return false;
        }
        
        // First delete stock
        Stock stock = stockDAO.findByBookId(id);
        if (stock != null) {
            stockDAO.delete(stock.getId());
        }
        
        // Then delete book
        return bookDAO.delete(id);
    }

    @Override
    public Book getBookById(int id) {
        if (id <= 0) {
            LOGGER.log(Level.WARNING, "Invalid book ID");
            return null;
        }
        
        return bookDAO.findById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDAO.findAll();
    }

    @Override
    public List<Book> searchByTitle(String title) {
        if (ValidationUtils.isEmpty(title)) {
            LOGGER.log(Level.WARNING, "Title cannot be empty");
            return List.of();
        }
        
        return bookDAO.findByTitle(title);
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        if (ValidationUtils.isEmpty(author)) {
            LOGGER.log(Level.WARNING, "Author cannot be empty");
            return List.of();
        }
        
        return bookDAO.findByAuthor(author);
    }

    @Override
    public List<Book> searchByField(String field) {
        if (ValidationUtils.isEmpty(field)) {
            LOGGER.log(Level.WARNING, "Field cannot be empty");
            return List.of();
        }
        
        return bookDAO.findByField(field);
    }

    @Override
    public List<Book> searchByYear(int year) {
        if (!ValidationUtils.isValidPublishYear(year)) {
            LOGGER.log(Level.WARNING, "Invalid publish year");
            return List.of();
        }
        
        return bookDAO.findByYear(year);
    }

    @Override
    public int countBooksByAuthor(String author) {
        if (ValidationUtils.isEmpty(author)) {
            LOGGER.log(Level.WARNING, "Author cannot be empty");
            return 0;
        }
        
        return bookDAO.countBooksByAuthor(author);
    }
}