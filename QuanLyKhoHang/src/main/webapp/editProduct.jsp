<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Nvh_Product" %>
<%@ page import="model.Nvh_Supplier" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Sửa Thông Tin Sản Phẩm</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center mb-4">Sửa Thông Tin Sản Phẩm</h2>
    
    <%
        Nvh_Product product = (Nvh_Product) request.getAttribute("product");
        if (product == null) {
    %>
    <div class="alert alert-danger text-center">
        Không tìm thấy thông tin sản phẩm.
    </div>
    <%
        } else {
    %>
    <form action="<%= request.getContextPath() %>/UpdateProductServlet" method="post">
        <input type="hidden" name="productId" value="<%= product.getNvhProductId() %>">
        
        <div class="mb-3">
            <label for="name" class="form-label">Tên Sản Phẩm:</label>
            <input type="text" class="form-control" id="name" name="name" value="<%= product.getNvhName() %>" required>
        </div>
        
        <div class="mb-3">
            <label for="price" class="form-label">Giá:</label>
            <input type="number" class="form-control" id="price" name="price" step="0.01" 
                   value="<%= product.getNvhPrice() %>" required>
        </div>
        
        <div class="mb-3">
            <label for="quantity" class="form-label">Số Lượng:</label>
            <input type="number" class="form-control" id="quantity" name="quantity" 
                   value="<%= product.getNvhQuantity() %>" required>
        </div>
        
        <div class="mb-3">
            <label for="description" class="form-label">Mô Tả:</label>
            <textarea class="form-control" id="description" name="description" required><%= product.getNvhDescription() %></textarea>
        </div>
        
        <div class="mb-3">
            <label for="supplierId" class="form-label">Nhà Cung Cấp:</label>
            <select name="supplierId" id="supplierId" class="form-control" required>
    <option value="">-- Chọn nhà cung cấp --</option>
    <%
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
        
        <button type="submit" class="btn btn-primary">Cập Nhật</button>
    </form>
    <%
        }
    %>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
