<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System - Return Book</title>
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
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/borrowing">Borrowing</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/reports">Reports</a>
                    </li>
                </ul>
            </div>
        </nav>
        
        <h1>Return Book</h1>
        
        <!-- Error Alert -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        <!-- Return Book Form -->
        <div class="card">
            <div class="card-header">
                <h5>Confirm Book Return</h5>
            </div>
            <div class="card-body">
                <c:if test="${empty issuedBookId}">
                    <div class="alert alert-warning">No book selected for return.</div>
                    <a href="${pageContext.request.contextPath}/borrowing/not-returned" class="btn btn-primary">View Not Returned Books</a>
                </c:if>
                
                <c:if test="${not empty issuedBookId}">
                    <p>Are you sure you want to return this book?</p>
                    
                    <form action="${pageContext.request.contextPath}/borrowing/return" method="post">
                        <input type="hidden" name="issuedBookId" value="${issuedBookId}">
                        
                        <button type="submit" class="btn btn-primary">Confirm Return</button>
                        <a href="${pageContext.request.contextPath}/borrowing" class="btn btn-secondary">Cancel</a>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>