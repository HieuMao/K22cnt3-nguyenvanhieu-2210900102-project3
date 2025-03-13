package controller;

import dao.Nvh_RequestExportDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ApproveRequestServlet")
public class ApproveRequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int requestId = Integer.parseInt(request.getParameter("request_id"));

            System.out.println("Duyệt yêu cầu xuất hàng với ID: " + requestId);

            Nvh_RequestExportDAO requestDAO = new Nvh_RequestExportDAO();
            boolean success = requestDAO.approveExportRequest(requestId);

            if (success) {
                response.sendRedirect("admin-request-export.jsp?success=1");
            } else {
                response.sendRedirect("admin-request-export.jsp?error=out_of_stock");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("admin-request-export.jsp?error=invalid_id");
        }
    }
}
