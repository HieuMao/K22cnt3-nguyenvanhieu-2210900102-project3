	package controller;
	
	import dao.Nvh_RequestExportDAO;
	import model.Nvh_RequestExport;
	
	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import java.io.IOException;
	import java.util.List;
	
	@WebServlet("/RequestExportServlet")
	public class RequestExportServlet extends HttpServlet {
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        try {
	            String productIdStr = request.getParameter("product_id");
	            String quantityStr = request.getParameter("quantity");
	
	            if (productIdStr == null || quantityStr == null || productIdStr.isEmpty() || quantityStr.isEmpty()) {
	                response.sendRedirect("request-export.jsp?error=missing_data");
	                return;
	            }
	
	            int productId = Integer.parseInt(productIdStr);
	            int quantity = Integer.parseInt(quantityStr);
	
	            if (quantity <= 0) {
	                response.sendRedirect("request-export.jsp?error=invalid_quantity");
	                return;
	            }
	
	            // Debug log
	            System.out.println("Tạo yêu cầu xuất hàng - Product ID: " + productId + ", Quantity: " + quantity);
	
	            // Tạo yêu cầu
	            Nvh_RequestExport requestExport = new Nvh_RequestExport(productId, quantity, "Chờ duyệt");
	
	            // Lưu vào DB
	            Nvh_RequestExportDAO requestExportDAO = new Nvh_RequestExportDAO();
	            boolean success = requestExportDAO.insertRequest(requestExport);
	
	            if (success) {
	                response.sendRedirect("request-export.jsp?success=true");
	            } else {
	                response.sendRedirect("request-export.jsp?error=db_error");
	            }
	
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	            response.sendRedirect("request-export.jsp?error=invalid_input");
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("request-export.jsp?error=server_error");
	        }
	    }
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        int page = 1;
	        int pageSize = 5; // Hiển thị 5 yêu cầu mỗi trang
	
	        if (request.getParameter("page") != null) {
	            try {
	                page = Integer.parseInt(request.getParameter("page"));
	            } catch (NumberFormatException e) {
	                page = 1;
	            }
	        }
	
	        Nvh_RequestExportDAO requestExportDAO = new Nvh_RequestExportDAO();
	        List<Nvh_RequestExport> requests = requestExportDAO.getRequestsByPage(page, pageSize);
	        int totalRequests = requestExportDAO.getTotalRequestCount();
	        int totalPages = (int) Math.ceil((double) totalRequests / pageSize);
	
	        request.setAttribute("requests", requests);
	        request.setAttribute("currentPage", page);
	        request.setAttribute("totalPages", totalPages);
	
	        request.getRequestDispatcher("request-export.jsp").forward(request, response);
	    }
	}
