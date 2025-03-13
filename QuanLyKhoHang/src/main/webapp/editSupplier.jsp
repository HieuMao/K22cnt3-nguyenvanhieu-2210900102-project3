<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Nvh_Supplier" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Sửa Thông Tin Nhà Cung Cấp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center mb-4">Sửa Thông Tin Nhà Cung Cấp</h2>
    
    <%
        Nvh_Supplier supplier = (Nvh_Supplier) request.getAttribute("supplier");
        if (supplier == null) {
    %>
    <div class="alert alert-danger text-center">
        Không tìm thấy thông tin nhà cung cấp.
    </div>
    <%
        } else {
    %>
    <form action="<%= request.getContextPath() %>/editSupplier" method="post">
        <!-- Ẩn supplierId -->
        <input type="hidden" name="supplierId" value="<%= supplier.getnvh_Supplier_Id() %>">

        <div class="mb-3">
            <label for="name" class="form-label">Tên Nhà Cung Cấp:</label>
            <input type="text" class="form-control" id="name" name="name" 
                   value="<%= supplier.getnvh_Name() %>" required>
        </div>
        
        <div class="mb-3">
            <label for="contactInfo" class="form-label">Thông Tin Liên Hệ:</label>
            <input type="text" class="form-control" id="contactInfo" name="contactInfo"
                   value="<%= supplier.getnvh_Contact_Info() %>" required>
        </div>
        
        <div class="mb-3">
            <label for="address" class="form-label">Địa Chỉ:</label>
            <input type="text" class="form-control" id="address" name="address"
                   value="<%= supplier.getnvh_Address() %>" required>
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
