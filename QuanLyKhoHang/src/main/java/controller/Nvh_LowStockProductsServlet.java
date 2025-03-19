package controller;

import dao.Nvh_ProductDAO;
import model.Nvh_Product;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/lowStockProducts")
public class Nvh_LowStockProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Nvh_ProductDAO productDAO = new Nvh_ProductDAO();

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách sản phẩm có số lượng < 5
        List<Nvh_Product> products = productDAO.getLowStockProducts();
        

        // Đặt danh sách vào request attribute
        request.setAttribute("products", products);
        
        // Forward sang trang JSP hiển thị
        request.getRequestDispatcher("lowStockProducts.jsp").forward(request, response);
    }
}
