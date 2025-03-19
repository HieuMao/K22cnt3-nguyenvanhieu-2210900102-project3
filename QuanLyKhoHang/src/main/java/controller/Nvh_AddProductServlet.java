package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddProductServlet")
public class Nvh_AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Đảm bảo đọc form với UTF-8
        request.setCharacterEncoding("UTF-8");
        // 1. Lấy dữ liệu từ form
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int supplierId = Integer.parseInt(request.getParameter("supplierId"));
        
        // 2. Kết nối DB và thêm sản phẩm theo schema mới
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
            	 "jdbc:mysql://localhost:3306/QuanLyKhoHang?useUnicode=true&characterEncoding=UTF-8",
                "root",
                "Mao2462004"
            );
            
            // Chú ý: bảng Nvh_products có các cột: nvh_name, nvh_description, nvh_price, nvh_quantity, nvh_supplier_id
            String sql = "INSERT INTO Nvh_products (nvh_name, nvh_description, nvh_price, nvh_quantity, nvh_supplier_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.setInt(4, quantity);
            stmt.setInt(5, supplierId);
            
            int result = stmt.executeUpdate();
            stmt.close();
            conn.close();
            
            // 3. Redirect về trang sản phẩm (truy cập qua servlet ProductServlet)
            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/products");
            } else {
                response.sendRedirect(request.getContextPath() + "/addProducts.jsp?error=Không thể thêm sản phẩm");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/addProducts.jsp?error=Có lỗi xảy ra");
        }
    }
}
