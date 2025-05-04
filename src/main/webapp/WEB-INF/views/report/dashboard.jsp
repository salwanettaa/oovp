<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System - Reports</title>
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
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/books">Books</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/members">Members</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/borrowing">Borrowing</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/reports">Reports</a>
                    </li>
                </ul>
            </div>
        </nav>
        
        <h1>Library Reports</h1>
        
        <!-- Reports List -->
        <div class="row mt-4">
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header">
                        <h5>Borrowing Reports</h5>
                    </div>
                    <div class="card-body">
                        <p>View reports related to book borrowing activities.</p>
                        <a href="${pageContext.request.contextPath}/reports/not-returned" class="btn btn-warning mb-2">Not Returned Books</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header">
                        <h5>Book Reports</h5>
                    </div>
                    <div class="card-body">
                        <p>View reports related to books and their statistics.</p>
                        <a href="${pageContext.request.contextPath}/reports/books-by-author" class="btn btn-info mb-2">Books by Author</a>
                        <a href="${pageContext.request.contextPath}/reports/books-by-field" class="btn btn-info mb-2 ml-2">Books by Field</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header">
                        <h5>Member Reports</h5>
                    </div>
                    <div class="card-body">
                        <p>View reports related to library members.</p>
                        <a href="${pageContext.request.contextPath}/reports/active-members" class="btn btn-success mb-2">Most Active Members</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header">
                        <h5>Popularity Reports</h5>
                    </div>
                    <div class="card-body">
                        <p>View reports about the most popular books in the library.</p>
                        <a href="${pageContext.request.contextPath}/reports/popular-books" class="btn btn-primary mb-2">Most Popular Books</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>