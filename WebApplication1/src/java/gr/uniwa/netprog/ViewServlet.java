
package gr.uniwa.netprog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author fileg
 */
@WebServlet(name = "ViewServlet", urlPatterns = {"/view"})
public class ViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>View Employees</title>");
        out.println("""
                    <style>
                     table {
                     font-family: arial, sans-serif;
                     border-collapse: collapse;
                     width: 60%;
                     }
                    
                     td, th {
                     border: 1px solid #dddddd;
                     text-align: left;
                     padding: 8px;
                     }
                    
                     tr:nth-child(even) {
                     background-color: #dddddd;
                     }
                     </style>""");
        out.println("</head>");
        out.println("<body>");

        out.println("<h1>Employees List</h1>");

        List<Employee> list = EmployeeDao.getAllEmployees();

        out.print("<table border='1' width='100%'");
        out.print("<tr><th>Id</th><th>Name</th><th>Password</th><th>Email</th><th>Country</th><th>Edit</th><th>Delete</th></tr>");

        for (Employee e : list) {
            out.print("<tr>"
                    + "<td>" + e.getId() + "</td>"
                    + "<td>" + e.getName() + "</td>"
                    + "<td>" + e.getPassword() + "</td>"
                    + "<td>" + e.getEmail() + "</td>"
                    + "<td>" + e.getCountry() + "</td>"
                    + "<td><a href='./edit?id=" + e.getId() + "'>edit</a></td>"
                    + "<td><a href='./delete?id=" + e.getId() + "'>delete</a></td>"
                    + "</tr>");
        }
        out.print("</table>");
        out.println("<p><a href='./add.html'>Add New Employee</a></p>");
        out.println("<p><a href='./index.html'>Home</a></p>");
        out.println("</body>");
        out.println("</html>");

        out.close();

    }

}
