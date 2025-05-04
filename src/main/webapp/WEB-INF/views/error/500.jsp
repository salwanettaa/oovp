<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>500 - Internal Server Error</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-8 offset-md-2 text-center">
                <h1 class="display-1">500</h1>
                <h2 class="mb-4">Internal Server Error</h2>
                <p class="lead">The server encountered an unexpected condition that prevented it from fulfilling the request.</p>
                <hr>
                <p>Please try again later or contact the administrator if the problem persists.</p>
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary mt-3">Return to Home</a>
                
                <c:if test="${pageContext.errorData.throwable != null}">
                    <div class="mt-5 text-left">
                        <h5>Error Details (Visible only in development):</h5>
                        <div class="alert alert-danger">
                            <strong>Error Type:</strong> ${pageContext.errorData.throwable.class.name}<br>
                            <strong>Message:</strong> ${pageContext.errorData.throwable.message}<br>
                        </div>
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