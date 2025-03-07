package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy session hiện tại (nếu có)
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Hủy bỏ session
            session.invalidate();
        }
        // Chuyển hướng về trang đăng nhập (hoặc trang chủ tùy bạn)
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}
