package com.example;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        // Get the current session, if it exists
        HttpSession session = request.getSession(false); // false = don't create a new session
        if (session != null) {
            session.invalidate(); // destroy session
        }
        out.println("<script>alert('LogOut successful!'); window.location='index.html';</script>");
        // Redirect user to login page after logout
        
    }
}
