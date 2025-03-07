<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Nvh_Product" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Sản Phẩm Có Số Lượng thấp </title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center mb-4">Danh Sách Sản Phẩm Có Số Lượng thấp </h2>
    
    <% 
        // Nếu có message=? trên URL, hiển thị
        String message = request.getParameter("message");
        if (message != null) {
    %>
    <div class="alert alert-success text-center" role="alert">
        <%= message %>
    </div>
    <% } %>
    
    <!-- Form gửi đến servlet xử lý nhập hàng -->
    <form action="<%= request.getContextPath() %>/importProductsServlet" method="post">
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Tên Sản Phẩm</th>
                    <th>Số Lượng</th>
                    <th>Giá</th>
                    <th>Số Lượng Nhập Thêm</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Lấy danh sách sản phẩm từ request attribute "products"
                    List<Nvh_Product> products = (List<Nvh_Product>) request.getAttribute("products");
                    if (products != null && !products.isEmpty()) {
                        for (Nvh_Product product : products) {
                %>
                <tr>
                    <td><%= product.getNvhProductId() %></td>
                    <td><%= product.getNvhName() %></td>
                    <td><%= product.getNvhQuantity() %></td>
                    <td><%= product.getNvhPrice() %></td>
                    <td>
                        <!-- Tên input: importQty_<productId> để servlet dễ đọc -->
                        <input type="number" class="form-control"
                               name="importQty_<%= product.getNvhProductId() %>" 
                               value="0" min="0">
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">Không có sản phẩm nào.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <!-- Nút xác nhận nhập hàng -->
        <button type="submit" class="btn btn-primary">Xác Nhận Nhập Hàng</button>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
