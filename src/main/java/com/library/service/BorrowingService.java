package com.library.service;

import com.library.model.IssuedBook;
import java.util.List;

/**
 * Service interface for Borrowing operations
 */
public interface BorrowingService {
    
    /**
     * Issue a book to a member
     * @param bookId Book ID
     * @param memberId Member ID
     * @param daysToReturn Number of days allowed for return
     * @return true if successful, false otherwise
     */
    boolean issueBook(int bookId, int memberId, int daysToReturn);
    
    /**
     * Return a book
     * @param issuedBookId Issued Book ID
     * @return true if successful, false otherwise
     */
    boolean returnBook(int issuedBookId);
    
    /**
     * Get all issued books
     * @return List of all issued books
     */
    List<IssuedBook> getAllIssuedBooks();
    
    /**
     * Get not returned books
     * @return List of books not returned yet
     */
    List<IssuedBook> getNotReturnedBooks();
    
    /**
     * Get books issued to a member
     * @param memberId Member ID
     * @return List of books issued to the member
     */
    List<IssuedBook> getBooksByMember(int memberId);
}