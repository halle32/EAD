package com.itsc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editscreen")
public class editscreen extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String query = "select name, designation, salary from employe where id = ?";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Get PrintWriter
        PrintWriter pw = resp.getWriter();
        // Set content type
        resp.setContentType("text/html");
        // Get the id of the record
        int id = Integer.parseInt(req.getParameter("id"));

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        // Generate the connection
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql:///employee", "root", "baba11@");
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            pw.println("<form action='editurl?id=" + id + "' method='post'>");
            pw.println("<table>");
            pw.println("<tr>");
            pw.println("<td>name</td>");
            pw.println("<td><input type='text' name='name' value='" + rs.getString(1) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>designation</td>");
            pw.println("<td><input type='text' name='designation' value='" + rs.getString(2) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>salary</td>");
            pw.println("<td><input type='text' name='salary' value='" + rs.getFloat(3) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td><input type='submit' value='Edit'></td>");
            pw.println("<td><input type='reset' value='Cancel'></td>");
            pw.println("</tr>");
            pw.println("</table>");
            pw.println("</form>");

        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }

        pw.println("<a href='NewFile.html'>Home</a>");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}

