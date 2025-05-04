package com.library.service;

import com.library.model.Member;
import java.util.List;

/**
 * Service interface for Member operations
 */
public interface MemberService {
    
    /**
     * Add a new member
     * @param member Member to add
     * @return true if successful, false otherwise
     */
    boolean addMember(Member member);
    
    /**
     * Update an existing member
     * @param member Member to update
     * @return true if successful, false otherwise
     */
    boolean updateMember(Member member);
    
    /**
     * Delete a member
     * @param id Member ID
     * @return true if successful, false otherwise
     */
    boolean deleteMember(int id);
    
    /**
     * Get a member by ID
     * @param id Member ID
     * @return Member if found, null otherwise
     */
    Member getMemberById(int id);
    
    /**
     * Get all members
     * @return List of all members
     */
    List<Member> getAllMembers();
    
    /**
     * Search members by name
     * @param name Name to search
     * @return List of members with matching name
     */
    List<Member> searchByName(String name);
}