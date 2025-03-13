package controller;

import dao.Nvh_SupplierDAO;
import model.Nvh_Supplier;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/addSupplier")
public class AddSupplierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Nvh_SupplierDAO supplierDAO = new Nvh_SupplierDAO();

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chỉ forward đến form addSupplier.jsp
        request.getRequestDispatcher("addSupplier.jsp").forward(request, response);
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Đảm bảo đọc form UTF-8
        request.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu form
        String name = request.getParameter("name");
        String contactInfo = request.getParameter("contactInfo");
        String address = request.getParameter("address");

        // Tạo đối tượng Nvh_Supplier
        Nvh_Supplier supplier = new Nvh_Supplier();
        supplier.setnvh_Name(name);
        supplier.setnvh_Contact_Info(contactInfo);
        supplier.setnvh_Address(address);

        // Gọi DAO để thêm
        boolean added = supplierDAO.addSupplier(supplier);

        // Chuyển hướng kèm thông báo
        if (added) {
        	supplierDAO.recalcDisplayIndex();
            String msg = URLEncoder.encode("Thêm nhà cung cấp thành công!", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/suppliers?message=" + msg);
        } else {
            String msg = URLEncoder.encode("Thêm nhà cung cấp thất bại!", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/addSupplier?message=" + msg);
        }
    }
}
