<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System - Member Details</title>
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
                    <li class="nav-item active">
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
        
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Member Details</h1>
            <div>
                <a href="${pageContext.request.contextPath}/members/edit?id=${member.id}" class="btn btn-primary">Edit</a>
                <a href="${pageContext.request.contextPath}/members" class="btn btn-secondary">Back to List</a>
            </div>
        </div>
        
        <!-- Member Details Card -->
        <div class="card mb-4">
            <div class="card-header">
                <h5>Member Information</h5>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <p><strong>ID:</strong> ${member.id}</p>
                        <p><strong>Name:</strong> ${member.name}</p>
                        <p><strong>Registration Date:</strong> <fmt:formatDate value="${member.dateRegister}" pattern="yyyy-MM-dd" /></p>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Borrowed Books Section -->
        <div class="card">
            <div class="card-header">
                <h5>Borrowed Books</h5>
            </div>
            <div class="card-body">
                <a href="${pageContext.request.contextPath}/borrowing/member?id=${member.id}" class="btn btn-info mb-3">View All Borrowed Books</a>
                
                <a href="${pageContext.request.contextPath}/borrowing/issue?memberId=${member.id}" class="btn btn-success mb-3 ml-2">Issue New Book</a>
            </div>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>