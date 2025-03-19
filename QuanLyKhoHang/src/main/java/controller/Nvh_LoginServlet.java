package controller;

import dao.AdminDAO;
import dao.Nvh_EmployeeDAO;
import model.Nvh_Admin;
import model.Nvh_Employee;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/LoginServlet")
public class Nvh_LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            // Xử lý đăng xuất
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();
        String errorMsg = null;

        // Kiểm tra đăng nhập với Admin
        AdminDAO adminDAO = new AdminDAO();
        Nvh_Admin nvh_Admin = adminDAO.getAdminByUsernameAndPassword(username, password);

        if (nvh_Admin != null) {
            session.setAttribute("loggedInAdmin", nvh_Admin);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        // Nếu không phải Admin, kiểm tra trong Employee
        Nvh_EmployeeDAO employeeDAO = new Nvh_EmployeeDAO();
        Nvh_Employee employee = employeeDAO.login(username, password);

        if (employee != null) {
            session.setAttribute("loggedInEmployee", employee);
            response.sendRedirect(request.getContextPath() + "/employee-dashboard.jsp");
            return;
        }

        // Nếu không tìm thấy trong cả hai bảng
        errorMsg = "Thông tin đăng nhập không chính xác!";
        String encodedMsg = URLEncoder.encode(errorMsg, StandardCharsets.UTF_8.toString());
        response.sendRedirect(request.getContextPath() + "/login.jsp?error=" + encodedMsg);
    }
}
