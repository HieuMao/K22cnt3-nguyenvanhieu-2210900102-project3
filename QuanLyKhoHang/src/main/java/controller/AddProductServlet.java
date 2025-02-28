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
public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	String name = request.getParameter("name");
    	double price = Double.parseDouble(request.getParameter("price"));
    	int quantity = Integer.parseInt(request.getParameter("quantity"));
    	String description = request.getParameter("description");

        try {
            // Kết nối cơ sở dữ liệu
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/QuanLyKhoHang", "root", "Mao2462004");

            // Câu lệnh SQL
            String sql = "INSERT INTO products (name, price, quantity, description) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, quantity);
            stmt.setString(4, description);

            int result = stmt.executeUpdate();
            stmt.close();
            conn.close();

            if (result > 0) {
                response.sendRedirect("addProducts.jsp?message=Sản phẩm đã được thêm thành công!");
            } else {
                response.sendRedirect("addProducts.jsp?message=Lỗi khi thêm sản phẩm!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addProducts.jsp?message=Lỗi kết nối cơ sở dữ liệu!");
        }
    }
}