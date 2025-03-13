<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.Nvh_RequestExportDAO" %>
<%@ page import="model.Nvh_RequestExport" %>
<%@ page import="java.lang.Math" %>

<%
    Nvh_RequestExportDAO requestDAO = new Nvh_RequestExportDAO();

    // Số bản ghi mỗi trang
    int recordsPerPage = 5;
    int currentPage = 1;

    // Lấy trang hiện tại từ request (nếu có)
    if (request.getParameter("page") != null) {
        currentPage = Integer.parseInt(request.getParameter("page"));
    }

    // Tính tổng số bản ghi và số trang
    int totalRecords = requestDAO.getTotalRequestCount();
    int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

    // Lấy danh sách yêu cầu theo trang
    List<Nvh_RequestExport> requestList = requestDAO.getRequestsByPage(currentPage, recordsPerPage);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch Sử Xuất Hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        .container {
            max-width: 900px;
            margin: 50px auto;
            background-color: #ffffff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #007bff;
            margin-bottom: 30px;
        }
        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
        .pagination a {
            margin: 0 5px;
            padding: 8px 12px;
            border: 1px solid #007bff;
            color: #007bff;
            text-decoration: none;
            border-radius: 5px;
        }
        .pagination a.active {
            background-color: #007bff;
            color: white;
        }
        .pagination a:hover {
            background-color: #0056b3;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Lịch Sử Xuất Hàng</h2>
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>ID Yêu Cầu</th>
                    <th>Mã Sản Phẩm</th>
                    <th>Số Lượng Xuất</th>
                    <th>Ngày Yêu Cầu</th>
                    <th>Trạng Thái</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Nvh_RequestExport exportReq : requestList) {
                %>
                <tr>
                    <td><%= exportReq.getNvhRequestId() %></td>
                    <td><%= exportReq.getNvhProductId() %></td>
                    <td><%= exportReq.getNvhQuantity() %></td>
                    <td><%= exportReq.getNvhRequestDate() %></td>
                    <td><%= exportReq.getNvhStatus() %></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <!-- Phân trang -->
        <div class="pagination">
            <%
                for (int i = 1; i <= totalPages; i++) {
                    if (i == currentPage) {
            %>
                        <a class="active" href="?page=<%= i %>"><%= i %></a>
            <%
                    } else {
            %>
                        <a href="?page=<%= i %>"><%= i %></a>
            <%
                    }
                }
            %>
        </div>

        <div class="text-center mt-3">
            <a href="employee-dashboard.jsp" class="btn btn-secondary">Quay lại</a>
        </div>
    </div>
</body>
</html>
