<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Nvh_Product" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ - Quản Lý Sản Phẩm</title>
    <!-- Bootstrap CSS từ CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- Thanh điều hướng -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container-fluid">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/index.jsp">Quản Lý Sản Phẩm</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      </div>
    </nav>
    
    <!-- Nội dung chính -->
    <div class="container mt-5">
        <h2 class="text-center mb-4">Danh Sách Sản Phẩm</h2>
        
        <%-- Hiển thị thông báo nếu có --%>
        <%
            String message = request.getParameter("message");
            if(message != null) {
        %>
        <div class="alert alert-success text-center" role="alert">
            <%= message %>
        </div>
        <%
            }
        %>
        
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Tên Sản Phẩm</th>
                    <th>Số Lượng</th>
                    <th>Giá</th>
                    <th>Mô Tả</th>
                    <th>Nhà Cung Cấp</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <%
                    Object obj = request.getAttribute("products");
                    if (obj instanceof List<?>) {
                        List<?> products = (List<?>) obj;
                        for (Object productObj : products) {
                            if (productObj instanceof Nvh_Product) {
                                Nvh_Product product = (Nvh_Product) productObj;
                %>
                <tr>
                    <td><%= product.getNvhProductId() %></td>
                    <td><%= product.getNvhName() %></td>
                    <td>
                        <% if (product.getNvhQuantity() < 10) { %>
                            <span class="text-danger"><%= product.getNvhQuantity() %></span>
                            <br/><small class="text-danger">Nhập thêm hàng</small>
                        <% } else { %>
                            <%= product.getNvhQuantity() %>
                        <% } %>
                    </td>
                    <td><%= product.getNvhPrice() %></td>
                    <td><%= product.getNvhDescription() %></td>
                    <td>
                        <%= (product.getNvhSupplierName() != null ? product.getNvhSupplierName() : "Chưa xác định") %>
                    </td>
                    <td>
                        <!-- Nút "Sửa" chuyển hướng đến trang sửa sản phẩm -->
                        <a href="<%= request.getContextPath() %>/editProduct?id=<%= product.getNvhProductId() %>" 
                           class="btn btn-warning btn-sm">Sửa</a>
                        <a href="<%= request.getContextPath() %>/deleteProduct?id=<%= product.getNvhProductId() %>" 
                           class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa?');">Xóa</a>
                    </td>
                </tr>
                <%
                            }
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7" class="text-center">Không có sản phẩm nào.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        
        <div class="text-center mt-4">
            <a href="<%= request.getContextPath() %>/addProductPage" class="btn btn-primary">Thêm Sản Phẩm</a>
        </div>
    </div>
    
    <!-- Footer -->
    <footer class="footer mt-5 py-3 bg-light">
        <div class="container text-center">
            <span class="text-muted">© 2025 Quản Lý Sản Phẩm. All rights reserved.</span>
        </div>
    </footer>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
