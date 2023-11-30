package com.itsc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class AddEmployeeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String query = "INSERT INTO employe(name, designation, salary) VALUES (?, ?, ?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Get PrintWriter
        PrintWriter pw = resp.getWriter();
        // Set content type
        resp.setContentType("text/html");

        // Get the employee info
        String name = req.getParameter("name");
        String designation = req.getParameter("designation");
        String salaryParam = req.getParameter("salary");
        float salary = 0.0f;
        boolean hasError = false;

        // Validate name
        if (name == null || name.trim().isEmpty()) {
            hasError = true;
            pw.println("<p>Name is required.</p>");
        }

        // Validate designation
        if (designation == null || designation.trim().isEmpty()) {
            hasError = true;
            pw.println("<p>Designation is required.</p>");
        }

        // Validate salary
        if (salaryParam == null || salaryParam.trim().isEmpty()) {
            hasError = true;
            pw.println("<p>Salary is required.</p>");
        } else {
            try {
                salary = Float.parseFloat(salaryParam);
            } catch (NumberFormatException e) {
                hasError = true;
                pw.println("<p>Invalid salary format. Please enter a valid number.</p>");
            }
        }

        if (!hasError) {
            // Load the JDBC driver
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException cnf) {
                cnf.printStackTrace();
            }

            // Generate the connection and execute the prepared statement
            try (Connection conn = DriverManager.getConnection("jdbc:mysql:///employee", "root", "baba11@")) {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, designation);
                ps.setFloat(3, salary);
                int count = ps.executeUpdate();
                if (count == 1) {
                    pw.println("<h2>Employee registered successfully.</h2>");
                } else {
                    pw.println("<h2>Employee not registered successfully.</h2>");
                }
            } catch (SQLException se) {
                se.printStackTrace();
                pw.println("<h1>" + se.getMessage() + "</h1>");
            } catch (Exception e) {
                e.printStackTrace();
                pw.println("<h1>" + e.getMessage() + "</h1>");
            }
        }

        pw.println("<a href='NewFile.html'>Home</a>");
        pw.print("<br>");
        pw.println("<a href='employeelist'>Employee List</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}