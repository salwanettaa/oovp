package com.library.service.impl;

import com.library.dao.BookDAO;
import com.library.dao.IssuedBookDAO;
import com.library.dao.MemberDAO;
import com.library.dao.impl.BookDAOImpl;
import com.library.dao.impl.IssuedBookDAOImpl;
import com.library.dao.impl.MemberDAOImpl;
import com.library.model.Book;
import com.library.model.IssuedBook;
import com.library.model.Member;
import com.library.service.ReportService;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of ReportService interface
 */
public class ReportServiceImpl implements ReportService {

    private final BookDAO bookDAO;
    private final MemberDAO memberDAO;
    private final IssuedBookDAO issuedBookDAO;
    
    public ReportServiceImpl() {
        this.bookDAO = new BookDAOImpl();
        this.memberDAO = new MemberDAOImpl();
        this.issuedBookDAO = new IssuedBookDAOImpl();
    }
    
    @Override
    public List<IssuedBook> getBooksNotReturned() {
        return issuedBookDAO.findNotReturned();
    }

    @Override
    public Map<String, Integer> getBookCountByAuthor() {
        List<Book> allBooks = bookDAO.findAll();
        Map<String, Integer> countByAuthor = new HashMap<>();
        
        for (Book book : allBooks) {
            String author = book.getAuthor();
            countByAuthor.put(author, countByAuthor.getOrDefault(author, 0) + 1);
        }
        
        // Sort by count in descending order
        return countByAuthor.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        HashMap::new
                ));
    }

    @Override
    public List<Book> getBooksByField(String field) {
        return bookDAO.findByField(field);
    }

    @Override
    public Map<String, Integer> getMostActiveMembers(int limit) {
        List<IssuedBook> allIssuedBooks = issuedBookDAO.findAll();
        Map<Integer, Integer> countByMemberId = new HashMap<>();
        
        // Count books issued by each member
        for (IssuedBook issuedBook : allIssuedBooks) {
            int memberId = issuedBook.getMemberId();
            countByMemberId.put(memberId, countByMemberId.getOrDefault(memberId, 0) + 1);
        }
        
        // Get member names and sort by count
        Map<String, Integer> countByMemberName = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : countByMemberId.entrySet()) {
            Member member = memberDAO.findById(entry.getKey());
            if (member != null) {
                countByMemberName.put(member.getName(), entry.getValue());
            }
        }
        
        // Sort by count in descending order and limit results
        return countByMemberName.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        HashMap::new
                ));
    }

    @Override
    public Map<String, Integer> getMostPopularBooks(int limit) {
        List<IssuedBook> allIssuedBooks = issuedBookDAO.findAll();
        Map<Integer, Integer> countByBookId = new HashMap<>();
        
        // Count how many times each book has been borrowed
        for (IssuedBook issuedBook : allIssuedBooks) {
            int bookId = issuedBook.getBookId();
            countByBookId.put(bookId, countByBookId.getOrDefault(bookId, 0) + 1);
        }
        
        // Get book titles and sort by count
        Map<String, Integer> countByBookTitle = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : countByBookId.entrySet()) {
            Book book = bookDAO.findById(entry.getKey());
            if (book != null) {
                countByBookTitle.put(book.getTitle(), entry.getValue());
            }
        }
        
        // Sort by count in descending order and limit results
        return countByBookTitle.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        HashMap::new
                ));
    }
}