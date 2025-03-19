package controller;

import dao.Nvh_SupplierDAO;
import model.Nvh_Supplier;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/suppliers")
public class Nvh_SupplierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Nvh_SupplierDAO supplierDAO = new Nvh_SupplierDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Cập nhật lại thứ tự hiển thị nếu cần
        supplierDAO.recalcDisplayIndex();

        // Lấy tham số trang từ request (mặc định là trang 1)
        int page = 1;
        int pageSize = 5; // Số lượng nhà cung cấp hiển thị trên mỗi trang
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        // Lấy danh sách nhà cung cấp có phân trang
        List<Nvh_Supplier> suppliers = supplierDAO.getSuppliersByPage(page, pageSize);

        // Lấy tổng số nhà cung cấp để tính tổng số trang
        int totalSuppliers = supplierDAO.getTotalSuppliers();
        int totalPages = (int) Math.ceil((double) totalSuppliers / pageSize);

        // Đặt dữ liệu vào request attribute
        request.setAttribute("suppliers", suppliers);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Forward đến JSP để hiển thị danh sách
        request.getRequestDispatcher("supplier.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
