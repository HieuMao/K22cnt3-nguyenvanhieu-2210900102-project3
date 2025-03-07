package controller;

import dao.Nvh_ProductDAO;
import dao.Nvh_SupplierDAO;
import model.Nvh_Product;
import model.Nvh_Supplier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/editProduct")
public class EditProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Nvh_ProductDAO productDAO = new Nvh_ProductDAO();
    private Nvh_SupplierDAO supplierDAO = new Nvh_SupplierDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/products?message=Thiếu%20ID%20sản%20phẩm");
            return;
        }
        try {
            int productId = Integer.parseInt(idStr);
            // Lấy thông tin sản phẩm
            Nvh_Product product = productDAO.getProductById(productId);
            if (product == null) {
                response.sendRedirect(request.getContextPath() + "/products?message=Sản%20phẩm%20không%20tồn%20tại");
                return;
            }
            // Lấy danh sách nhà cung cấp
            List<Nvh_Supplier> suppliers = supplierDAO.getAllSuppliers();

            // Đặt vào request
            request.setAttribute("product", product);
            request.setAttribute("suppliers", suppliers);

            // Forward sang editProduct.jsp
            request.getRequestDispatcher("editProduct.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/products?message=ID%20không%20hợp%20lệ");
        }
    }
}
