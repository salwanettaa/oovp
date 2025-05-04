package com.library.dao;

import java.util.List;

/**
 * Base DAO interface with common CRUD operations
 * @param <T> Type of the entity
 */
public interface BaseDAO<T> {
    
    /**
     * Save entity to database
     * @param t Entity to save
     * @return true if successful, false otherwise
     */
    boolean save(T t);
    
    /**
     * Update entity in database
     * @param t Entity to update
     * @return true if successful, false otherwise
     */
    boolean update(T t);
    
    /**
     * Delete entity from database
     * @param id ID of the entity to delete
     * @return true if successful, false otherwise
     */
    boolean delete(int id);
    
    /**
     * Find entity by ID
     * @param id ID of the entity
     * @return Entity if found, null otherwise
     */
    T findById(int id);
    
    /**
     * Find all entities
     * @return List of all entities
     */
    List<T> findAll();
}
