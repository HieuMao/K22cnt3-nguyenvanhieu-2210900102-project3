<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="model.Nvh_Product" %>
<%@ page import="dao.Nvh_ProductDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gửi Yêu Cầu Xuất Hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa; /* Nhẹ nhàng hơn */
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            background-color: #ffffff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Nhẹ nhàng hơn */
        }
        h2 {
            text-align: center;
            color: #007bff; /* Màu chủ đạo Bootstrap */
            margin-bottom: 30px;
        }
        .form-label {
            font-weight: bold;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0056b3; /* Đậm hơn khi hover */
            border-color: #0056b3;
        }
        .btn-secondary {
            background-color: #6c757d; /* Màu xám Bootstrap */
            border-color: #6c757d;
        }
        .btn-secondary:hover {
            background-color: #545b62; /* Đậm hơn khi hover */
            border-color: #545b62;
        }
        .alert-success {
            background-color: #d4edda;
            border-color: #c3e6cb;
            color: #155724;
            text-align: center;
            margin-bottom: 20px;
        }
        .alert-danger {
            background-color: #f8d7da;
            border-color: #f5c6cb;
            color: #721c24;
            text-align: center;
            margin-bottom: 20px;
        }
        .text-danger {
            font-size: 0.8rem;
            margin-top: 0.25rem;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Gửi Yêu Cầu Xuất Hàng</h2>

        <% if (request.getParameter("success") != null) { %>
            <div class="alert alert-success" role="alert">
                Yêu cầu xuất hàng đã được gửi thành công!
            </div>
        <% } else if (request.getParameter("error") != null) { %>
            <div class="alert alert-danger" role="alert">
                Đã xảy ra lỗi! Vui lòng thử lại.
            </div>
        <% } %>

        <form action="RequestExportServlet" method="post" id="exportForm">
            <div class="mb-3">
                <label for="product" class="form-label">Chọn sản phẩm:</label>
                <select name="product_id" id="product" class="form-select" required>
                    <option value="">-- Chọn sản phẩm --</option>
                    <%
                        Nvh_ProductDAO productDAO = new Nvh_ProductDAO();
                        List<Nvh_Product> products = productDAO.getAllProducts();
                        for (Nvh_Product product : products) {
                    %>
                    <option value="<%= product.getNvhProductId() %>">
                        <%= product.getNvhName() %> (Còn: <%= product.getNvhQuantity() %>)
                    </option>
                    <% } %>
                </select>
            </div>

            <div class="mb-3">
                <label for="quantity" class="form-label">Số lượng:</label>
                <input type="number" name="quantity" id="quantity" class="form-control" min="1" required>
                <div id="quantityError" class="text-danger"></div>
            </div>

            <div class="text-center">
                <button type="submit" class="btn btn-primary">Gửi Yêu Cầu</button>
                <a href="employee-dashboard.jsp" class="btn btn-secondary">Quay lại</a>
            </div>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('exportForm').addEventListener('submit', function(event) {
            var productId = document.getElementById('product').value;
            var quantity = parseInt(document.getElementById('quantity').value);
            var products = <%= new org.json.JSONArray(products).toString() %>; // Chuyển danh sách sản phẩm thành JSON

            if (productId && quantity) {
                var selectedProduct = products.find(p => p.nvhProductId == productId);
                if (selectedProduct && quantity > selectedProduct.nvhQuantity) {
                    document.getElementById('quantityError').textContent = "Số lượng không khả dụng.";
                    event.preventDefault(); // Ngăn chặn gửi form
                } else {
                    document.getElementById('quantityError').textContent = ""; // Xóa thông báo lỗi nếu hợp lệ
                }
            }
        });
    </script>
</body>
</html>