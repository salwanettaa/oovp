<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System - Not Returned Books Report</title>
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
            <h1>Not Returned Books Report</h1>
            <a href="${pageContext.request.contextPath}/reports" class="btn btn-secondary">Back to Reports</a>
        </div>
        
        <!-- Report Statistics -->
        <div class="row mb-4">
            <div class="col-md-4">
                <div class="card text-white bg-warning">
                    <div class="card-body">
                        <h5 class="card-title">Total Not Returned</h5>
                        <p class="card-text display-4">${not empty booksNotReturned ? booksNotReturned.size() : 0}</p>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card text-white bg-danger">
                    <div class="card-body">
                        <h5 class="card-title">Overdue Books</h5>
                        <c:set var="now" value="<%= new java.util.Date() %>" />
                        <c:set var="overdueCount" value="0" />
                        <c:forEach items="${booksNotReturned}" var="book">
                            <c:if test="${book.dateReturn lt now}">
                                <c:set var="overdueCount" value="${overdueCount + 1}" />
                            </c:if>
                        </c:forEach>
                        <p class="card-text display-4">${overdueCount}</p>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card text-white bg-info">
                    <div class="card-body">
                        <h5 class="card-title">Members with Books</h5>
                        <c:set var="uniqueMembers" value="0" />
                        <c:set var="membersSet" value="${{}}"/>
                        <c:forEach items="${booksNotReturned}" var="book">
                            <c:if test="${not membersSet.containsKey(book.memberId)}">
                                <c:set target="${membersSet}" property="${book.memberId}" value="1"/>
                                <c:set var="uniqueMembers" value="${uniqueMembers + 1}" />
                            </c:if>
                        </c:forEach>
                        <p class="card-text display-4">${uniqueMembers}</p>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Books List -->
        <div class="card">
            <div class="card-header">
                <h5>Not Returned Books</h5>
            </div>
            <div class="card-body">
                <c:if test="${empty booksNotReturned}">
                    <div class="alert alert-info">All books have been returned.</div>
                </c:if>
                
                <c:if test="${not empty booksNotReturned}">
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
                                    <th>Days</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="book" items="${booksNotReturned}">
                                    <c:set var="now" value="<%= new java.util.Date() %>" />
                                    <c:set var="isOverdue" value="${book.dateReturn lt now}" />
                                    
                                    <tr class="${isOverdue ? 'table-danger' : ''}">
                                        <td>${book.id}</td>
                                        <td>${book.bookTitle}</td>
                                        <td>${book.memberName}</td>
                                        <td><fmt:formatDate value="${book.dateIssued}" pattern="yyyy-MM-dd" /></td>
                                        <td><fmt:formatDate value="${book.dateReturn}" pattern="yyyy-MM-dd" /></td>
                                        <td>
                                            <c:if test="${isOverdue}">
                                                <span class="badge badge-danger">Overdue</span>
                                            </c:if>
                                            <c:if test="${not isOverdue}">
                                                <span class="badge badge-warning">Borrowed</span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${isOverdue}">
                                                <fmt:parseDate var="dueDate" value="${book.dateReturn}" pattern="yyyy-MM-dd" />
                                                <fmt:parseDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />
                                                <fmt:parseNumber var="daysOverdue" value="${(currentDate.time - dueDate.time) / (1000*60*60*24)}" integerOnly="true" />
                                                <span class="text-danger">${daysOverdue} days overdue</span>
                                            </c:if>
                                            <c:if test="${not isOverdue}">
                                                <fmt:parseDate var="dueDate" value="${book.dateReturn}" pattern="yyyy-MM-dd" />
                                                <fmt:parseDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />
                                                <fmt:parseNumber var="daysLeft" value="${(dueDate.time - currentDate.time) / (1000*60*60*24)}" integerOnly="true" />
                                                ${daysLeft} days left
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