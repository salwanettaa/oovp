package com.library.controller;

import com.library.model.Book;
import com.library.service.BookService;
import com.library.service.impl.BookServiceImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for Book operations
 */
@WebServlet(name = "BookController", urlPatterns = {"/books/*"})
public class BookController extends HttpServlet {

    private final BookService bookService;
    
    public BookController() {
        this.bookService = new BookServiceImpl();
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
            // List all books
            List<Book> books = bookService.getAllBooks();
            request.setAttribute("books", books);
            request.getRequestDispatcher("/WEB-INF/views/book/list.jsp").forward(request, response);
        } else if (pathInfo.equals("/add")) {
            // Show add book form
            request.getRequestDispatcher("/WEB-INF/views/book/add.jsp").forward(request, response);
        } else if (pathInfo.equals("/edit")) {
            // Show edit book form
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Book book = bookService.getBookById(id);
                
                if (book != null) {
                    request.setAttribute("book", book);
                    request.getRequestDispatcher("/WEB-INF/views/book/edit.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/books");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/books");
            }
        } else if (pathInfo.equals("/search")) {
            // Handle search
            String searchType = request.getParameter("type");
            String searchTerm = request.getParameter("term");
            
            List<Book> books;
            
            if (searchType != null && searchTerm != null && !searchTerm.isEmpty()) {
                switch (searchType) {
                    case "title":
                        books = bookService.searchByTitle(searchTerm);
                        break;
                    case "author":
                        books = bookService.searchByAuthor(searchTerm);
                        break;
                    case "field":
                        books = bookService.searchByField(searchTerm);
                        break;
                    case "year":
                        try {
                            int year = Integer.parseInt(searchTerm);
                            books = bookService.searchByYear(year);
                        } catch (NumberFormatException e) {
                            books = bookService.getAllBooks();
                        }
                        break;
                    default:
                        books = bookService.getAllBooks();
                }
            } else {
                books = bookService.getAllBooks();
            }
            
            request.setAttribute("books", books);
            request.setAttribute("searchType", searchType);
            request.setAttribute("searchTerm", searchTerm);
            request.getRequestDispatcher("/WEB-INF/views/book/list.jsp").forward(request, response);
        } else {
            // Show book details
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                Book book = bookService.getBookById(id);
                
                if (book != null) {
                    request.setAttribute("book", book);
                    request.getRequestDispatcher("/WEB-INF/views/book/details.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/books");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/books");
            }
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
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Handle form submission for adding a book
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String field = request.getParameter("field");
            String isbn = request.getParameter("isbn");
            String yearStr = request.getParameter("yearOfPublish");
            String qtyStr = request.getParameter("quantity");
            
            try {
                int year = Integer.parseInt(yearStr);
                int qty = Integer.parseInt(qtyStr);
                
                Book book = new Book();
                book.setTitle(title);
                book.setAuthor(author);
                book.setField(field);
                book.setIsbn(isbn);
                book.setYearOfPublish(year);
                book.setAvailability(qty > 0);
                
                boolean success = bookService.addBook(book, qty);
                
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/books");
                } else {
                    request.setAttribute("book", book);
                    request.setAttribute("error", "Failed to add book");
                    request.getRequestDispatcher("/WEB-INF/views/book/add.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid year or quantity");
                request.getRequestDispatcher("/WEB-INF/views/book/add.jsp").forward(request, response);
            }
        } else if (pathInfo.equals("/update")) {
            // Handle form submission for updating a book
            String idStr = request.getParameter("id");
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String field = request.getParameter("field");
            String isbn = request.getParameter("isbn");
            String yearStr = request.getParameter("yearOfPublish");
            String availabilityStr = request.getParameter("availability");
            
            try {
                int id = Integer.parseInt(idStr);
                int year = Integer.parseInt(yearStr);
                boolean availability = "on".equals(availabilityStr) || "true".equals(availabilityStr);
                
                Book book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setAuthor(author);
                book.setField(field);
                book.setIsbn(isbn);
                book.setYearOfPublish(year);
                book.setAvailability(availability);
                
                boolean success = bookService.updateBook(book);
                
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/books");
                } else {
                    request.setAttribute("book", book);
                    request.setAttribute("error", "Failed to update book");
                    request.getRequestDispatcher("/WEB-INF/views/book/edit.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/books");
            }
        } else if (pathInfo.equals("/delete")) {
            // Handle book deletion
            String idStr = request.getParameter("id");
            
            try {
                int id = Integer.parseInt(idStr);
                bookService.deleteBook(id);
            } catch (NumberFormatException e) {
                // Ignore
            }
            
            response.sendRedirect(request.getContextPath() + "/books");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Book Controller Servlet";
    }
}