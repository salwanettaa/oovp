<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System - Issue Book</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2@4.1.0-beta.1/dist/css/select2.min.css">
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
        
        <h1>Issue Book</h1>
        
        <!-- Error Alert -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        <!-- Issue Book Form -->
        <div class="card">
            <div class="card-header">
                <h5>Issue Book to Member</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/borrowing/issue" method="post">
                    <div class="form-group">
                        <label for="bookId">Book</label>
                        <select class="form-control select2" id="bookId" name="bookId" required>
                            <option value="">Select a book</option>
                            <c:forEach var="book" items="${books}">
                                <c:if test="${book.availability}">
                                    <option value="${book.id}" ${param.bookId eq book.id ? 'selected' : ''}>${book.title} (${book.author})</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="memberId">Member</label>
                        <select class="form-control select2" id="memberId" name="memberId" required>
                            <option value="">Select a member</option>
                            <c:forEach var="member" items="${members}">
                                <option value="${member.id}" ${param.memberId eq member.id ? 'selected' : ''}>${member.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="daysToReturn">Days to Return</label>
                        <input type="number" class="form-control" id="daysToReturn" name="daysToReturn" value="14" min="1" max="60" required>
                        <small class="form-text text-muted">Number of days the member has to return the book</small>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">Issue Book</button>
                    <a href="${pageContext.request.contextPath}/borrowing" class="btn btn-secondary">Cancel</a>
                </form>
            </div>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-beta.1/dist/js/select2.min.js"></script>
    
    <script>
        $(document).ready(function() {
            $('.select2').select2();
        });
    </script>
</body>
</html>