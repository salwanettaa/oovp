<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System - Borrowing Records</title>
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
        
        <h1>Borrowing Records</h1>
        
        <!-- Action Buttons -->
        <div class="mb-4">
            <a href="${pageContext.request.contextPath}/borrowing/issue" class="btn btn-success">Issue Book</a>
            <a href="${pageContext.request.contextPath}/borrowing/not-returned" class="btn btn-warning ml-2">Not Returned Books</a>
        </div>
        
        <!-- Borrowing Records Table -->
        <div class="card">
            <div class="card-header">
                <h5>All Borrowing Records</h5>
            </div>
            <div class="card-body">
                <c:if test="${empty issuedBooks}">
                    <div class="alert alert-info">No borrowing records found.</div>
                </c:if>
                
                <c:if test="${not empty issuedBooks}">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Book</th>
                                    <th>Member</th>
                                    <th>Issue Date</th>
                                    <th>Return Date</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="issuedBook" items="${issuedBooks}">
                                    <tr>
                                        <td>${issuedBook.id}</td>
                                        <td>${issuedBook.bookTitle}</td>
                                        <td>${issuedBook.memberName}</td>
                                        <td><fmt:formatDate value="${issuedBook.dateIssued}" pattern="yyyy-MM-dd" /></td>
                                        <td><fmt:formatDate value="${issuedBook.dateReturn}" pattern="yyyy-MM-dd" /></td>
                                        <td>
                                            <c:if test="${issuedBook.returned}">
                                                <span class="badge badge-success">Returned</span>
                                            </c:if>
                                            <c:if test="${not issuedBook.returned}">
                                                <c:set var="now" value="<%= new java.util.Date() %>" />
                                                <c:if test="${issuedBook.dateReturn lt now}">
                                                    <span class="badge badge-danger">Overdue</span>
                                                </c:if>
                                                <c:if test="${issuedBook.dateReturn ge now}">
                                                    <span class="badge badge-warning">Borrowed</span>
                                                </c:if>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${not issuedBook.returned}">
                                                <a href="${pageContext.request.contextPath}/borrowing/return?id=${issuedBook.id}" class="btn btn-sm btn-primary">Return</a>
                                            </c:if>
                                            <c:if test="${issuedBook.returned}">
                                                <span class="text-muted">Already returned</span>
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
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>