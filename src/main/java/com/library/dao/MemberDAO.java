package com.library.dao;

import com.library.model.Member;
import java.util.List;

/**
 * DAO interface for Member entity
 */
public interface MemberDAO extends BaseDAO<Member> {
    
    /**
     * Find members by name
     * @param name Name to search
     * @return List of members with matching name
     */
    List<Member> findByName(String name);
}