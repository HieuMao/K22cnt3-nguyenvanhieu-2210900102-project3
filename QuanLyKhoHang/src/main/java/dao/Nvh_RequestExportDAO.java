package dao;

import java.sql.*;
import java.util.*;
import model.Nvh_RequestExport;

public class Nvh_RequestExportDAO {
    private Connection conn;

    public Nvh_RequestExportDAO() {
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                throw new SQLException("Không thể kết nối Database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy tất cả yêu cầu xuất hàng
    public List<Nvh_RequestExport> getAllRequests() {
        List<Nvh_RequestExport> requestList = new ArrayList<>();
        String sql = "SELECT * FROM nvh_request_export";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("Đọc dữ liệu: " + rs.getInt("nvh_product_id"));

                Nvh_RequestExport request = new Nvh_RequestExport(
                    rs.getInt("nvh_request_id"),
                    rs.getInt("nvh_product_id"),
                    rs.getInt("nvh_quantity"),
                    rs.getTimestamp("nvh_request_date"),
                    rs.getString("nvh_status")
                );

                requestList.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requestList;
    }

    // Duyệt yêu cầu xuất hàng và giảm tồn kho
    public boolean approveExportRequest(int requestId) {
        PreparedStatement stmtGetRequest = null;
        PreparedStatement stmtCheckStock = null;
        PreparedStatement stmtUpdateStock = null;
        PreparedStatement stmtUpdateRequest = null;
        ResultSet rs = null;

        try {
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Lấy thông tin yêu cầu xuất hàng
            String sqlGetRequest = "SELECT nvh_product_id, nvh_quantity FROM nvh_request_export WHERE nvh_request_id = ? AND nvh_status = 'Chờ duyệt'";
            stmtGetRequest = conn.prepareStatement(sqlGetRequest);
            stmtGetRequest.setInt(1, requestId);
            rs = stmtGetRequest.executeQuery();
            if (!rs.next()) {
                return false; // Không tìm thấy yêu cầu hợp lệ
            }

            int productId = rs.getInt("nvh_product_id");
            int requestQuantity = rs.getInt("nvh_quantity");

            // 2. Kiểm tra số lượng tồn kho
            String sqlCheckStock = "SELECT nvh_quantity FROM nvh_products WHERE nvh_product_id = ?";
            stmtCheckStock = conn.prepareStatement(sqlCheckStock);
            stmtCheckStock.setInt(1, productId);
            rs = stmtCheckStock.executeQuery();
            if (!rs.next() || rs.getInt("nvh_quantity") < requestQuantity) {
                return false; // Không đủ hàng
            }

            // 3. Giảm số lượng tồn kho
            String sqlUpdateStock = "UPDATE nvh_products SET nvh_quantity = nvh_quantity - ? WHERE nvh_product_id = ?";
            stmtUpdateStock = conn.prepareStatement(sqlUpdateStock);
            stmtUpdateStock.setInt(1, requestQuantity);
            stmtUpdateStock.setInt(2, productId);
            stmtUpdateStock.executeUpdate();

            // 4. Cập nhật trạng thái yêu cầu xuất hàng
            String sqlUpdateRequest = "UPDATE nvh_request_export SET nvh_status = 'Đã duyệt' WHERE nvh_request_id = ?";
            stmtUpdateRequest = conn.prepareStatement(sqlUpdateRequest);
            stmtUpdateRequest.setInt(1, requestId);
            stmtUpdateRequest.executeUpdate();

            conn.commit(); // Xác nhận transaction
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // Hoàn tác nếu có lỗi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            closeResources(stmtGetRequest, rs);
            closeResources(stmtCheckStock, null);
            closeResources(stmtUpdateStock, null);
            closeResources(stmtUpdateRequest, null);
        }
    }

    

    // Thêm mới yêu cầu xuất hàng
    public boolean insertRequest(Nvh_RequestExport requestExport) {
        String sql = "INSERT INTO nvh_request_export (nvh_product_id, nvh_quantity, nvh_status) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, requestExport.getNvhProductId());
            stmt.setInt(2, requestExport.getNvhQuantity());
            stmt.setString(3, requestExport.getNvhStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Đóng tài nguyên
    private void closeResources(PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // đơn hàng gần đây
    public List<Nvh_RequestExport> getRecentExports(int limit) {
        List<Nvh_RequestExport> list = new ArrayList<>();
        String sql = "SELECT * FROM nvh_request_export ORDER BY nvh_request_date DESC LIMIT ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Nvh_RequestExport req = new Nvh_RequestExport();
                req.setNvhRequestId(rs.getInt("nvh_request_id"));
                req.setNvhProductId(rs.getInt("nvh_product_id"));
                req.setNvhQuantity(rs.getInt("nvh_quantity"));
                req.setNvhRequestDate(rs.getTimestamp("nvh_request_date"));
                req.setNvhStatus(rs.getString("nvh_status"));
                list.add(req);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

 // Đếm số đơn hàng chờ duyệt
    public int countPendingRequests() {
        String sql = "SELECT COUNT(*) FROM nvh_request_export WHERE nvh_status = 'Chờ duyệt'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
 // Lấy danh sách yêu cầu xuất hàng có phân trang
    public List<Nvh_RequestExport> getRequestsByPage(int page, int pageSize) {
        List<Nvh_RequestExport> list = new ArrayList<>();
        String sql = "SELECT * FROM nvh_request_export ORDER BY nvh_request_date DESC LIMIT ?, ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int start = (page - 1) * pageSize;
            stmt.setInt(1, start);
            stmt.setInt(2, pageSize);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Nvh_RequestExport req = new Nvh_RequestExport();
                req.setNvhRequestId(rs.getInt("nvh_request_id"));
                req.setNvhProductId(rs.getInt("nvh_product_id"));
                req.setNvhQuantity(rs.getInt("nvh_quantity"));
                req.setNvhRequestDate(rs.getTimestamp("nvh_request_date"));
                req.setNvhStatus(rs.getString("nvh_status"));
                list.add(req);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Đếm tổng số yêu cầu xuất hàng
    public int getTotalRequestCount() {
        String sql = "SELECT COUNT(*) FROM nvh_request_export";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //employ
    
    
}