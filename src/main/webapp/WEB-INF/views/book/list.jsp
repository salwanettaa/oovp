<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System - Books</title>
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
                    <li class="nav-item active">
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
        
        <h1>Book Management</h1>
        
        <!-- Search Form -->
        <div class="card mb-4">
            <div class="card-header">
                <h5>Search Books</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/books/search" method="get" class="form-inline">
                    <select name="type" class="form-control mr-2">
                        <option value="title" ${searchType eq 'title' ? 'selected' : ''}>Title</option>
                        <option value="author" ${searchType eq 'author' ? 'selected' : ''}>Author</option>
                        <option value="field" ${searchType eq 'field' ? 'selected' : ''}>Field</option>
                        <option value="year" ${searchType eq 'year' ? 'selected' : ''}>Year</option>
                    </select>
                    <input type="text" name="term" class="form-control mr-2" value="${searchTerm}" placeholder="Search term">
                    <button type="submit" class="btn btn-primary">Search</button>
                    <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary ml-2">Clear</a>
                </form>
            </div>
        </div>
        
        <!-- Add Book Button -->
        <div class="mb-3">
            <a href="${pageContext.request.contextPath}/books/add" class="btn btn-success">Add New Book</a>
        </div>
        
        <!-- Books Table -->
        <div class="card">
            <div class="card-header">
                <h5>Books List</h5>
            </div>
            <div class="card-body">
                <c:if test="${empty books}">
                    <div class="alert alert-info">No books found.</div>
                </c:if>
                
                <c:if test="${not empty books}">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Author</th>
                                    <th>Field</th>
                                    <th>ISBN</th>
                                    <th>Year</th>
                                    <th>Available</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="book" items="${books}">
                                    <tr>
                                        <td>${book.id}</td>
                                        <td>${book.title}</td>
                                        <td>${book.author}</td>
                                        <td>${book.field}</td>
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
                                        <td>
                                            <a href="${pageContext.request.contextPath}/books/${book.id}" class="btn btn-sm btn-info">View</a>
                                            <a href="${pageContext.request.contextPath}/books/edit?id=${book.id}" class="btn btn-sm btn-primary">Edit</a>
                                            <button type="button" class="btn btn-sm btn-danger" onclick="confirmDelete(${book.id})">Delete</button>
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
    
    <!-- Delete Confirmation Modal -->
    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Confirm Delete</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete this book?</p>
                </div>
                <div class="modal-footer">
                    <form id="deleteForm" action="${pageContext.request.contextPath}/books/delete" method="post">
                        <input type="hidden" id="deleteId" name="id" value="">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <script>
        function confirmDelete(id) {
            document.getElementById('deleteId').value = id;
            $('#deleteModal').modal('show');
        }
    </script>
</body>
</html>