package controller;

import dao.Nvh_SupplierDAO;
import model.Nvh_Supplier;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/addProductPage")
public class AddProductPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Nvh_SupplierDAO supplierDAO = new Nvh_SupplierDAO();

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Nvh_Supplier> suppliers = supplierDAO.getAllSuppliers();
        // Debug
        System.out.println("AddProductPageServlet -> suppliers.size() = " + suppliers.size());
        
        request.setAttribute("suppliers", suppliers);
        request.getRequestDispatcher("addProducts.jsp").forward(request, response);
    }
}
