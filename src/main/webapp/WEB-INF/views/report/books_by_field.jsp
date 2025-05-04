<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System - Books by Field Report</title>
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
        
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Books by Field Report</h1>
            <a href="${pageContext.request.contextPath}/reports" class="btn btn-secondary">Back to Reports</a>
        </div>
        
        <!-- Field Selection Form -->
        <div class="card mb-4">
            <div class="card-header">
                <h5>Select Field/Category</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/reports/books-by-field" method="get" class="form-inline">
                    <input type="text" name="field" class="form-control mr-2" value="${field}" placeholder="Enter field/category" required>
                    <button type="submit" class="btn btn-primary">Show Books</button>
                </form>
            </div>
        </div>
        
        <!-- Books List -->
        <c:if test="${not empty field}">
            <div class="card">
                <div class="card-header">
                    <h5>Books in "${field}" Category</h5>
                </div>
                <div class="card-body">
                    <c:if test="${empty booksByField}">
                        <div class="alert alert-info">No books found in this category.</div>
                    </c:if>
                    
                    <c:if test="${not empty booksByField}">
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Title</th>
                                        <th>Author</th>
                                        <th>ISBN</th>
                                        <th>Year</th>
                                        <th>Available</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="book" items="${booksByField}">
                                        <tr>
                                            <td>${book.id}</td>
                                            <td>${book.title}</td>
                                            <td>${book.author}</td>
                                            <td>${book.isbn}</td>
                                            <td>${book.yearOfPublish}</td>
                                            <td>
                                                <c:if test="${book.availability}">
                                                    <span class="badge badge-success">Yes</span>
                                                </c:if>
                                                <c:if test="${not book.availability}">
                                                    <span class="badge badge-danger">No</span>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
        </c:if>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>