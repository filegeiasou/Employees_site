/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package gr.uniwa.netprog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author fileg
 */
@WebServlet(name = "SaveServlet", urlPatterns = {"/save"})
public class SaveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Employee employee = new Employee();
        employee.setName(request.getParameter("name"));
        employee.setPassword(request.getParameter("password"));
        employee.setEmail(request.getParameter("email"));
        employee.setCountry(request.getParameter("country"));

        int status = EmployeeDao.save(employee);

        if (status > 0) {
            out.print("<h3 style='color:blue;'>Record saved successfully!</h3>");
        } else {
            out.println("<h3 style='color:red;'>Sorry! unable to save record</h3>");
        }
        request.getRequestDispatcher("./add.html").include(request, response);
        out.close();
    }

}
