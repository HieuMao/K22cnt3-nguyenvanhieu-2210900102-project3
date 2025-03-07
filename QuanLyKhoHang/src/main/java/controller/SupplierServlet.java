package controller;

import dao.Nvh_SupplierDAO;
import model.Nvh_Supplier;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/suppliers")
public class SupplierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Nvh_SupplierDAO supplierDAO = new Nvh_SupplierDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách nhà cung cấp từ database thông qua DAO
        List<Nvh_Supplier> suppliers = supplierDAO.getAllSuppliers();
        // Đặt danh sách vào request attribute để JSP sử dụng
        request.setAttribute("suppliers", suppliers);
        // Forward đến trang supplier.jsp để hiển thị danh sách
        request.getRequestDispatcher("supplier.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
