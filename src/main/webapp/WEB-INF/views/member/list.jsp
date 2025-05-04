<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System - Members</title>
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
        
        <h1>Member Management</h1>
        
        <!-- Search Form -->
        <div class="card mb-4">
            <div class="card-header">
                <h5>Search Members</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/members/search" method="get" class="form-inline">
                    <input type="text" name="name" class="form-control mr-2" value="${searchName}" placeholder="Member name">
                    <button type="submit" class="btn btn-primary">Search</button>
                    <a href="${pageContext.request.contextPath}/members" class="btn btn-secondary ml-2">Clear</a>
                </form>
            </div>
        </div>
        
        <!-- Add Member Button -->
        <div class="mb-3">
            <a href="${pageContext.request.contextPath}/members/add" class="btn btn-success">Add New Member</a>
        </div>
        
        <!-- Members Table -->
        <div class="card">
            <div class="card-header">
                <h5>Members List</h5>
            </div>
            <div class="card-body">
                <c:if test="${empty members}">
                    <div class="alert alert-info">No members found.</div>
                </c:if>
                
                <c:if test="${not empty members}">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Registration Date</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="member" items="${members}">
                                    <tr>
                                        <td>${member.id}</td>
                                        <td>${member.name}</td>
                                        <td><fmt:formatDate value="${member.dateRegister}" pattern="yyyy-MM-dd" /></td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/members/${member.id}" class="btn btn-sm btn-info">View</a>
                                            <a href="${pageContext.request.contextPath}/members/edit?id=${member.id}" class="btn btn-sm btn-primary">Edit</a>
                                            <button type="button" class="btn btn-sm btn-danger" onclick="confirmDelete(${member.id})">Delete</button>
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
                    <p>Are you sure you want to delete this member? This will also remove any borrowing records associated with this member.</p>
                </div>
                <div class="modal-footer">
                    <form id="deleteForm" action="${pageContext.request.contextPath}/members/delete" method="post">
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