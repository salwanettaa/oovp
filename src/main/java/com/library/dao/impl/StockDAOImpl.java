package com.library.dao.impl;

import com.library.dao.StockDAO;
import com.library.model.Stock;
import com.library.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of StockDAO interface
 */
public class StockDAOImpl implements StockDAO {

    private static final Logger LOGGER = Logger.getLogger(StockDAOImpl.class.getName());
    
    @Override
    public boolean save(Stock stock) {
        String sql = "INSERT INTO stock (book_id, qty) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, stock.getBookId());
            stmt.setInt(2, stock.getQty());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        stock.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving stock", e);
            return false;
        }
    }

    @Override
    public boolean update(Stock stock) {
        String sql = "UPDATE stock SET book_id = ?, qty = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, stock.getBookId());
            stmt.setInt(2, stock.getQty());
            stmt.setInt(3, stock.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating stock", e);
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM stock WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting stock", e);
            return false;
        }
    }

    @Override
    public Stock findById(int id) {
        String sql = "SELECT s.*, b.title as book_title FROM stock s JOIN books b ON s.book_id = b.id WHERE s.id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractStockFromResultSet(rs);
                }
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding stock by ID", e);
        }
        
        return null;
    }

    @Override
    public List<Stock> findAll() {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT s.*, b.title as book_title FROM stock s JOIN books b ON s.book_id = b.id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                stocks.add(extractStockFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding all stocks", e);
        }
        
        return stocks;
    }

    @Override
    public Stock findByBookId(int bookId) {
        String sql = "SELECT s.*, b.title as book_title FROM stock s JOIN books b ON s.book_id = b.id WHERE s.book_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractStockFromResultSet(rs);
                }
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding stock by book ID", e);
        }
        
        return null;
    }

    @Override
    public boolean updateQuantity(int bookId, int newQty) {
        String sql = "UPDATE stock SET qty = ? WHERE book_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, newQty);
            stmt.setInt(2, bookId);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                // If no rows affected, the stock record might not exist yet
                Stock newStock = new Stock();
                newStock.setBookId(bookId);
                newStock.setQty(newQty);
                return save(newStock);
            }
            
            return true;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating quantity", e);
            return false;
        }
    }

    @Override
    public boolean increaseQuantity(int bookId, int amount) {
        Stock stock = findByBookId(bookId);
        
        if (stock != null) {
            return updateQuantity(bookId, stock.getQty() + amount);
        } else {
            // If stock record doesn't exist, create a new one
            Stock newStock = new Stock();
            newStock.setBookId(bookId);
            newStock.setQty(amount);
            return save(newStock);
        }
    }

    @Override
    public boolean decreaseQuantity(int bookId, int amount) {
        Stock stock = findByBookId(bookId);
        
        if (stock != null) {
            int newQty = stock.getQty() - amount;
            if (newQty < 0) {
                LOGGER.log(Level.WARNING, "Cannot decrease quantity below zero");
                return false;
            }
            
            return updateQuantity(bookId, newQty);
        }
        
        return false;
    }
    
    /**
     * Helper method to extract a Stock object from a ResultSet
     * @param rs ResultSet containing stock data
     * @return Stock object
     * @throws SQLException if an error occurs
     */
    private Stock extractStockFromResultSet(ResultSet rs) throws SQLException {
        Stock stock = new Stock();
        stock.setId(rs.getInt("id"));
        stock.setBookId(rs.getInt("book_id"));
        stock.setQty(rs.getInt("qty"));
        
        // Set additional properties if available
        try {
            stock.setBookTitle(rs.getString("book_title"));
        } catch (SQLException e) {
            // Ignore if the column doesn't exist
        }
        
        return stock;
    }
}