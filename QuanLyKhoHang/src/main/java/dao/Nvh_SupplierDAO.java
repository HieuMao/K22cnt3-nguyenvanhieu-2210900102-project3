package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Nvh_Supplier;

public class Nvh_SupplierDAO {

    // Lấy tất cả nhà cung cấp
    // => Nhớ đọc cả display_index
    public List<Nvh_Supplier> getAllSuppliers() {
        List<Nvh_Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT nvh_supplier_id, nvh_name, nvh_contact_info, nvh_address, display_index "
                   + "FROM Nvh_supplier";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("nvh_supplier_id");
                String name = rs.getString("nvh_name");
                String contact = rs.getString("nvh_contact_info");
                String address = rs.getString("nvh_address");
                int displayIdx = rs.getInt("display_index");

                // Tạo đối tượng supplier
                Nvh_Supplier supplier = new Nvh_Supplier(id, name, contact, address);
                // Gán display_index
                supplier.setDisplayIndex(displayIdx);

                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    // Lấy nhà cung cấp theo ID
    // => Đọc cả display_index
    public Nvh_Supplier getSupplierById(int supplierId) {
        Nvh_Supplier supplier = null;
        String sql = "SELECT nvh_supplier_id, nvh_name, nvh_contact_info, nvh_address, display_index "
                   + "FROM Nvh_supplier "
                   + "WHERE nvh_supplier_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                supplier = new Nvh_Supplier();
                supplier.setnvh_Supplier_Id(rs.getInt("nvh_supplier_id"));
                supplier.setnvh_Name(rs.getString("nvh_name"));
                supplier.setnvh_Contact_Info(rs.getString("nvh_contact_info"));
                supplier.setnvh_Address(rs.getString("nvh_address"));
                // Đọc display_index
                supplier.setDisplayIndex(rs.getInt("display_index"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }

    // Cập nhật nhà cung cấp
    public boolean updateSupplier(Nvh_Supplier supplier) {
        String sql = "UPDATE Nvh_supplier "
                   + "SET nvh_name = ?, nvh_contact_info = ?, nvh_address = ? "
                   + "WHERE nvh_supplier_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, supplier.getnvh_Name());
            stmt.setString(2, supplier.getnvh_Contact_Info());
            stmt.setString(3, supplier.getnvh_Address());
            stmt.setInt(4, supplier.getnvh_Supplier_Id());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm nhà cung cấp
    public boolean addSupplier(Nvh_Supplier supplier) {
        String sql = "INSERT INTO Nvh_supplier (nvh_name, nvh_contact_info, nvh_address) "
                   + "VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, supplier.getnvh_Name());
            stmt.setString(2, supplier.getnvh_Contact_Info());
            stmt.setString(3, supplier.getnvh_Address());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa nhà cung cấp
    public boolean deleteSupplierById(int supplierId) {
        String sql = "DELETE FROM Nvh_supplier WHERE nvh_supplier_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, supplierId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Dồn lại thứ tự hiển thị (display_index) cho nhà cung cấp
    public void recalcDisplayIndex() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // 1. Đặt @rank = 0
            stmt.execute("SET @rank := 0");

            // 2. Cập nhật display_index = rank + 1, sắp xếp theo nvh_supplier_id
            //   Thêm WHERE nvh_supplier_id > 0 để tránh safe update mode
            String sql = "UPDATE Nvh_supplier "
                       + "SET display_index = (@rank := @rank + 1) "
                       + "WHERE nvh_supplier_id > 0 "
                       + "ORDER BY nvh_supplier_id";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 // Lấy danh sách nhà cung cấp có phân trang
    public List<Nvh_Supplier> getSuppliersByPage(int page, int pageSize) {
        List<Nvh_Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT nvh_supplier_id, nvh_name, nvh_contact_info, nvh_address, display_index "
                   + "FROM Nvh_supplier "
                   + "ORDER BY display_index ASC "
                   + "LIMIT ?, ?";

        int startIndex = (page - 1) * pageSize;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, startIndex);
            stmt.setInt(2, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("nvh_supplier_id");
                String name = rs.getString("nvh_name");
                String contact = rs.getString("nvh_contact_info");
                String address = rs.getString("nvh_address");
                int displayIdx = rs.getInt("display_index");

                Nvh_Supplier supplier = new Nvh_Supplier(id, name, contact, address);
                supplier.setDisplayIndex(displayIdx);
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    // Lấy tổng số nhà cung cấp để tính tổng số trang
    public int getTotalSuppliers() {
        String sql = "SELECT COUNT(*) AS total FROM Nvh_supplier";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

