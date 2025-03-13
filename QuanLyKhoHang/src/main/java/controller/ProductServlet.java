package controller;

import model.Nvh_Product;
import dao.Nvh_ProductDAO;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Nvh_ProductDAO productDAO = new Nvh_ProductDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Cập nhật displayIndex trước khi lấy danh sách sản phẩm
            productDAO.recalcDisplayIndex();

            // Lấy từ khóa tìm kiếm từ request
            String keyword = request.getParameter("keyword");
            List<Nvh_Product> products;

            // Nếu có từ khóa -> tìm kiếm, ngược lại -> lấy toàn bộ sản phẩm
            if (keyword != null && !keyword.trim().isEmpty()) {
                products = productDAO.searchProductsByName(keyword);
            } else {
                products = productDAO.getAllProducts();
            }

            // Đặt danh sách sản phẩm vào request attribute
            request.setAttribute("products", products);
            // Forward đến products.jsp để hiển thị
            request.getRequestDispatcher("products.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Lỗi khi truy xuất sản phẩm", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Gọi `doGet` để xử lý request POST tương tự
    }
}
