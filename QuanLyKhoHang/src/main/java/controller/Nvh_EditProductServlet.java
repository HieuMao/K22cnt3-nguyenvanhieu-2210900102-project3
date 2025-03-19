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
public class Nvh_EditProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Nvh_ProductDAO productDAO = new Nvh_ProductDAO();
    private Nvh_SupplierDAO supplierDAO = new Nvh_SupplierDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            // Ghi log lỗi
            log("Lỗi: Thiếu ID sản phẩm");
            // Hiển thị thông báo lỗi cho người dùng
            request.setAttribute("errorMessage", "Thiếu ID sản phẩm");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        try {
            int productId = Integer.parseInt(idStr);
            // Lấy thông tin sản phẩm
            Nvh_Product product = productDAO.getProductById(productId);
            if (product == null) {
                // Ghi log lỗi
                log("Lỗi: Sản phẩm không tồn tại với ID = " + productId);
                // Hiển thị thông báo lỗi cho người dùng
                request.setAttribute("errorMessage", "Sản phẩm không tồn tại");
                request.getRequestDispatcher("error.jsp").forward(request, response);
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
            // Ghi log lỗi
            log("Lỗi: ID không hợp lệ: " + idStr, e);
            // Hiển thị thông báo lỗi cho người dùng
            request.setAttribute("errorMessage", "ID không hợp lệ");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            // Ghi log lỗi
            log("Lỗi trong quá trình xử lý", e);
            // Hiển thị thông báo lỗi cho người dùng
            request.setAttribute("errorMessage", "Có lỗi xảy ra trong quá trình xử lý");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            // Ghi log lỗi
            log("Lỗi không xác định", e);
            // Hiển thị thông báo lỗi cho người dùng
            request.setAttribute("errorMessage", "Có lỗi xảy ra");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}