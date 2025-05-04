package com.library.service.impl;

import com.library.dao.MemberDAO;
import com.library.dao.impl.MemberDAOImpl;
import com.library.model.Member;
import com.library.service.MemberService;
import com.library.util.DateUtils;
import com.library.util.ValidationUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of MemberService interface
 */
public class MemberServiceImpl implements MemberService {

    private static final Logger LOGGER = Logger.getLogger(MemberServiceImpl.class.getName());
    private final MemberDAO memberDAO;
    
    public MemberServiceImpl() {
        this.memberDAO = new MemberDAOImpl();
    }
    
    @Override
    public boolean addMember(Member member) {
        // Validate input
        if (member == null) {
            LOGGER.log(Level.WARNING, "Cannot add null member");
            return false;
        }
        
        if (ValidationUtils.isEmpty(member.getName())) {
            LOGGER.log(Level.WARNING, "Member name cannot be empty");
            return false;
        }
        
        // Set registration date to current date if not set
        if (member.getDateRegister() == null) {
            member.setDateRegister(DateUtils.getCurrentDate());
        }
        
        // Save member
        return memberDAO.save(member);
    }

    @Override
    public boolean updateMember(Member member) {
        // Validate input
        if (member == null) {
            LOGGER.log(Level.WARNING, "Cannot update null member");
            return false;
        }
        
        if (member.getId() <= 0) {
            LOGGER.log(Level.WARNING, "Invalid member ID");
            return false;
        }
        
        if (ValidationUtils.isEmpty(member.getName())) {
            LOGGER.log(Level.WARNING, "Member name cannot be empty");
            return false;
        }
        
        // Check if member exists
        Member existingMember = memberDAO.findById(member.getId());
        if (existingMember == null) {
            LOGGER.log(Level.WARNING, "Member with ID {0} does not exist", member.getId());
            return false;
        }
        
        // Update member
        return memberDAO.update(member);
    }

    @Override
    public boolean deleteMember(int id) {
        if (id <= 0) {
            LOGGER.log(Level.WARNING, "Invalid member ID");
            return false;
        }
        
        // Check if member exists
        Member existingMember = memberDAO.findById(id);
        if (existingMember == null) {
            LOGGER.log(Level.WARNING, "Member with ID {0} does not exist", id);
            return false;
        }
        
        // TODO: Check if member has books not returned
        // This should be done in a transaction to avoid issues
        
        // Delete member
        return memberDAO.delete(id);
    }

    @Override
    public Member getMemberById(int id) {
        if (id <= 0) {
            LOGGER.log(Level.WARNING, "Invalid member ID");
            return null;
        }
        
        return memberDAO.findById(id);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberDAO.findAll();
    }

    @Override
    public List<Member> searchByName(String name) {
        if (ValidationUtils.isEmpty(name)) {
            LOGGER.log(Level.WARNING, "Name cannot be empty");
            return List.of();
        }
        
        return memberDAO.findByName(name);
    }
}