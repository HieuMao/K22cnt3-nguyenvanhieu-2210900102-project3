package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Nvh_Supplier;

public class Nvh_SupplierDAO {
    private static final String SELECT_ALL_SUPPLIERS = 
        "SELECT nvh_supplier_id, nvh_name, nvh_contact_info, nvh_address FROM Nvh_supplier";

    public List<Nvh_Supplier> getAllSuppliers() {
        List<Nvh_Supplier> suppliers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT nvh_supplier_id, nvh_name, nvh_contact_info, nvh_address FROM Nvh_supplier"
             )) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("nvh_supplier_id");
                String name = rs.getString("nvh_name");
                String contact = rs.getString("nvh_contact_info");
                String address = rs.getString("nvh_address");
                Nvh_Supplier supplier = new Nvh_Supplier(id, name, contact, address);
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }
}
