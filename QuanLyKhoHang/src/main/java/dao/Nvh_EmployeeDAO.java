package dao;

import model.Nvh_Employee;
import java.sql.*;

public class Nvh_EmployeeDAO {
    public Nvh_Employee login(String username, String password) {
        String sql = "SELECT * FROM Nvh_employees WHERE nvh_username = ? AND nvh_password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Nvh_Employee(
                    rs.getInt("nvh_employee_id"),
                    rs.getString("nvh_username"),
                    rs.getString("nvh_password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
