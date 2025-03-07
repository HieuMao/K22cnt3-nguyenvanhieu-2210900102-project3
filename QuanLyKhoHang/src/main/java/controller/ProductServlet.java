package controller;

import model.Nvh_Product;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Nvh_ProductDAO;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Khởi tạo DAO để truy cập dữ liệu sản phẩm
    private Nvh_ProductDAO productDAO = new Nvh_ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy danh sách sản phẩm từ cơ sở dữ liệu
            List<Nvh_Product> products = productDAO.getAllProducts();
            // Đặt danh sách sản phẩm vào request attribute
            request.setAttribute("products", products);
            // Forward đến products.jsp để hiển thị
            request.getRequestDispatcher("products.jsp").forward(request, response);
        } catch (Exception e) {
            // Xử lý ngoại lệ và hiển thị lỗi nếu cần
            throw new ServletException("Error retrieving products", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Dùng doPost gọi doGet để xử lý thống nhất
        doGet(request, response);
    }
}
