package dao;

import model.Nvh_Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Nvh_ProductDAO {

    // Lấy sản phẩm theo ID
    public Nvh_Product getProductById(int productId) {
        Nvh_Product product = null;
        String sql = "SELECT nvh_product_id, nvh_name, nvh_description, nvh_price, "
                   + "nvh_quantity, nvh_supplier_id, display_index "
                   + "FROM Nvh_products "
                   + "WHERE nvh_product_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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
                product.setDisplayIndex(rs.getInt("display_index"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    // Lấy tất cả sản phẩm (JOIN với Nvh_supplier để lấy tên supplier)
    private static final String SELECT_ALL_PRODUCTS =
        "SELECT p.nvh_product_id, p.nvh_name, p.nvh_description, p.nvh_price, "
      + "p.nvh_quantity, p.nvh_supplier_id, p.display_index, "
      + "s.nvh_name AS supplier_name "
      + "FROM Nvh_products p "
      + "LEFT JOIN Nvh_supplier s ON p.nvh_supplier_id = s.nvh_supplier_id";

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
                int displayIndex = rs.getInt("display_index");
                String supplierName = rs.getString("supplier_name");

                // Constructor 7 tham số (có supplierName)
                Nvh_Product product = new Nvh_Product(id, name, description, price, quantity, supplierId, supplierName);
                product.setDisplayIndex(displayIndex);

                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    // Xóa sản phẩm theo ID
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

    // Cập nhật sản phẩm (thêm display_index nếu muốn cập nhật luôn cột này)
    public boolean updateProduct(Nvh_Product product) {
        // Nếu bạn muốn cập nhật cả display_index, thêm "display_index=?," vào SET
        // Nếu không muốn cập nhật display_index, có thể bỏ cột này
        String sql = "UPDATE Nvh_products "
                   + "SET nvh_name=?, nvh_description=?, nvh_price=?, nvh_quantity=?, "
                   + "nvh_supplier_id=?, display_index=? "
                   + "WHERE nvh_product_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getNvhName());
            stmt.setString(2, product.getNvhDescription());
            stmt.setDouble(3, product.getNvhPrice());
            stmt.setInt(4, product.getNvhQuantity());
            stmt.setInt(5, product.getNvhSupplierId());
            stmt.setInt(6, product.getDisplayIndex());
            stmt.setInt(7, product.getNvhProductId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy sản phẩm có số lượng < 10 (hoặc < 5)
    public List<Nvh_Product> getLowStockProducts() {
        List<Nvh_Product> products = new ArrayList<>();
        String sql = "SELECT nvh_product_id, nvh_name, nvh_description, nvh_price, "
                   + "nvh_quantity, nvh_supplier_id, display_index "
                   + "FROM Nvh_products "
                   + "WHERE nvh_quantity < 10";  // hoặc <5 nếu muốn
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
                p.setDisplayIndex(rs.getInt("display_index"));

                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Thêm số lượng cho nhiều sản phẩm (sử dụng Batch)
    public boolean updateMultiple(Map<Integer, Integer> updates) {
        String sql = "UPDATE Nvh_products SET nvh_quantity = nvh_quantity + ? WHERE nvh_product_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Map.Entry<Integer, Integer> e : updates.entrySet()) {
                stmt.setInt(1, e.getValue()); // quantity to add
                stmt.setInt(2, e.getKey());   // productId
                stmt.addBatch();
            }
            stmt.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 // Đếm tổng số sản phẩm
    public int countAllProducts() {
        String sql = "SELECT COUNT(*) FROM Nvh_products";
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

    // Đếm sản phẩm sắp hết (dưới ngưỡng)
    public int countLowStockProducts(int threshold) {
        String sql = "SELECT COUNT(*) FROM Nvh_products WHERE nvh_quantity < ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, threshold); // VD: 10 hoặc 5
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    // Dồn lại thứ tự hiển thị (display_index) theo nvh_product_id
    public void recalcDisplayIndex() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // 1. Đặt @rank = 0
            stmt.execute("SET @rank := 0");

            // 2. Sắp xếp theo nvh_product_id, gán display_index = (@rank := @rank + 1)
            String sql = "UPDATE Nvh_products "
                       + "SET display_index = (@rank := @rank + 1) "
                       + "ORDER BY nvh_product_id";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    public List<Nvh_Product> getProductsByPage(int page, int pageSize) {
        List<Nvh_Product> productList = new ArrayList<>();
        String sql = "SELECT p.nvh_product_id, p.nvh_name, p.nvh_description, p.nvh_price, "
                   + "p.nvh_quantity, p.nvh_supplier_id, p.display_index, s.nvh_name AS supplier_name "
                   + "FROM Nvh_products p "
                   + "LEFT JOIN Nvh_supplier s ON p.nvh_supplier_id = s.nvh_supplier_id "
                   + "ORDER BY p.nvh_product_id "
                   + "LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pageSize);
            stmt.setInt(2, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("nvh_product_id");
                String name = rs.getString("nvh_name");
                String description = rs.getString("nvh_description");
                double price = rs.getDouble("nvh_price");
                int quantity = rs.getInt("nvh_quantity");
                int supplierId = rs.getInt("nvh_supplier_id");
                int displayIndex = rs.getInt("display_index");
                String supplierName = rs.getString("supplier_name");

                Nvh_Product product = new Nvh_Product(id, name, description, price, quantity, supplierId, supplierName);
                product.setDisplayIndex(displayIndex);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
    //// chức năng tìm kiếm theo tên
    public List<Nvh_Product> searchProductsByName(String keyword) {
        List<Nvh_Product> productList = new ArrayList<>();
        String sql = "SELECT p.nvh_product_id, p.nvh_name, p.nvh_description, p.nvh_price, "
                   + "p.nvh_quantity, p.nvh_supplier_id, p.display_index, s.nvh_name AS supplier_name "
                   + "FROM Nvh_products p "
                   + "LEFT JOIN Nvh_supplier s ON p.nvh_supplier_id = s.nvh_supplier_id "
                   + "WHERE p.nvh_name LIKE ? "
                   + "ORDER BY p.nvh_product_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%"); // Tìm kiếm tương đối (chứa từ khóa)
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("nvh_product_id");
                String name = rs.getString("nvh_name");
                String description = rs.getString("nvh_description");
                double price = rs.getDouble("nvh_price");
                int quantity = rs.getInt("nvh_quantity");
                int supplierId = rs.getInt("nvh_supplier_id");
                int displayIndex = rs.getInt("display_index");
                String supplierName = rs.getString("supplier_name");

                Nvh_Product product = new Nvh_Product(id, name, description, price, quantity, supplierId, supplierName);
                product.setDisplayIndex(displayIndex);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
}
