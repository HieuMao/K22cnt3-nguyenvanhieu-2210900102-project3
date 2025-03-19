package controller;

import dao.Nvh_ProductDAO;
import model.Nvh_Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/importProductsServlet")
public class Nvh_ImportProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Nvh_ProductDAO productDAO = new Nvh_ProductDAO();

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Debug: thông báo vào console
        System.out.println("ImportProductsServlet doGet called...");

        // Lấy danh sách sản phẩm có số lượng < 5
        List<Nvh_Product> products = productDAO.getLowStockProducts();
        System.out.println("Found " + products.size() + " products with quantity < 10.");

        // Đặt vào request attribute
        request.setAttribute("products", products);

        // Forward sang JSP
        request.getRequestDispatcher("lowStockProducts.jsp").forward(request, response);
        System.out.println("Forwarded to lowStockProducts.jsp.");
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Debug
        System.out.println("ImportProductsServlet doPost called...");

        // 1. Lấy danh sách sản phẩm (có số lượng < 5)
        List<Nvh_Product> products = productDAO.getLowStockProducts();
        System.out.println("Found " + products.size() + " products with quantity < 5 for import.");

        // 2. Tạo Map {productId -> quantityToAdd}
        Map<Integer, Integer> updates = new HashMap<>();

        // 3. Duyệt qua từng sản phẩm, đọc input name="importQty_<id>"
        for (Nvh_Product p : products) {
            String paramName = "importQty_" + p.getNvhProductId();
            String paramValue = request.getParameter(paramName);

            if (paramValue != null && !paramValue.trim().isEmpty()) {
                try {
                    int qtyToAdd = Integer.parseInt(paramValue);
                    if (qtyToAdd > 0) {
                        updates.put(p.getNvhProductId(), qtyToAdd);
                        System.out.println("Product ID=" + p.getNvhProductId() 
                            + " => importQty=" + qtyToAdd);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number for " + paramName + ": " + paramValue);
                }
            }
        }

        // 4. Gọi DAO để cập nhật (cần viết updateMultiple(...) trong DAO)
        boolean success = false;
        if (!updates.isEmpty()) {
            success = productDAO.updateMultiple(updates);
        }
        // 5. Thông báo
        String msg = success ? "Nhập hàng thành công!" : "Không có sản phẩm nào được nhập hàng!";
        // URL-encode tiếng Việt
        String encodedMsg = URLEncoder.encode(msg, StandardCharsets.UTF_8.toString());

        // Chuyển hướng về doGet để hiển thị lại danh sách
        response.sendRedirect(request.getContextPath() 
            + "/importProductsServlet?message=" + encodedMsg);
        System.out.println("Redirecting with message: " + msg);
    }
}
