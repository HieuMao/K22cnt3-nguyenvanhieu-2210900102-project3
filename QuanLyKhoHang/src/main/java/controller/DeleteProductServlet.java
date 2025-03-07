package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.Nvh_ProductDAO;

@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Nvh_ProductDAO productDAO = new Nvh_ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số "id" từ URL
        String idStr = request.getParameter("id");
        String baseURL = request.getContextPath() + "/products";
        String redirectURL = baseURL;
        
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                boolean success = productDAO.deleteProductById(id);
                if (success) {
                    redirectURL += "?message=" + URLEncoder.encode("Xóa sản phẩm thành công!", StandardCharsets.UTF_8.toString());
                } else {
                    redirectURL += "?error=" + URLEncoder.encode("Xóa sản phẩm thất bại!", StandardCharsets.UTF_8.toString());
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                redirectURL += "?error=" + URLEncoder.encode("ID sản phẩm không hợp lệ!", StandardCharsets.UTF_8.toString());
            }
        } else {
            redirectURL += "?error=" + URLEncoder.encode("Không tìm thấy ID sản phẩm!", StandardCharsets.UTF_8.toString());
        }
        response.sendRedirect(redirectURL);
    }
}
