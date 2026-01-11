package com.example;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            
        	Connection con = DBUtil.getConnection();

            String query = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                // Set session
            	String name = rs.getString("first_name");
                HttpSession session = request.getSession();
                session.setAttribute("username", name);

                out.println("<script>alert('Login successful!'); window.location='index.html';</script>");            
                } else 
                {
                	out.println("<script>alert('Invalid email or password'); window.location='login.html';</script>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
