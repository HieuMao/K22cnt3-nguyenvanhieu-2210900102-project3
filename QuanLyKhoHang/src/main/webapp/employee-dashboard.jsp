<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Nvh_Employee" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    Nvh_Employee loggedInEmployee = (Nvh_Employee) session.getAttribute("loggedInEmployee");
    if (loggedInEmployee == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang nhân viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            padding: 20px;
        }
        .container {
            background: white;
            padding: 20px;
            max-width: 600px;
            margin: auto;
            box-shadow: 0px 0px 10px gray;
            border-radius: 8px;
        }
        h2 {
            color: #333;
        }
        .btn {
            display: inline-block;
            padding: 10px 15px;
            margin: 10px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .btn-logout {
            background-color: #dc3545;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Chào mừng, <%= loggedInEmployee.getUsername() %>!</h2>
        <p>Bạn đã đăng nhập thành công vào hệ thống quản lý kho.</p>

        <h3>Chức năng</h3>
        <a href="request-export.jsp" class="btn">Yêu cầu xuất hàng</a>
        <a href="view-exports.jsp" class="btn">Xem yêu cầu đã gửi</a>

        <br><br>
        <a href="LoginServlet?action=logout" class="btn btn-logout">Đăng xuất</a>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>