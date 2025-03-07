package controller;

import dao.Nvh_ProductDAO;
import model.Nvh_Product;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Nvh_ProductDAO productDAO = new Nvh_ProductDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Lấy dữ liệu từ form
            int productId = Integer.parseInt(request.getParameter("productId"));
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String description = request.getParameter("description");
            int supplierId = Integer.parseInt(request.getParameter("supplierId")); // Lấy từ dropdown

            // 2. Tạo đối tượng
            Nvh_Product product = new Nvh_Product(productId, name, description, price, quantity, supplierId);

            // 3. Cập nhật
            boolean updated = productDAO.updateProduct(product);

            // 4. Xử lý kết quả
            if (updated) {
                // Thành công
                String successMsg = URLEncoder.encode("Cập nhật thành công!", StandardCharsets.UTF_8.toString());
                response.sendRedirect(request.getContextPath() + "/products?message=" + successMsg);
            } else {
                // Thất bại
                String errorMsg = URLEncoder.encode("Không thể cập nhật!", StandardCharsets.UTF_8.toString());
                response.sendRedirect(request.getContextPath() 
                    + "/editProduct?id=" + productId 
                    + "&message=" + errorMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Lỗi khác
            String errorMsg = URLEncoder.encode("Lỗi khi cập nhật!", StandardCharsets.UTF_8.toString());
            response.sendRedirect(request.getContextPath() + "/products?message=" + errorMsg);
        }
    }
}
