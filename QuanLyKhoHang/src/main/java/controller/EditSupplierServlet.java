package controller;

import dao.Nvh_SupplierDAO;
import model.Nvh_Supplier;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/editSupplier")
public class EditSupplierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Nvh_SupplierDAO supplierDAO = new Nvh_SupplierDAO();

    // Hiển thị form sửa (GET)
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect(request.getContextPath() + "/suppliers?message=Thiếu%20ID");
            return;
        }
        try {
            int supplierId = Integer.parseInt(idStr);
            Nvh_Supplier supplier = supplierDAO.getSupplierById(supplierId);
            if (supplier == null) {
                response.sendRedirect(request.getContextPath() + "/suppliers?message=Nhà%20cung%20cấp%20không%20tồn%20tại");
                return;
            }
            request.setAttribute("supplier", supplier);
            request.getRequestDispatcher("editSupplier.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/suppliers?message=ID%20không%20hợp%20lệ");
        }
    }

    // Xử lý cập nhật (POST)
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Đảm bảo đọc form UTF-8
        request.setCharacterEncoding("UTF-8");

        try {
            int supplierId = Integer.parseInt(request.getParameter("supplierId"));
            String name = request.getParameter("name");
            String contactInfo = request.getParameter("contactInfo");
            String address = request.getParameter("address");

            Nvh_Supplier supplier = new Nvh_Supplier(supplierId, name, contactInfo, address);
            boolean updated = supplierDAO.updateSupplier(supplier);
            if (updated) {
            	supplierDAO.recalcDisplayIndex();
                String msg = URLEncoder.encode("Cập nhật nhà cung cấp thành công!", StandardCharsets.UTF_8);
                response.sendRedirect(request.getContextPath() + "/suppliers?message=" + msg);
            } else {
                String msg = URLEncoder.encode("Cập nhật thất bại!", StandardCharsets.UTF_8);
                response.sendRedirect(request.getContextPath() + "/editSupplier?id=" + supplierId 
                    + "&message=" + msg);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/suppliers?message=ID%20không%20hợp%20lệ");
        }
    }
}
