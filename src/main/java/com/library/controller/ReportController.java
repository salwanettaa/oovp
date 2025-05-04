package com.library.controller;

import com.library.model.Book;
import com.library.model.IssuedBook;
import com.library.service.ReportService;
import com.library.service.impl.ReportServiceImpl;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for Report operations
 */
@WebServlet(name = "ReportController", urlPatterns = {"/reports/*"})
public class ReportController extends HttpServlet {

    private final ReportService reportService;
    
    public ReportController() {
        this.reportService = new ReportServiceImpl();
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
            // Show reports dashboard
            request.getRequestDispatcher("/WEB-INF/views/report/dashboard.jsp").forward(request, response);
        } else if (pathInfo.equals("/not-returned")) {
            // Show books not returned report
            List<IssuedBook> notReturnedBooks = reportService.getBooksNotReturned();
            request.setAttribute("notReturnedBooks", notReturnedBooks);
            request.getRequestDispatcher("/WEB-INF/views/report/not_returned.jsp").forward(request, response);
        } else if (pathInfo.equals("/books-by-author")) {
            // Show books by author report
            Map<String, Integer> bookCountByAuthor = reportService.getBookCountByAuthor();
            request.setAttribute("bookCountByAuthor", bookCountByAuthor);
            request.getRequestDispatcher("/WEB-INF/views/report/books_by_author.jsp").forward(request, response);
        } else if (pathInfo.equals("/books-by-field")) {
            // Show books by field report
            String field = request.getParameter("field");
            
            if (field != null && !field.isEmpty()) {
                List<Book> booksByField = reportService.getBooksByField(field);
                request.setAttribute("field", field);
                request.setAttribute("booksByField", booksByField);
            }
            
            request.getRequestDispatcher("/WEB-INF/views/report/books_by_field.jsp").forward(request, response);
        } else if (pathInfo.equals("/active-members")) {
            // Show active members report
            int limit = 10; // Default limit
            
            try {
                String limitStr = request.getParameter("limit");
                if (limitStr != null && !limitStr.isEmpty()) {
                    limit = Integer.parseInt(limitStr);
                }
            } catch (NumberFormatException e) {
                // Use default limit
            }
            
            Map<String, Integer> activeMembersMap = reportService.getMostActiveMembers(limit);
            request.setAttribute("activeMembersMap", activeMembersMap);
            request.setAttribute("limit", limit);
            request.getRequestDispatcher("/WEB-INF/views/report/active_members.jsp").forward(request, response);
        } else if (pathInfo.equals("/popular-books")) {
            // Show popular books report
            int limit = 10; // Default limit
            
            try {
                String limitStr = request.getParameter("limit");
                if (limitStr != null && !limitStr.isEmpty()) {
                    limit = Integer.parseInt(limitStr);
                }
            } catch (NumberFormatException e) {
                // Use default limit
            }
            
            Map<String, Integer> popularBooksMap = reportService.getMostPopularBooks(limit);
            request.setAttribute("popularBooksMap", popularBooksMap);
            request.setAttribute("limit", limit);
            request.getRequestDispatcher("/WEB-INF/views/report/popular_books.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Report Controller Servlet";
    }
}