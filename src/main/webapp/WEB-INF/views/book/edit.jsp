<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System - Edit Book</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <!-- Navigation -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Library Management System</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/books">Books</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/members">Members</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/borrowing">Borrowing</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/reports">Reports</a>
                    </li>
                </ul>
            </div>
        </nav>
        
        <h1>Edit Book</h1>
        
        <!-- Error Alert -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        <!-- Edit Book Form -->
        <div class="card">
            <div class="card-header">
                <h5>Book Information</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/books/update" method="post">
                    <input type="hidden" name="id" value="${book.id}">
                    
                    <div class="form-group">
                        <label for="title">Title</label>
                        <input type="text" class="form-control" id="title" name="title" value="${book.title}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="author">Author</label>
                        <input type="text" class="form-control" id="author" name="author" value="${book.author}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="field">Field/Category</label>
                        <input type="text" class="form-control" id="field" name="field" value="${book.field}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="isbn">ISBN</label>
                        <input type="text" class="form-control" id="isbn" name="isbn" value="${book.isbn}" required
                               pattern="^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$">
                        <small class="form-text text-muted">Valid ISBN format (e.g., 978-3-16-148410-0)</small>
                    </div>
                    
                    <div class="form-group">
                        <label for="yearOfPublish">Year of Publication</label>
                        <input type="number" class="form-control" id="yearOfPublish" name="yearOfPublish" value="${book.yearOfPublish}" 
                               min="1000" max="2100" required>
                    </div>
                    
                    <div class="form-group">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="availability" name="availability" ${book.availability ? 'checked' : ''}>
                            <label class="form-check-label" for="availability">
                                Book is available
                            </label>
                        </div>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">Update Book</button>
                    <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Cancel</a>
                </form>
            </div>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>