<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String message = request.getParameter("message");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm Sản Phẩm</title>
    <link rel="stylesheet" href="css/style.css"> <!-- Nếu có CSS -->
</head>
<body>

<h2>Thêm Sản Phẩm Mới</h2>

<% if (message != null) { %>
    <p style="color: green;"><%= message %></p>
<% } %>

<form action="AddProductServlet" method="post">
    <label for="name">Tên Sản Phẩm:</label>
    <input type="text" id="name" name="name" required><br>

    <label for="price">Giá:</label>
    <input type="number" id="price" name="price" step="0.01" required><br>

    <label for="quantity">Số Lượng:</label>
    <input type="number" id="quantity" name="quantity" required><br>

    <label for="description">Mô Tả:</label>
    <textarea id="description" name="description"></textarea><br>

    <button type="submit">Thêm Sản Phẩm</button>
</form>

</body>
</html>