<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.Nvh_RequestExportDAO" %>
<%@ page import="dao.Nvh_ProductDAO" %>
<%@ page import="model.Nvh_RequestExport" %>
<%@ page import="model.Nvh_Product" %>

<%
    model.Nvh_Employee loggedInEmployee = (model.Nvh_Employee) session.getAttribute("loggedInEmployee");
    model.Nvh_Admin loggedInAdmin = (model.Nvh_Admin) session.getAttribute("loggedInAdmin");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Yêu Cầu Xuất Hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body { font-family: Arial, sans-serif; background-color: #f8f9fa; display: flex; }
        .sidebar { width: 250px; background-color: #343a40; color: white; height: 100vh; position: fixed; padding-top: 20px; }
        .sidebar h4 { padding: 10px 20px; text-align: center; }
        .sidebar a { padding: 10px 20px; text-decoration: none; color: white; display: block; }
        .sidebar a:hover { background-color: #495057; }
        .content { margin-left: 250px; padding: 20px; width: calc(100% - 250px); overflow-y: auto; height: 100vh; }
        .table-container { background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        .table-dark th { background-color: #343a40; color: white; }
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
        <h2>Danh Sách Yêu Cầu Xuất Hàng</h2>

        <%
            Nvh_RequestExportDAO requestDAO = new Nvh_RequestExportDAO();
            Nvh_ProductDAO productDAO = new Nvh_ProductDAO();
            List<Nvh_RequestExport> requestList = requestDAO.getAllRequests();

            // Số lượng yêu cầu trên mỗi trang
            int itemsPerPage = 5;
            int totalRequests = (requestList != null) ? requestList.size() : 0;
            int totalPages = (int) Math.ceil((double) totalRequests / itemsPerPage);

            // Lấy trang hiện tại từ request, mặc định là trang 1
            int currentPage = 1;
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                try {
                    currentPage = Integer.parseInt(pageParam);
                    if (currentPage < 1 || currentPage > totalPages) currentPage = 1;
                } catch (NumberFormatException e) {
                    currentPage = 1;
                }
            }

            // Tính vị trí bắt đầu và kết thúc của danh sách con
            int start = (currentPage - 1) * itemsPerPage;
            int end = Math.min(start + itemsPerPage, totalRequests);
            List<Nvh_RequestExport> paginatedList = requestList.subList(start, end);
        %>

        <% if (paginatedList.isEmpty()) { %>
            <p class="text-danger">Không có yêu cầu xuất hàng nào.</p>
        <% } else { %>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID Yêu Cầu</th>
                <th>Mã Sản Phẩm</th>
                <th>Số Lượng</th>
                <th>Trạng Thái</th>
                <th>Hành Động</th>
            </tr>
            </thead>
            <tbody>
            <% for (Nvh_RequestExport exportReq : paginatedList) { %>
            <tr>
                <td><%= exportReq.getNvhRequestId() %></td>
                <td><%= exportReq.getNvhProductId() %></td>
                <td><%= exportReq.getNvhQuantity() %></td>
                <td><%= exportReq.getNvhStatus() %></td>
                <td>
                    <% if ("Chờ duyệt".equals(exportReq.getNvhStatus())) { 
                        Nvh_Product product = productDAO.getProductById(exportReq.getNvhProductId());
                        if (product != null && product.getNvhQuantity() >= exportReq.getNvhQuantity()) { %>
                            <form action="ApproveRequestServlet" method="post" style="display:inline;">
                                <input type="hidden" name="request_id" value="<%= exportReq.getNvhRequestId() %>">
                                <button type="submit" class="btn btn-success btn-sm">Duyệt</button>
                            </form>
                        <% } else { %>
                            <span class="text-danger">Không đủ hàng cần nhập thêm</span>
                        <% } 
                    } else { %>
                        <button class="btn btn-secondary btn-sm" disabled>Đã duyệt</button>
                    <% } %>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>

        <!-- Hiển thị phân trang -->
        <nav>
            <ul class="pagination">
                <% for (int i = 1; i <= totalPages; i++) { %>
                    <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                        <a class="page-link" href="admin-request-export.jsp?page=<%= i %>"><%= i %></a>
                    </li>
                <% } %>
            </ul>
        </nav>
        <% } %>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
