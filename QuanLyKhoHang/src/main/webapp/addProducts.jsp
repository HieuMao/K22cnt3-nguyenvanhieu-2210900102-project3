<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Nvh_Supplier" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm Sản Phẩm</title>
    <!-- Bootstrap CSS từ CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center mb-4">Thêm Sản Phẩm Mới</h2>
    
    <%-- Hiển thị thông báo nếu có (message) --%>
    <%
        String message = request.getParameter("message");
        if (message != null) {
    %>
    <div class="alert alert-success text-center" role="alert">
        <%= message %>
    </div>
    <%
        }
    %>
    
    <form action="<%= request.getContextPath() %>/AddProductServlet" method="post">
        <div class="mb-3">
            <label for="name" class="form-label">Tên Sản Phẩm:</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        
        <div class="mb-3">
            <label for="price" class="form-label">Giá:</label>
            <input type="number" class="form-control" id="price" name="price" step="0.01" required>
        </div>
        
        <div class="mb-3">
            <label for="quantity" class="form-label">Số Lượng:</label>
            <input type="number" class="form-control" id="quantity" name="quantity" required>
        </div>
        
        <div class="mb-3">
            <label for="description" class="form-label">Mô Tả:</label>
            <textarea class="form-control" id="description" name="description"></textarea>
        </div>
        
        <div class="mb-3">
            <label for="supplierId" class="form-label">Nhà Cung Cấp:</label>
            <select class="form-control" id="supplierId" name="supplierId" required>
                <option value="">-- Chọn nhà cung cấp --</option>
                <%
                    // Lấy danh sách nhà cung cấp từ request attribute "suppliers"
                    List<Nvh_Supplier> suppliers = (List<Nvh_Supplier>) request.getAttribute("suppliers");
                    if (suppliers != null && !suppliers.isEmpty()) {
                        for (Nvh_Supplier supplier : suppliers) {
                %>
                <option value="<%= supplier.getNvhSupplierId() %>">
                    <%= supplier.getNvhName() %>
                </option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        
        <button type="submit" class="btn btn-primary">Thêm Sản Phẩm</button>
    </form>
</div>
<!-- Bootstrap JS từ CDN -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
