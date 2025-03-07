<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Nvh_Supplier" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Nhà Cung Cấp</title>
    <!-- Bootstrap CSS từ CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- Thanh điều hướng (có thể điều chỉnh theo dự án của bạn) -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container-fluid">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/index.jsp">Quản Lý Kho Hàng</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      </div>
    </nav>
    
    <!-- Nội dung chính -->
    <div class="container mt-5">
        <h2 class="text-center mb-4">Danh Sách Nhà Cung Cấp</h2>
        
        <%-- Hiển thị thông báo nếu có --%>
        <%
            String message = request.getParameter("message");
            if(message != null) {
        %>
        <div class="alert alert-success text-center" role="alert">
            <%= message %>
        </div>
        <%
            }
        %>
        
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Tên Nhà Cung Cấp</th>
                    <th>Thông Tin Liên Hệ</th>
                    <th>Địa Chỉ</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Lấy danh sách nhà cung cấp từ request attribute "suppliers"
                    List<Nvh_Supplier> suppliers = (List<Nvh_Supplier>) request.getAttribute("suppliers");
                    if(suppliers != null && !suppliers.isEmpty()){
                        for(Nvh_Supplier supplier : suppliers){
                %>
                <tr>
                    <td><%= supplier.getnvh_Supplier_Id() %></td>
                    <td><%= supplier.getnvh_Name() %></td>
                    <td><%= supplier.getnvh_Contact_Info() %></td>
                    <td><%= supplier.getnvh_Address() %></td>
                    <td>
                        <a href="editSupplier?id=<%= supplier.getnvh_Supplier_Id() %>" class="btn btn-warning btn-sm">Sửa</a>
                        <a href="deleteSupplier?id=<%= supplier.getnvh_Supplier_Id() %>" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa?');">Xóa</a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">Không có nhà cung cấp nào.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        
        <div class="text-center mt-4">
            <a href="<%= request.getContextPath() %>/addSupplier.jsp" class="btn btn-primary">Thêm Nhà Cung Cấp Mới</a>
        </div>
    </div>
    
    <!-- Footer đơn giản -->
    <footer class="footer mt-5 py-3 bg-light">
        <div class="container text-center">
            <span class="text-muted">© 2025 Quản Lý Kho Hàng. All rights reserved.</span>
        </div>
    </footer>
    
    <!-- Bootstrap JS từ CDN -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
