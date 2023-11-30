package com.itsc;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/operation")
public class OperationServlet extends HttpServlet {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String operation = request.getParameter("operation");

        if (operation.equals("+")) {
            request.getRequestDispatcher("/addition").forward(request, response);
        } else if (operation.equals("*")) {
            request.getRequestDispatcher("/multiplication").forward(request, response);
        } else {
            response.setContentType("text/html");
            response.getWriter().println("<h2>Invalid operation selected!</h2>");
        }
    }
}