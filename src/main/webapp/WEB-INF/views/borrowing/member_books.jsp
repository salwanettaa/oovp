<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System - Member's Books</title>
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
        
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Books Borrowed by ${member.name}</h1>
            <div>
                <a href="${pageContext.request.contextPath}/borrowing/issue?memberId=${member.id}" class="btn btn-success">Issue New Book</a>
                <a href="${pageContext.request.contextPath}/members/${member.id}" class="btn btn-info ml-2">Member Profile</a>
                <a href="${pageContext.request.contextPath}/borrowing" class="btn btn-secondary ml-2">All Borrowings</a>
            </div>
        </div>
        
        <!-- Books List -->
        <div class="card">
            <div class="card-header">
                <h5>Borrowing History</h5>
            </div>
            <div class="card-body">
                <c:if test="${empty issuedBooks}">
                    <div class="alert alert-info">This member hasn't borrowed any books yet.</div>
                </c:if>
                
                <c:if test="${not empty issuedBooks}">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Book Title</th>
                                    <th>Issue Date</th>
                                    <th>Due Date</th>
                                    <th>Return Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="book" items="${issuedBooks}">
                                    <tr>
                                        <td>${book.bookTitle}</td>
                                        <td><fmt:formatDate value="${book.dateIssued}" pattern="yyyy-MM-dd" /></td>
                                        <td><fmt:formatDate value="${book.dateReturn}" pattern="yyyy-MM-dd" /></td>
                                        <td>
                                            <c:if test="${book.returned}">
                                                <span class="badge badge-success">Returned</span>
                                            </c:if>
                                            <c:if test="${not book.returned}">
                                                <c:set var="now" value="<%= new java.util.Date() %>" />
                                                <c:if test="${book.dateReturn lt now}">
                                                    <span class="badge badge-danger">Overdue</span>
                                                </c:if>
                                                <c:if test="${book.dateReturn ge now}">
                                                    <span class="badge badge-warning">Borrowed</span>
                                                </c:if>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${not book.returned}">
                                                <a href="${pageContext.request.contextPath}/borrowing/return?id=${book.id}" class="btn btn-sm btn-primary">Return</a>
                                            </c:if>
                                            <c:if test="${book.returned}">
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
        
        <!-- Summary Section -->
        <div class="card mt-4">
            <div class="card-header">
                <h5>Borrowing Summary</h5>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-4">
                        <div class="card text-white bg-primary">
                            <div class="card-body text-center">
                                <h5 class="card-title">Total Books Borrowed</h5>
                                <p class="card-text display-4">${issuedBooks.size()}</p>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-4">
                        <div class="card text-white bg-success">
                            <div class="card-body text-center">
                                <h5 class="card-title">Books Returned</h5>
                                <c:set var="returnedCount" value="0" />
                                <c:forEach items="${issuedBooks}" var="book">
                                    <c:if test="${book.returned}">
                                        <c:set var="returnedCount" value="${returnedCount + 1}" />
                                    </c:if>
                                </c:forEach>
                                <p class="card-text display-4">${returnedCount}</p>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-4">
                        <div class="card text-white bg-warning">
                            <div class="card-body text-center">
                                <h5 class="card-title">Books Still Out</h5>
                                <c:set var="outCount" value="0" />
                                <c:forEach items="${issuedBooks}" var="book">
                                    <c:if test="${not book.returned}">
                                        <c:set var="outCount" value="${outCount + 1}" />
                                    </c:if>
                                </c:forEach>
                                <p class="card-text display-4">${outCount}</p>
                            </div>
                        </div>
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