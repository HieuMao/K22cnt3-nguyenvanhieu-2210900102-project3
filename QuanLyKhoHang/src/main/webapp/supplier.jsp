<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Nvh_Supplier" %>
<%
    model.Nvh_Employee loggedInEmployee = (model.Nvh_Employee) session.getAttribute("loggedInEmployee");
    model.Nvh_Admin loggedInAdmin = (model.Nvh_Admin) session.getAttribute("loggedInAdmin");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Nhà Cung Cấp</title>
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
        .btn-warning:hover { background-color: #e0a800; }
        .btn-danger:hover { background-color: #c82333; }
        .btn-primary:hover { background-color: #0056b3; }
    </style>
</head>
<body>

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

<div class="content">
    <div class="container mt-5 table-container">
        <h2 class="text-center mb-4">Danh Sách Nhà Cung Cấp</h2>

        <%
            String message = request.getParameter("message");
            if (message != null) {
        %>
        <div class="alert alert-success text-center" role="alert">
            <%= message %>
        </div>
        <% } %>

        <a href="addSupplier.jsp" class="btn btn-primary mb-3"><i class="fas fa-plus"></i> Thêm Nhà Cung Cấp</a>

        <table class="table table-bordered table-striped">
            <thead class="table-dark">
            <tr>
                <th>STT</th>
                <th>Tên Nhà Cung Cấp</th>
                <th>Thông Tin Liên Hệ</th>
                <th>Địa Chỉ</th>
                <th>Hành Động</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Nvh_Supplier> suppliers = (List<Nvh_Supplier>) request.getAttribute("suppliers");
                Integer currentPage = (Integer) request.getAttribute("currentPage");
                Integer totalPages = (Integer) request.getAttribute("totalPages");

                if (suppliers != null && !suppliers.isEmpty()) {
                    int index = (currentPage - 1) * 5 + 1; // Cập nhật số thứ tự
                    for (Nvh_Supplier supplier : suppliers) {
            %>
            <tr>
                <td><%= index++ %></td>
                <td><%= supplier.getnvh_Name() %></td>
                <td><%= supplier.getnvh_Contact_Info() %></td>
                <td><%= supplier.getnvh_Address() %></td>
                <td>
                    <a href="editSupplier?id=<%= supplier.getnvh_Supplier_Id() %>" class="btn btn-warning btn-sm">
                        <i class="fas fa-edit"></i> Sửa
                    </a>
                    <a href="deleteSupplier?id=<%= supplier.getnvh_Supplier_Id() %>" class="btn btn-danger btn-sm"
                       onclick="return confirm('Bạn có chắc muốn xóa nhà cung cấp này?');">
                        <i class="fas fa-trash-alt"></i> Xóa
                    </a>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="5" class="text-center">Không có nhà cung cấp nào.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>

        <!-- Phân trang -->
        <div class="d-flex justify-content-center">
            <% if (currentPage > 1) { %>
                <a href="suppliers?page=<%= currentPage - 1 %>" class="btn btn-outline-primary me-2">Trang trước</a>
            <% } %>

            <span class="align-self-center">Trang <%= currentPage %> / <%= totalPages %></span>

            <% if (currentPage < totalPages) { %>
                <a href="suppliers?page=<%= currentPage + 1 %>" class="btn btn-outline-primary ms-2">Trang sau</a>
            <% } %>
        </div>
    </div>
</div>

</body>
</html>
