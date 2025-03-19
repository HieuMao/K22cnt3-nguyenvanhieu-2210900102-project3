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
public class Nvh_UpdateProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Nvh_ProductDAO productDAO = new Nvh_ProductDAO();

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. Đảm bảo đọc form UTF-8 (tránh lỗi tiếng Việt)
        request.setCharacterEncoding("UTF-8");

        // 2. Lấy dữ liệu form
        int productId = Integer.parseInt(request.getParameter("productId"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String description = request.getParameter("description");
        int supplierId = Integer.parseInt(request.getParameter("supplierId"));

        // 3. Tạo đối tượng product
        Nvh_Product product = new Nvh_Product(productId, name, description, price, quantity, supplierId);

        // 4. Gọi updateProduct để cập nhật DB
        boolean updated = productDAO.updateProduct(product);

        // 5. Chuyển hướng kèm thông báo
        if (updated) {
        	productDAO.recalcDisplayIndex();
            // Encode tiếng Việt
            String successMsg = URLEncoder.encode("Cập nhật thành công!", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/products?message=" + successMsg);
        } else {
            String errorMsg = URLEncoder.encode("Cập nhật thất bại!", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/editProduct?id=" + productId 
                + "&message=" + errorMsg);
        }
    }
}
