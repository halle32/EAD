package com.itsc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class edit extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String query = "UPDATE employe SET name=?, designation=?, salary=? WHERE id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // Get PrintWriter
        PrintWriter pw = resp.getWriter();
        // Set content type
        resp.setContentType("text/html");
        // Get the id of the record
        int id = Integer.parseInt(req.getParameter("id"));
        // Get the edited data
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
            // Load the driver
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException cnf) {
                cnf.printStackTrace();
            }
            // Generate the connection
            try (Connection conn = DriverManager.getConnection("jdbc:mysql:///employee", "root", "baba11@")) {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, designation);
                ps.setFloat(3, salary);
                ps.setInt(4, id);
                int count = ps.executeUpdate();
                if (count == 1) {
                    pw.println("<h2>Record is edited successfully.</h2>");
                } else {
                    pw.println("<h2>Record not edited.</h2>");
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}