<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách sản phẩm</title>
</head>
<body>
    <h2>Danh sách Sản Phẩm</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Tên sản phẩm</th>
            <th>Số lượng</th>
            <th>Giá</th>
        </tr>
        <%
            // Retrieve the 'products' attribute from the request
            Object obj = request.getAttribute("products");
            
            // Check if the object is a List<Product>
            if (obj instanceof List<?>) {
                List<?> products = (List<?>) obj;
                
                // Check if each element is of type Product
                for (Object productObj : products) {
                    if (productObj instanceof Product) {
                        Product product = (Product) productObj;
        %>
        <tr>
            <td><%= product.getId() %></td>
            <td><%= product.getName() %></td>
            <td><%= product.getQuantity() %></td>
            <td><%= product.getPrice() %></td>
        </tr>
        <%
                    }
                }
            }
        %>
    </table>
</body>
</html>
