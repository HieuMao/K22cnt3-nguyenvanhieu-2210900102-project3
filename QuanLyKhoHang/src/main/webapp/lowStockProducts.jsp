<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Nvh_Product" %>
<%
model.Nvh_Employee loggedInEmployee = (model.Nvh_Employee) session.getAttribute("loggedInEmployee");
    model.Nvh_Admin loggedInAdmin = (model.Nvh_Admin) session.getAttribute("loggedInAdmin");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Sản Phẩm Có Số Lượng Thấp</title>
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
        .table-dark th {
            background-color: #343a40;
            color: white;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
        .btn-secondary {
            background-color: #6c757d;
            border-color: #6c757d;
        }
        .btn-secondary:hover {
            background-color: #545b62;
            border-color: #545b62;
        }
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
        <h2 class="text-center mb-4">Danh Sách Sản Phẩm Có Số Lượng Thấp</h2>

        <%
            String message = request.getParameter("message");
            if (message != null) {
        %>
        <div class="alert alert-success text-center" role="alert">
            <%= message %>
        </div>
        <% } %>

        <form action="<%= request.getContextPath() %>/importProductsServlet" method="post">
            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Tên Sản Phẩm</th>
                    <th>Số Lượng</th>
                    <th>Giá</th>
                    <th>Số Lượng Nhập Thêm</th>
                    <th>Tổng Chi Phí</th>
                </tr>
                </thead>
                <tbody>
                <%
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
                        <input type="number" class="form-control"
                               name="importQty_<%= product.getNvhProductId() %>"
                               value="0" min="0"
                               oninput="calcRow('<%= product.getNvhProductId() %>', <%= product.getNvhPrice() %>)">
                    </td>
                    <td>
                        <span id="total_<%= product.getNvhProductId() %>">0</span>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6" class="text-center">Không có sản phẩm nào.</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>

            <div class="text-center">
                <button type="submit" class="btn btn-primary">Xác Nhận Nhập Hàng</button>
                <a href="products" class="btn btn-secondary">Quay lại</a>
            </div>
        </form>
    </div>
</div>

<script>
    function calcRow(productId, price) {
        var qtyInput = document.getElementsByName("importQty_" + productId)[0];
        var qty = parseInt(qtyInput.value) || 0;
        var totalCost = price * qty;
        var totalSpan = document.getElementById("total_" + productId);
        totalSpan.textContent = totalCost.toFixed(2);
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>