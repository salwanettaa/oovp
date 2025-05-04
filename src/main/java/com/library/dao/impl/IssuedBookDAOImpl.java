package com.library.dao.impl;

import com.library.dao.IssuedBookDAO;
import com.library.model.IssuedBook;
import com.library.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of IssuedBookDAO interface
 */
public class IssuedBookDAOImpl implements IssuedBookDAO {

    private static final Logger LOGGER = Logger.getLogger(IssuedBookDAOImpl.class.getName());
    
    @Override
    public boolean save(IssuedBook issuedBook) {
        String sql = "INSERT INTO issued_books (book_id, member_id, date_issued, date_return, is_returned) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, issuedBook.getBookId());
            stmt.setInt(2, issuedBook.getMemberId());
            stmt.setDate(3, issuedBook.getDateIssued());
            stmt.setDate(4, issuedBook.getDateReturn());
            stmt.setBoolean(5, issuedBook.isReturned());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        issuedBook.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving issued book", e);
            return false;
        }
    }

    @Override
    public boolean update(IssuedBook issuedBook) {
        String sql = "UPDATE issued_books SET book_id = ?, member_id = ?, date_issued = ?, date_return = ?, is_returned = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, issuedBook.getBookId());
            stmt.setInt(2, issuedBook.getMemberId());
            stmt.setDate(3, issuedBook.getDateIssued());
            stmt.setDate(4, issuedBook.getDateReturn());
            stmt.setBoolean(5, issuedBook.isReturned());
            stmt.setInt(6, issuedBook.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating issued book", e);
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM issued_books WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting issued book", e);
            return false;
        }
    }

    @Override
    public IssuedBook findById(int id) {
        String sql = "SELECT ib.*, b.title as book_title, m.name as member_name " +
                     "FROM issued_books ib " +
                     "JOIN books b ON ib.book_id = b.id " +
                     "JOIN members m ON ib.member_id = m.id " +
                     "WHERE ib.id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractIssuedBookFromResultSet(rs);
                }
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding issued book by ID", e);
        }
        
        return null;
    }

    @Override
    public List<IssuedBook> findAll() {
        List<IssuedBook> issuedBooks = new ArrayList<>();
        String sql = "SELECT ib.*, b.title as book_title, m.name as member_name " +
                     "FROM issued_books ib " +
                     "JOIN books b ON ib.book_id = b.id " +
                     "JOIN members m ON ib.member_id = m.id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                issuedBooks.add(extractIssuedBookFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding all issued books", e);
        }
        
        return issuedBooks;
    }

    @Override
    public List<IssuedBook> findByMemberId(int memberId) {
        List<IssuedBook> issuedBooks = new ArrayList<>();
        String sql = "SELECT ib.*, b.title as book_title, m.name as member_name " +
                     "FROM issued_books ib " +
                     "JOIN books b ON ib.book_id = b.id " +
                     "JOIN members m ON ib.member_id = m.id " +
                     "WHERE ib.member_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, memberId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    issuedBooks.add(extractIssuedBookFromResultSet(rs));
                }
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding issued books by member ID", e);
        }
        
        return issuedBooks;
    }

    @Override
    public List<IssuedBook> findByBookId(int bookId) {
        List<IssuedBook> issuedBooks = new ArrayList<>();
        String sql = "SELECT ib.*, b.title as book_title, m.name as member_name " +
                     "FROM issued_books ib " +
                     "JOIN books b ON ib.book_id = b.id " +
                     "JOIN members m ON ib.member_id = m.id " +
                     "WHERE ib.book_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    issuedBooks.add(extractIssuedBookFromResultSet(rs));
                }
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding issued books by book ID", e);
        }
        
        return issuedBooks;
    }

    @Override
    public List<IssuedBook> findNotReturned() {
        List<IssuedBook> issuedBooks = new ArrayList<>();
        String sql = "SELECT ib.*, b.title as book_title, m.name as member_name " +
                     "FROM issued_books ib " +
                     "JOIN books b ON ib.book_id = b.id " +
                     "JOIN members m ON ib.member_id = m.id " +
                     "WHERE ib.is_returned = FALSE";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                issuedBooks.add(extractIssuedBookFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding not returned books", e);
        }
        
        return issuedBooks;
    }

    @Override
    public boolean markAsReturned(int id) {
        String sql = "UPDATE issued_books SET is_returned = TRUE WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error marking book as returned", e);
            return false;
        }
    }
    
    /**
     * Helper method to extract an IssuedBook object from a ResultSet
     * @param rs ResultSet containing issued book data
     * @return IssuedBook object
     * @throws SQLException if an error occurs
     */
    private IssuedBook extractIssuedBookFromResultSet(ResultSet rs) throws SQLException {
        IssuedBook issuedBook = new IssuedBook();
        issuedBook.setId(rs.getInt("id"));
        issuedBook.setBookId(rs.getInt("book_id"));
        issuedBook.setMemberId(rs.getInt("member_id"));
        issuedBook.setDateIssued(rs.getDate("date_issued"));
        issuedBook.setDateReturn(rs.getDate("date_return"));
        issuedBook.setReturned(rs.getBoolean("is_returned"));
        
        // Set additional properties if available
        try {
            issuedBook.setBookTitle(rs.getString("book_title"));
            issuedBook.setMemberName(rs.getString("member_name"));
        } catch (SQLException e) {
            // Ignore if the columns don't exist
        }
        
        return issuedBook;
    }
}