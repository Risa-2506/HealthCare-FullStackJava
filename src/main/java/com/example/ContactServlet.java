package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	PrintWriter out1 = response.getWriter();
    	HttpSession session2 = request.getSession(false); // do not create new session
        if (session2 == null || session2.getAttribute("username") == null) {
        	
        	out1.println("<script>alert('Login First!');window.location='login.html';</script>");
            // User not logged in → redirect to login page
            //response.sendRedirect("login.html");
            return;
        }

                
        // Get logged-in username from session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username"); // logged-in user
        PrintWriter out = response.getWriter();
        // Get form parameters
        String fullName = request.getParameter("full_name");
        String email = request.getParameter("email_address");
        String phone = request.getParameter("phone_number");
        String department = request.getParameter("department");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        try {
            // Load MySQL JDBC driver
            
        	Connection con = DBUtil.getConnection();

            // Get user_id from users table
            int userId = 0;
            String getUserQuery = "SELECT id FROM users WHERE first_name=?";
            PreparedStatement pst1 = con.prepareStatement(getUserQuery);
            pst1.setString(1, username);
            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id");
            }

            // Insert into contact table
            String sql = "INSERT INTO contact (full_name, email_address, phone_number, department, subject, message, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst2 = con.prepareStatement(sql);
            pst2.setString(1, fullName);
            pst2.setString(2, email);
            pst2.setString(3, phone);
            pst2.setString(4, department);
            pst2.setString(5, subject);
            pst2.setString(6, message);
            pst2.setInt(7, userId);

            int row = pst2.executeUpdate();
            if (row > 0) {
            	out.println("<script>alert('Contact Details submitted successfully!'); window.location='index.html';</script>");
            } else {
                response.getWriter().println("Error submitting contact.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database error: " + e.getMessage());
        }
    }
}
