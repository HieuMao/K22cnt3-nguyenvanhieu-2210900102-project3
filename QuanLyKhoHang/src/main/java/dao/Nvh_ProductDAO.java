package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Nvh_Product;

public class Nvh_ProductDAO {
	public Nvh_Product getProductById(int productId) {
	    Nvh_Product product = null;
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(
	             "SELECT nvh_product_id, nvh_name, nvh_description, nvh_price, nvh_quantity, nvh_supplier_id "
	             + "FROM Nvh_products WHERE nvh_product_id = ?")) {
	        stmt.setInt(1, productId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            product = new Nvh_Product();
	            product.setNvhProductId(rs.getInt("nvh_product_id"));
	            product.setNvhName(rs.getString("nvh_name"));
	            product.setNvhDescription(rs.getString("nvh_description"));
	            product.setNvhPrice(rs.getDouble("nvh_price"));
	            product.setNvhQuantity(rs.getInt("nvh_quantity"));
	            product.setNvhSupplierId(rs.getInt("nvh_supplier_id"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return product;
	}
    // Cập nhật số lượng sản phẩm
    public boolean updateQuantity(int productId, int newQuantity) {
        boolean rowUpdated = false;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "UPDATE Nvh_products SET nvh_quantity = ? WHERE nvh_product_id = ?")) {
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, productId);
            rowUpdated = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    // Truy vấn JOIN giữa Nvh_products và Nvh_supplier
    private static final String SELECT_ALL_PRODUCTS = 
        "SELECT p.nvh_product_id, p.nvh_name, p.nvh_description, p.nvh_price, p.nvh_quantity, " +
        "p.nvh_supplier_id, s.nvh_name AS supplier_name " +
        "FROM Nvh_products p " +
        "LEFT JOIN Nvh_supplier s ON p.nvh_supplier_id = s.nvh_supplier_id";

    public List<Nvh_Product> getAllProducts() {
        List<Nvh_Product> productList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_PRODUCTS)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("nvh_product_id");
                String name = rs.getString("nvh_name");
                String description = rs.getString("nvh_description");
                double price = rs.getDouble("nvh_price");
                int quantity = rs.getInt("nvh_quantity");
                int supplierId = rs.getInt("nvh_supplier_id");
                String supplierName = rs.getString("supplier_name");

                Nvh_Product product = new Nvh_Product(id, name, description, price, quantity, supplierId, supplierName);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    // Phương thức deleteProductById cũng giữ nguyên
    public boolean deleteProductById(int productId) {
        String sql = "DELETE FROM Nvh_products WHERE nvh_product_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // cập nhập sản phẩm
    public boolean updateProduct(Nvh_Product product) {
        String sql = "UPDATE Nvh_products "
                   + "SET nvh_name=?, nvh_description=?, nvh_price=?, nvh_quantity=?, nvh_supplier_id=? "
                   + "WHERE nvh_product_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getNvhName());
            stmt.setString(2, product.getNvhDescription());
            stmt.setDouble(3, product.getNvhPrice());
            stmt.setInt(4, product.getNvhQuantity());
            stmt.setInt(5, product.getNvhSupplierId());
            stmt.setInt(6, product.getNvhProductId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 // Phương thức lấy danh sách sản phẩm có nvh_quantity < 5
    public List<Nvh_Product> getLowStockProducts() {
        List<Nvh_Product> products = new ArrayList<>();
        String sql = "SELECT nvh_product_id, nvh_name, nvh_description, nvh_price, nvh_quantity, nvh_supplier_id "
                   + "FROM Nvh_products WHERE nvh_quantity < 10";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Nvh_Product p = new Nvh_Product();
                p.setNvhProductId(rs.getInt("nvh_product_id"));
                p.setNvhName(rs.getString("nvh_name"));
                p.setNvhDescription(rs.getString("nvh_description"));
                p.setNvhPrice(rs.getDouble("nvh_price"));
                p.setNvhQuantity(rs.getInt("nvh_quantity"));
                p.setNvhSupplierId(rs.getInt("nvh_supplier_id"));
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public boolean updateMultiple(Map<Integer, Integer> updates) {
        // Tư tưởng: "UPDATE Nvh_products SET nvh_quantity = nvh_quantity + ? WHERE nvh_product_id=?"
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "UPDATE Nvh_products SET nvh_quantity = nvh_quantity + ? WHERE nvh_product_id=?")) {

            for (Map.Entry<Integer, Integer> e : updates.entrySet()) {
                stmt.setInt(1, e.getValue());     // quantity to add
                stmt.setInt(2, e.getKey());       // productId
                stmt.addBatch();
            }
            int[] results = stmt.executeBatch();
            // Nếu cần kiểm tra kết quả, ta duyệt mảng results
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}