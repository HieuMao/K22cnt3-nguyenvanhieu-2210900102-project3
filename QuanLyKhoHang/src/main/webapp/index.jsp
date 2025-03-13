<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Nvh_Employee, model.Nvh_Admin" %>
<%@ page import="dao.Nvh_ProductDAO" %>
<%@ page import="model.Nvh_RequestExport" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.Nvh_RequestExportDAO" %>
<%
    // Kiểm tra người dùng đăng nhập
    Nvh_Employee loggedInEmployee = (Nvh_Employee) session.getAttribute("loggedInEmployee");
	Nvh_Admin loggedInAdmin = (Nvh_Admin) session.getAttribute("loggedInAdmin");

    if (loggedInEmployee == null && loggedInAdmin == null) {
        response.sendRedirect("login.jsp"); // Chưa đăng nhập, chuyển về trang login
        return;
    }

    // Lấy số liệu thống kê
    Nvh_ProductDAO productDAO = new Nvh_ProductDAO();
    int totalProducts = productDAO.countAllProducts();         // Tổng số sản phẩm
    int lowStockCount = productDAO.countLowStockProducts(10);  // Sản phẩm sắp hết (dưới 10)

    Nvh_RequestExportDAO requestExportDAO = new Nvh_RequestExportDAO();
    int pendingOrders = requestExportDAO.countPendingRequests(); // Đơn hàng chờ xử lý
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard - Quản Lý Kho Hàng</title>
    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <style>
        body {
            display: flex;
            min-height: 100vh;
            background-color: #f8f9fa;
        }
        .sidebar {
            width: 250px;
            background: #343a40;
            color: white;
            position: fixed;
            height: 100%;
            padding-top: 20px;
        }
        .sidebar a {
            padding: 10px 20px;
            display: block;
            color: white;
            text-decoration: none;
            font-size: 16px;
        }
        .sidebar a:hover {
            background: #495057;
        }
        .content {
            margin-left: 250px;
            padding: 20px;
            width: 100%;
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

    <!-- Nội dung chính -->
    <div class="content">
        <h2 class="mb-4">Dashboard - Chào mừng 
            
        </h2>

        <!-- Thông tin thống kê -->
        <div class="row">
            <div class="col-md-4">
                <div class="card text-white bg-primary mb-3">
                    <div class="card-body">
                        <h5 class="card-title">Tổng số sản phẩm</h5>
                        <p class="card-text"><%= totalProducts %> sản phẩm</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card text-white bg-warning mb-3">
                    <div class="card-body">
                        <h5 class="card-title">Sản phẩm sắp hết</h5>
                        <p class="card-text"><%= lowStockCount %> sản phẩm</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card text-white bg-danger mb-3">
                    <div class="card-body">
                        <h5 class="card-title">Đơn hàng chờ xử lý</h5>
                        <p class="card-text"><%= pendingOrders %> đơn hàng</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Danh sách đơn hàng gần đây (VD tĩnh) -->
        <div class="card mt-4">
    <div class="card-header bg-secondary text-white">
        <h5>Đơn hàng gần đây</h5>
    </div>
    <div class="card-body">
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Mã Đơn</th>
                    <th>Mã Sản Phẩm</th>
                    <th>Ngày Đặt</th>
                    <th>Trạng Thái</th>
                </tr>
            </thead>
            <tbody>
                <%
                    Nvh_RequestExportDAO exportDAO = new Nvh_RequestExportDAO();
                    List<Nvh_RequestExport> recentList = exportDAO.getRecentExports(5); // Lấy 5 đơn gần nhất

                    for (Nvh_RequestExport req : recentList) {
                %>
                <tr>
                    <td>DH00<%= req.getNvhRequestId() %></td>
                    <td><%= req.getNvhProductId() %></td>
                    <td><%= req.getNvhRequestDate() %></td>
                    <td>
                        <%
                            String status = req.getNvhStatus();
                            if ("Đã duyệt".equals(status)) {
                        %>
                            <span class="badge bg-success">Đã duyệt</span>
                        <%
                            } else if ("Chờ duyệt".equals(status)) {
                        %>
                            <span class="badge bg-warning">Chờ duyệt</span>
                        <%
                            } else {
                        %>
                            <span class="badge bg-danger">Từ chối / Lỗi</span>
                        <%
                            }
                        %>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
