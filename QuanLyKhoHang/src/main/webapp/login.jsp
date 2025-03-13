<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Nhập - Quản Lý Kho Hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa; /* Màu nền trang */
            height: 100vh;
            display: flex;
            align-items: center;
        }
        .login-container {
            width: 400px; /* Điều chỉnh kích thước container */
            margin: auto;
            background-color: #ffffff; /* Màu nền container */
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Hiệu ứng đổ bóng */
        }
        .login-title {
            text-align: center;
            color: #007bff; /* Màu chủ đạo Bootstrap */
            margin-bottom: 2rem;
        }
        .form-label {
            font-weight: bold;
        }
        .form-control {
            border-radius: 5px;
        }
        .btn-primary {
            width: 100%; /* Nút đăng nhập full width */
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0056b3; /* Màu đậm hơn khi hover */
            border-color: #0056b3;
        }
        .alert-danger {
            text-align: center;
            margin-top: 1rem;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="login-container">
            <h2 class="login-title">Đăng Nhập</h2>
            <% 
                String error = request.getParameter("error");
                if(error != null) { 
            %>
            <div class="alert alert-danger" role="alert">
                <%= error %>
            </div>
            <% } %>
            <form action="LoginServlet" method="post">
                <div class="mb-3">
                    <label for="username" class="form-label">Tên đăng nhập:</label>
                    <input type="text" class="form-control" id="username" name="username" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Mật khẩu:</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <button type="submit" class="btn btn-primary">Đăng nhập</button>
            </form>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>