package com.library.dao;

import com.library.model.IssuedBook;
import java.util.List;

/**
 * DAO interface for IssuedBook entity
 */
public interface IssuedBookDAO extends BaseDAO<IssuedBook> {
    
    /**
     * Find issued books by member ID
     * @param memberId Member ID
     * @return List of books issued to the member
     */
    List<IssuedBook> findByMemberId(int memberId);
    
    /**
     * Find issued books by book ID
     * @param bookId Book ID
     * @return List of issue records for the book
     */
    List<IssuedBook> findByBookId(int bookId);
    
    /**
     * Find all books that have not been returned
     * @return List of non-returned books
     */
    List<IssuedBook> findNotReturned();
    
    /**
     * Mark a book as returned
     * @param id ID of the issued book record
     * @return true if successful, false otherwise
     */
    boolean markAsReturned(int id);
}