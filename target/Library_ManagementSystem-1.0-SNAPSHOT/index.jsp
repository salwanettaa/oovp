<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .card-icon {
            font-size: 3rem;
            margin-bottom: 15px;
        }
        .feature-card {
            transition: transform 0.3s;
        }
        .feature-card:hover {
            transform: translateY(-5px);
        }
    </style>
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
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/reports">Reports</a>
                    </li>
                </ul>
            </div>
        </nav>
        
        <!-- Hero Section -->
        <div class="jumbotron">
            <h1 class="display-4">Library Management System</h1>
            <p class="lead">An efficient and user-friendly system to manage your library's books, members, and borrowing activities.</p>
            <hr class="my-4">
            <p>Start managing your library by exploring the features below.</p>
        </div>
        
        <!-- Features Section -->
        <div class="row">
            <div class="col-md-3 mb-4">
                <div class="card feature-card h-100">
                    <div class="card-body text-center">
                        <div class="card-icon">📚</div>
                        <h5 class="card-title">Book Management</h5>
                        <p class="card-text">Add, update, and manage your library's book collection with ease.</p>
                        <a href="${pageContext.request.contextPath}/books" class="btn btn-primary">Manage Books</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-3 mb-4">
                <div class="card feature-card h-100">
                    <div class="card-body text-center">
                        <div class="card-icon">👥</div>
                        <h5 class="card-title">Member Management</h5>
                        <p class="card-text">Keep track of all library members and their registration details.</p>
                        <a href="${pageContext.request.contextPath}/members" class="btn btn-primary">Manage Members</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-3 mb-4">
                <div class="card feature-card h-100">
                    <div class="card-body text-center">
                        <div class="card-icon">🔄</div>
                        <h5 class="card-title">Borrowing System</h5>
                        <p class="card-text">Issue and return books with our streamlined borrowing system.</p>
                        <a href="${pageContext.request.contextPath}/borrowing" class="btn btn-primary">Manage Borrowing</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-3 mb-4">
                <div class="card feature-card h-100">
                    <div class="card-body text-center">
                        <div class="card-icon">📊</div>
                        <h5 class="card-title">Reports</h5>
                        <p class="card-text">Generate insightful reports about your library's activities.</p>
                        <a href="${pageContext.request.contextPath}/reports" class="btn btn-primary">View Reports</a>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Quick Links Section -->
        <div class="card mt-4 mb-4">
            <div class="card-header">
                <h5>Quick Actions</h5>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-3 mb-2">
                        <a href="${pageContext.request.contextPath}/books/add" class="btn btn-outline-primary btn-block">Add New Book</a>
                    </div>
                    <div class="col-md-3 mb-2">
                        <a href="${pageContext.request.contextPath}/members/add" class="btn btn-outline-primary btn-block">Register New Member</a>
                    </div>
                    <div class="col-md-3 mb-2">
                        <a href="${pageContext.request.contextPath}/borrowing/issue" class="btn btn-outline-primary btn-block">Issue a Book</a>
                    </div>
                    <div class="col-md-3 mb-2">
                        <a href="${pageContext.request.contextPath}/borrowing/not-returned" class="btn btn-outline-primary btn-block">View Non-Returned Books</a>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Footer -->
        <footer class="mt-5 mb-3 text-center text-muted">
            <p>&copy; 2025 Library Management System</p>
        </footer>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>