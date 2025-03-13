<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm Nhà Cung Cấp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center mb-4">Thêm Nhà Cung Cấp</h2>

    <%-- Hiển thị thông báo nếu có --%>
    <%
        String message = request.getParameter("message");
        if (message != null) {
    %>
    <div class="alert alert-info text-center" role="alert">
        <%= message %>
    </div>
    <% } %>

    <form action="<%= request.getContextPath() %>/addSupplier" method="post">
        <div class="mb-3">
            <label for="name" class="form-label">Tên Nhà Cung Cấp:</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>

        <div class="mb-3">
            <label for="contactInfo" class="form-label">Thông Tin Liên Hệ:</label>
            <input type="text" class="form-control" id="contactInfo" name="contactInfo" required>
        </div>

        <div class="mb-3">
            <label for="address" class="form-label">Địa Chỉ:</label>
            <input type="text" class="form-control" id="address" name="address" required>
        </div>

        <button type="submit" class="btn btn-primary">Thêm</button>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
