package controller;

import dao.Nvh_SupplierDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/deleteSupplier")
public class DeleteSupplierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Nvh_SupplierDAO supplierDAO = new Nvh_SupplierDAO();

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String baseURL = request.getContextPath() + "/suppliers";
        String redirectURL = baseURL;
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int supplierId = Integer.parseInt(idStr);
                boolean success = supplierDAO.deleteSupplierById(supplierId);
                if (success) {
                    // Sau khi xóa, dồn lại display_index
                    supplierDAO.recalcDisplayIndex();
                    String msg = URLEncoder.encode("Xóa nhà cung cấp thành công!", StandardCharsets.UTF_8.toString());
                    redirectURL += "?message=" + msg;
                } else {
                    String msg = URLEncoder.encode("Xóa nhà cung cấp thất bại!", StandardCharsets.UTF_8.toString());
                    redirectURL += "?error=" + msg;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                String msg = URLEncoder.encode("ID không hợp lệ!", StandardCharsets.UTF_8.toString());
                redirectURL += "?error=" + msg;
            }
        } else {
            String msg = URLEncoder.encode("Thiếu ID nhà cung cấp!", StandardCharsets.UTF_8.toString());
            redirectURL += "?error=" + msg;
        }
        response.sendRedirect(redirectURL);
    }
}
