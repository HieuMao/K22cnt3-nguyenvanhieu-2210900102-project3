<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Nvh_Product" %>
<%
    model.Nvh_Employee loggedInEmployee = (model.Nvh_Employee) session.getAttribute("loggedInEmployee");
    model.Nvh_Admin loggedInAdmin = (model.Nvh_Admin) session.getAttribute("loggedInAdmin");

    int itemsPerPage = 5;  
    String pageStr = request.getParameter("page");
    int currentPage = (pageStr != null) ? Integer.parseInt(pageStr) : 1;

    Object obj = request.getAttribute("products");
    List<Nvh_Product> products = (obj instanceof List<?>) ? (List<Nvh_Product>) obj : null;

    int totalProducts = (products != null) ? products.size() : 0;
    int totalPages = (int) Math.ceil((double) totalProducts / itemsPerPage);

    int startIndex = (currentPage - 1) * itemsPerPage;
    int endIndex = Math.min(startIndex + itemsPerPage, totalProducts);
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Lý Sản Phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            display: flex;
        }

        .sidebar {
            width: 250px;
            background-color: #343a40;
            color: white;
            height: 100vh;
            position: fixed;
            padding-top: 20px;
        }

        .sidebar h4 {
            padding: 10px 20px;
            text-align: center;
        }

        .sidebar a {
            padding: 10px 20px;
            text-decoration: none;
            color: white;
            display: block;
        }

        .sidebar a:hover {
            background-color: #495057;
        }

        .content {
            margin-left: 250px;
            padding: 20px;
            width: calc(100% - 250px);
            overflow-y: auto;
            height: 100vh;
        }

        .table-container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>

<!-- Sidebar -->
<nav class="sidebar">
    <h4 class="text-center">Quản Lý Kho</h4>
    <a href="index.jsp"><i class="fas fa-home"></i> Dashboard</a>

    <% if (loggedInAdmin != null) { %>
        <a href="products"><i class="fas fa-box"></i> Quản lý Sản Phẩm</a>
        <a href="lowStockProducts"><i class="fas fa-truck-loading"></i> Nhập Hàng</a>
        <a href="admin-request-export.jsp"><i class="fas fa-shipping-fast"></i> Xuất Hàng</a>
        <a href="suppliers"><i class="fas fa-user-tie"></i> Nhà Cung Cấp</a>
    <% } %>

    <% if (loggedInEmployee != null) { %>
        <a href="request-export.jsp"><i class="fas fa-file-export"></i> Yêu Cầu Xuất Hàng</a>
        <a href="view-exports.jsp"><i class="fas fa-eye"></i> Xem Yêu Cầu</a>
    <% } %>

    <a href="LoginServlet?action=logout" class="text-danger"><i class="fas fa-sign-out-alt"></i> Đăng Xuất</a>
</nav>

<!-- Content -->
<div class="content">
    <div class="container mt-5 table-container">
        <h2 class="text-center mb-4">Danh Sách Sản Phẩm</h2>
        <div class="d-flex justify-content-between align-items-center mb-3">
    <form action="products" method="GET" class="d-flex">
        <input type="text" name="keyword" class="form-control me-2" placeholder="Nhập tên sản phẩm..." 
               value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
    </form>

    <% if (loggedInAdmin != null) { %>
        <a href="<%= request.getContextPath() %>/addProductPage" class="btn btn-success">Thêm Sản Phẩm</a>
    <% } %>
</div>


        <table class="table table-bordered table-striped">
            <thead class="table-dark">
            <tr>
                <th>STT</th>
                <th>Tên Sản Phẩm</th>
                <th>Số Lượng</th>
                <th>Giá</th>
                <th>Mô Tả</th>
                <th>Nhà Cung Cấp</th>
                <th>Hành Động</th>
            </tr>
            </thead>
            <tbody>
            <% if (products != null && !products.isEmpty()) { 
                for (int i = startIndex; i < endIndex; i++) {
                    Nvh_Product product = products.get(i);
            %>
            <tr>
                <td><%= i + 1 %></td>
                <td><%= product.getNvhName() %></td>
                <td><%= product.getNvhQuantity() %></td>
                <td><%= product.getNvhPrice() %></td>
                <td><%= product.getNvhDescription() %></td>
                <td><%= (product.getNvhSupplierName() != null ? product.getNvhSupplierName() : "Chưa xác định") %></td>
                <td>
                    <a href="<%= request.getContextPath() %>/editProduct?id=<%= product.getNvhProductId() %>"
                       class="btn btn-warning btn-sm">Sửa</a>
                    <a href="<%= request.getContextPath() %>/deleteProduct?id=<%= product.getNvhProductId() %>"
                       class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa?');">Xóa</a>
                </td>
            </tr>
            <% } } else { %>
            <tr>
                <td colspan="7" class="text-center">Không có sản phẩm nào.</td>
            </tr>
            <% } %>
            </tbody>
        </table>

        <!-- Phân trang -->
        <nav>
            <ul class="pagination justify-content-center">
                <% if (currentPage > 1) { %>
                <li class="page-item">
                    <a class="page-link" href="?page=<%= currentPage - 1 %>">Trang Trước</a>
                </li>
                <% } %>

                <% for (int i = 1; i <= totalPages; i++) { %>
                <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                    <a class="page-link" href="?page=<%= i %>"><%= i %></a>
                </li>
                <% } %>

                <% if (currentPage < totalPages) { %>
                <li class="page-item">
                    <a class="page-link" href="?page=<%= currentPage + 1 %>">Trang Sau</a>
                </li>
                <% } %>
            </ul>
        </nav>
    </div>
</div>

</body>
</html>
