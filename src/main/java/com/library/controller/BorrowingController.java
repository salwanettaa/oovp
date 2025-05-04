package com.library.controller;

import com.library.model.Book;
import com.library.model.IssuedBook;
import com.library.model.Member;
import com.library.service.BookService;
import com.library.service.BorrowingService;
import com.library.service.MemberService;
import com.library.service.impl.BookServiceImpl;
import com.library.service.impl.BorrowingServiceImpl;
import com.library.service.impl.MemberServiceImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for Borrowing operations
 */
@WebServlet(name = "BorrowingController", urlPatterns = {"/borrowing/*"})
public class BorrowingController extends HttpServlet {

    private final BorrowingService borrowingService;
    private final BookService bookService;
    private final MemberService memberService;
    
    public BorrowingController() {
        this.borrowingService = new BorrowingServiceImpl();
        this.bookService = new BookServiceImpl();
        this.memberService = new MemberServiceImpl();
    }
    
    /**
     * Handles the HTTP GET method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // List all issued books
            List<IssuedBook> issuedBooks = borrowingService.getAllIssuedBooks();
            request.setAttribute("issuedBooks", issuedBooks);
            request.getRequestDispatcher("/WEB-INF/views/borrowing/list.jsp").forward(request, response);
        } else if (pathInfo.equals("/issue")) {
            // Show issue book form
            List<Book> books = bookService.getAllBooks();
            List<Member> members = memberService.getAllMembers();
            
            request.setAttribute("books", books);
            request.setAttribute("members", members);
            request.getRequestDispatcher("/WEB-INF/views/borrowing/issue.jsp").forward(request, response);
        } else if (pathInfo.equals("/return")) {
            // Show return book form
            String idStr = request.getParameter("id");
            
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    request.setAttribute("issuedBookId", id);
                    request.getRequestDispatcher("/WEB-INF/views/borrowing/return.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/borrowing");
                }
            } else {
                // Show list of non-returned books
                List<IssuedBook> notReturnedBooks = borrowingService.getNotReturnedBooks();
                request.setAttribute("notReturnedBooks", notReturnedBooks);
                request.getRequestDispatcher("/WEB-INF/views/borrowing/not_returned.jsp").forward(request, response);
            }
        } else if (pathInfo.equals("/member")) {
            // Show books issued to a member
            String idStr = request.getParameter("id");
            
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    Member member = memberService.getMemberById(id);
                    
                    if (member != null) {
                        List<IssuedBook> issuedBooks = borrowingService.getBooksByMember(id);
                        request.setAttribute("member", member);
                        request.setAttribute("issuedBooks", issuedBooks);
                        request.getRequestDispatcher("/WEB-INF/views/borrowing/member_books.jsp").forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/borrowing");
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/borrowing");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/borrowing");
            }
        } else if (pathInfo.equals("/not-returned")) {
            // Show list of non-returned books
            List<IssuedBook> notReturnedBooks = borrowingService.getNotReturnedBooks();
            request.setAttribute("notReturnedBooks", notReturnedBooks);
            request.getRequestDispatcher("/WEB-INF/views/borrowing/not_returned.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo.equals("/issue")) {
            // Handle form submission for issuing a book
            String bookIdStr = request.getParameter("bookId");
            String memberIdStr = request.getParameter("memberId");
            String daysToReturnStr = request.getParameter("daysToReturn");
            
            try {
                int bookId = Integer.parseInt(bookIdStr);
                int memberId = Integer.parseInt(memberIdStr);
                int daysToReturn = Integer.parseInt(daysToReturnStr);
                
                boolean success = borrowingService.issueBook(bookId, memberId, daysToReturn);
                
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/borrowing");
                } else {
                    request.setAttribute("error", "Failed to issue book");
                    List<Book> books = bookService.getAllBooks();
                    List<Member> members = memberService.getAllMembers();
                    
                    request.setAttribute("books", books);
                    request.setAttribute("members", members);
                    request.getRequestDispatcher("/WEB-INF/views/borrowing/issue.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid input");
                List<Book> books = bookService.getAllBooks();
                List<Member> members = memberService.getAllMembers();
                
                request.setAttribute("books", books);
                request.setAttribute("members", members);
                request.getRequestDispatcher("/WEB-INF/views/borrowing/issue.jsp").forward(request, response);
            }
        } else if (pathInfo.equals("/return")) {
            // Handle form submission for returning a book
            String issuedBookIdStr = request.getParameter("issuedBookId");
            
            try {
                int issuedBookId = Integer.parseInt(issuedBookIdStr);
                
                boolean success = borrowingService.returnBook(issuedBookId);
                
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/borrowing");
                } else {
                    request.setAttribute("error", "Failed to return book");
                    request.setAttribute("issuedBookId", issuedBookId);
                    request.getRequestDispatcher("/WEB-INF/views/borrowing/return.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/borrowing");
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Borrowing Controller Servlet";
    }
}