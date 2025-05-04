package com.library.service.impl;

import com.library.dao.BookDAO;
import com.library.dao.IssuedBookDAO;
import com.library.dao.MemberDAO;
import com.library.dao.StockDAO;
import com.library.dao.impl.BookDAOImpl;
import com.library.dao.impl.IssuedBookDAOImpl;
import com.library.dao.impl.MemberDAOImpl;
import com.library.dao.impl.StockDAOImpl;
import com.library.model.Book;
import com.library.model.IssuedBook;
import com.library.model.Member;
import com.library.model.Stock;
import com.library.service.BorrowingService;
import com.library.util.DateUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of BorrowingService interface
 */
public class BorrowingServiceImpl implements BorrowingService {

    private static final Logger LOGGER = Logger.getLogger(BorrowingServiceImpl.class.getName());
    private final BookDAO bookDAO;
    private final MemberDAO memberDAO;
    private final IssuedBookDAO issuedBookDAO;
    private final StockDAO stockDAO;
    
    public BorrowingServiceImpl() {
        this.bookDAO = new BookDAOImpl();
        this.memberDAO = new MemberDAOImpl();
        this.issuedBookDAO = new IssuedBookDAOImpl();
        this.stockDAO = new StockDAOImpl();
    }
    
    @Override
    public boolean issueBook(int bookId, int memberId, int daysToReturn) {
        // Validate input
        if (bookId <= 0 || memberId <= 0 || daysToReturn <= 0) {
            LOGGER.log(Level.WARNING, "Invalid input parameters");
            return false;
        }
        
        // Check if book exists
        Book book = bookDAO.findById(bookId);
        if (book == null) {
            LOGGER.log(Level.WARNING, "Book with ID {0} does not exist", bookId);
            return false;
        }
        
        // Check if member exists
        Member member = memberDAO.findById(memberId);
        if (member == null) {
            LOGGER.log(Level.WARNING, "Member with ID {0} does not exist", memberId);
            return false;
        }
        
        // Check if book is available in stock
        Stock stock = stockDAO.findByBookId(bookId);
        if (stock == null || stock.getQty() <= 0) {
            LOGGER.log(Level.WARNING, "Book with ID {0} is not available in stock", bookId);
            return false;
        }
        
        // Reduce stock
        boolean stockUpdated = stockDAO.decreaseQuantity(bookId, 1);
        if (!stockUpdated) {
            LOGGER.log(Level.SEVERE, "Failed to update stock for book ID {0}", bookId);
            return false;
        }
        
        // Update book availability if stock is zero
        if (stock.getQty() == 1) { // It will be 0 after decrement
            book.setAvailability(false);
            bookDAO.update(book);
        }
        
        // Create issued book record
        IssuedBook issuedBook = new IssuedBook();
        issuedBook.setBookId(bookId);
        issuedBook.setMemberId(memberId);
        issuedBook.setDateIssued(DateUtils.getCurrentDate());
        issuedBook.setDateReturn(DateUtils.addDays(DateUtils.getCurrentDate(), daysToReturn));
        issuedBook.setReturned(false);
        
        return issuedBookDAO.save(issuedBook);
    }

    @Override
    public boolean returnBook(int issuedBookId) {
        // Validate input
        if (issuedBookId <= 0) {
            LOGGER.log(Level.WARNING, "Invalid issued book ID");
            return false;
        }
        
        // Check if issued book record exists
        IssuedBook issuedBook = issuedBookDAO.findById(issuedBookId);
        if (issuedBook == null) {
            LOGGER.log(Level.WARNING, "Issued book record with ID {0} does not exist", issuedBookId);
            return false;
        }
        
        // Check if book is already returned
        if (issuedBook.isReturned()) {
            LOGGER.log(Level.WARNING, "Book is already returned");
            return false;
        }
        
        // Mark as returned
        boolean returned = issuedBookDAO.markAsReturned(issuedBookId);
        if (!returned) {
            LOGGER.log(Level.SEVERE, "Failed to mark book as returned");
            return false;
        }
        
        // Increase stock
        boolean stockUpdated = stockDAO.increaseQuantity(issuedBook.getBookId(), 1);
        if (!stockUpdated) {
            LOGGER.log(Level.SEVERE, "Failed to update stock for book ID {0}", issuedBook.getBookId());
            // Rollback return
            issuedBook.setReturned(false);
            issuedBookDAO.update(issuedBook);
            return false;
        }
        
        // Update book availability
        Book book = bookDAO.findById(issuedBook.getBookId());
        if (book != null && !book.isAvailability()) {
            book.setAvailability(true);
            bookDAO.update(book);
        }
        
        return true;
    }

    @Override
    public List<IssuedBook> getAllIssuedBooks() {
        return issuedBookDAO.findAll();
    }

    @Override
    public List<IssuedBook> getNotReturnedBooks() {
        return issuedBookDAO.findNotReturned();
    }

    @Override
    public List<IssuedBook> getBooksByMember(int memberId) {
        if (memberId <= 0) {
            LOGGER.log(Level.WARNING, "Invalid member ID");
            return List.of();
        }
        
        return issuedBookDAO.findByMemberId(memberId);
    }
}