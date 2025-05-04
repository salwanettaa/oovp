<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System - Not Returned Books</title>
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
        
        <h1>Not Returned Books</h1>
        
        <!-- Actions Buttons -->
        <div class="mb-4">
            <a href="${pageContext.request.contextPath}/borrowing" class="btn btn-secondary">All Borrowing Records</a>
            <a href="${pageContext.request.contextPath}/borrowing/issue" class="btn btn-success ml-2">Issue Book</a>
        </div>
        
        <!-- Not Returned Books Table -->
        <div class="card">
            <div class="card-header">
                <h5>Books Currently Borrowed</h5>
            </div>
            <div class="card-body">
                <c:if test="${empty notReturnedBooks}">
                    <div class="alert alert-info">No books are currently borrowed.</div>
                </c:if>
                
                <c:if test="${not empty notReturnedBooks}">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Book</th>
                                    <th>Member</th>
                                    <th>Issue Date</th>
                                    <th>Due Date</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="book" items="${notReturnedBooks}">
                                    <tr>
                                        <td>${book.id}</td>
                                        <td>${book.bookTitle}</td>
                                        <td>${book.memberName}</td>
                                        <td><fmt:formatDate value="${book.dateIssued}" pattern="yyyy-MM-dd" /></td>
                                        <td><fmt:formatDate value="${book.dateReturn}" pattern="yyyy-MM-dd" /></td>
                                        <td>
                                            <c:set var="now" value="<%= new java.util.Date() %>" />
                                            <c:if test="${book.dateReturn lt now}">
                                                <span class="badge badge-danger">Overdue</span>
                                            </c:if>
                                            <c:if test="${book.dateReturn ge now}">
                                                <span class="badge badge-warning">Borrowed</span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/borrowing/return?id=${book.id}" class="btn btn-sm btn-primary">Return</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>