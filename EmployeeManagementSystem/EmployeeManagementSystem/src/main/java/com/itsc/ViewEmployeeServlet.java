package com.itsc;

import java.sql.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

;

@WebServlet("/employeelist")
public class ViewEmployeeServlet extends HttpServlet {

	private static final String query = "select id, name, designation, salary from employe";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        // Generate the connection and execute the prepared statement
        try (Connection conn = DriverManager.getConnection("jdbc:mysql:///employee", "root", "baba11@")){
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            pw.println("<html>");
            pw.println("<head>");
            pw.println("<title>employees</title>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h1>employee list</h1>");
            pw.println("<table border='1'>");
            pw.println("<tr>");
            pw.println("<th>employee Id</th>");
            pw.println("<th>employee Name</th>");
            pw.println("<th>employee Designation</th>");
            pw.println("<th>employee salary</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");

            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getInt(1) + "</td>");
                pw.println("<td>" + rs.getString(2) + "</td>");
                pw.println("<td>" + rs.getString(3) + "</td>");
                pw.println("<td>" + rs.getFloat(4) + "</td>");
                pw.println("<td><a href='editscreen?id=" + rs.getInt(1) + "'>edit</a></td>");
                pw.println("<td><a href='deleteurl?id=" + rs.getInt(1) + "'>delete</a></td>");
                pw.println("</tr>");
            }

            pw.println("</table>");
            pw.println("<br>");
            pw.println("<a href='NewFile.html'>register</a>");
            pw.println("</body>");
            pw.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
            pw.println("<a href='NewFile.html'>Home</a>");
        }
    }
}
