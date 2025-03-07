<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ - Quản Lý Kho Hàng</title>
    <!-- Bootstrap CSS từ CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- Thanh điều hướng -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="<%= request.getContextPath() %>/index.jsp">Quản Lý Kho Hàng</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/products">Sản Phẩm</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/lowStockProducts">Nhập Hàng</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/export.jsp">Xuất Hàng</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/suppliers">Nhà Cung Cấp</a>
                    </li>
                    <li class="nav-item">
                        <a href="<%= request.getContextPath() %>/logout" class="btn btn-secondary">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    
    <!-- Nội dung chính -->
    <div class="container mt-5">
        <div class="jumbotron text-center">
            <h1 class="display-4">Chào mừng đến với Quản Lý Kho Hàng</h1>
            <p class="lead">Hệ thống quản lý kho hàng cho doanh nghiệp</p>
            <hr class="my-4">
            <p>Bạn có thể quản lý sản phẩm, phiếu nhập, phiếu xuất và thông tin nhà cung cấp.</p>
            <a href="<%= request.getContextPath() %>/products" class="btn btn-primary btn-lg" role="button">
                Xem Sản Phẩm
            </a>
        </div>
    </div>
    
    <!-- Footer -->
    <footer class="footer mt-auto py-3 bg-light">
        <div class="container text-center">
            <span class="text-muted">© 2025 Quản Lý Kho Hàng. All rights reserved.</span>
        </div>
    </footer>
    
    <!-- Bootstrap JS từ CDN -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
