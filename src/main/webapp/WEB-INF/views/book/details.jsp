<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System - Book Details</title>
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
        
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Book Details</h1>
            <div>
                <a href="${pageContext.request.contextPath}/books/edit?id=${book.id}" class="btn btn-primary">Edit</a>
                <button type="button" class="btn btn-danger" onclick="confirmDelete(${book.id})">Delete</button>
                <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary ml-2">Back to List</a>
            </div>
        </div>
        
        <!-- Book Details Card -->
        <div class="row">
            <div class="col-md-8">
                <div class="card mb-4">
                    <div class="card-header">
                        <h5>Book Information</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <p><strong>ID:</strong> ${book.id}</p>
                                <p><strong>Title:</strong> ${book.title}</p>
                                <p><strong>Author:</strong> ${book.author}</p>
                                <p><strong>Field/Category:</strong> ${book.field}</p>
                            </div>
                            <div class="col-md-6">
                                <p><strong>ISBN:</strong> ${book.isbn}</p>
                                <p><strong>Year of Publication:</strong> ${book.yearOfPublish}</p>
                                <p><strong>Availability:</strong> 
                                    <c:if test="${book.availability}">
                                        <span class="badge badge-success">Available</span>
                                    </c:if>
                                    <c:if test="${not book.availability}">
                                        <span class="badge badge-danger">Not Available</span>
                                    </c:if>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card mb-4">
                    <div class="card-header">
                        <h5>Quick Actions</h5>
                    </div>
                    <div class="card-body">
                        <c:if test="${book.availability}">
                            <a href="${pageContext.request.contextPath}/borrowing/issue?bookId=${book.id}" class="btn btn-success btn-block mb-2">Issue Book</a>
                        </c:if>
                        <c:if test="${not book.availability}">
                            <a href="${pageContext.request.contextPath}/borrowing/not-returned" class="btn btn-warning btn-block mb-2">View Borrowed Status</a>
                        </c:if>
                        <a href="${pageContext.request.contextPath}/books/edit?id=${book.id}" class="btn btn-primary btn-block mb-2">Edit Details</a>
                        <button type="button" class="btn btn-danger btn-block" onclick="confirmDelete(${book.id})">Delete Book</button>
                    </div>
                </div>
                
                <!-- Stock Information -->
                <div class="card">
                    <div class="card-header">
                        <h5>Stock Information</h5>
                    </div>
                    <div class="card-body">
                        <p><strong>Current Stock:</strong> <span id="stockQty">Loading...</span></p>
                        
                        <!-- Update Stock Form -->
                        <form id="updateStockForm" action="${pageContext.request.contextPath}/books/update-stock" method="post" class="mt-3">
                            <input type="hidden" name="bookId" value="${book.id}">
                            <div class="input-group mb-3">
                                <input type="number" class="form-control" name="qty" id="newQty" placeholder="New quantity" min="0" required>
                                <div class="input-group-append">
                                    <button class="btn btn-outline-primary" type="submit">Update</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Borrowing History -->
        <div class="card mt-3">
            <div class="card-header">
                <h5>Borrowing History</h5>
            </div>
            <div class="card-body">
                <div id="borrowingHistory">
                    <!-- This will be filled via AJAX -->
                    <div class="text-center">
                        <div class="spinner-border text-primary" role="status">
                            <span class="sr-only">Loading...</span>
                        </div>
                        <p>Loading borrowing history...</p>
                    </div>
                </div>
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
                    <p>Are you sure you want to delete this book? This cannot be undone.</p>
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
    
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <script>
        function confirmDelete(id) {
            document.getElementById('deleteId').value = id;
            $('#deleteModal').modal('show');
        }
        
        // Load stock information
        $(document).ready(function() {
            $.ajax({
                url: "${pageContext.request.contextPath}/api/stock?bookId=${book.id}",
                method: "GET",
                success: function(data) {
                    $("#stockQty").text(data.qty);
                    $("#newQty").val(data.qty);
                },
                error: function() {
                    $("#stockQty").text("Error loading stock information");
                }
            });
            
            // Load borrowing history
            $.ajax({
                url: "${pageContext.request.contextPath}/api/borrowing/book?id=${book.id}",
                method: "GET",
                success: function(data) {
                    if (data.length === 0) {
                        $("#borrowingHistory").html("<div class='alert alert-info'>This book has not been borrowed yet.</div>");
                    } else {
                        let html = "<div class='table-responsive'><table class='table table-striped'>";
                        html += "<thead><tr><th>Member</th><th>Issue Date</th><th>Return Date</th><th>Status</th></tr></thead>";
                        html += "<tbody>";
                        
                        data.forEach(function(item) {
                            html += "<tr>";
                            html += "<td>" + item.memberName + "</td>";
                            html += "<td>" + new Date(item.dateIssued).toLocaleDateString() + "</td>";
                            html += "<td>" + new Date(item.dateReturn).toLocaleDateString() + "</td>";
                            html += "<td>";
                            if (item.returned) {
                                html += "<span class='badge badge-success'>Returned</span>";
                            } else {
                                let now = new Date();
                                let returnDate = new Date(item.dateReturn);
                                if (returnDate < now) {
                                    html += "<span class='badge badge-danger'>Overdue</span>";
                                } else {
                                    html += "<span class='badge badge-warning'>Borrowed</span>";
                                }
                            }
                            html += "</td>";
                            html += "</tr>";
                        });
                        
                        html += "</tbody></table></div>";
                        $("#borrowingHistory").html(html);
                    }
                },
                error: function() {
                    $("#borrowingHistory").html("<div class='alert alert-danger'>Error loading borrowing history.</div>");
                }
            });
        });
    </script>
</body>
</html>