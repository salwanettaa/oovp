package com.library.model;

import java.sql.Date;

/**
 * Represents a book issued to a member
 */
public class IssuedBook {
    private int id;
    private int bookId;
    private int memberId;
    private Date dateIssued;
    private Date dateReturn;
    private boolean isReturned;
    
    // Additional properties for displaying information
    private String bookTitle;
    private String memberName;
    
    // Default constructor
    public IssuedBook() {
    }
    
    // Parameterized constructor
    public IssuedBook(int id, int bookId, int memberId, Date dateIssued, Date dateReturn, boolean isReturned) {
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.dateIssued = dateIssued;
        this.dateReturn = dateReturn;
        this.isReturned = isReturned;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getBookId() {
        return bookId;
    }
    
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    public int getMemberId() {
        return memberId;
    }
    
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    
    public Date getDateIssued() {
        return dateIssued;
    }
    
    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }
    
    public Date getDateReturn() {
        return dateReturn;
    }
    
    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }
    
    public boolean isReturned() {
        return isReturned;
    }
    
    public void setReturned(boolean isReturned) {
        this.isReturned = isReturned;
    }
    
    public String getBookTitle() {
        return bookTitle;
    }
    
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    
    public String getMemberName() {
        return memberName;
    }
    
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    
    @Override
    public String toString() {
        return "IssuedBook{" + "id=" + id + ", bookId=" + bookId + ", memberId=" + memberId + 
               ", dateIssued=" + dateIssued + ", dateReturn=" + dateReturn + ", isReturned=" + isReturned + '}';
    }
}