package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/BookAppointmentServlet")
public class BookAppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // ✅ Check login
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            out.println("<script>alert('Please log in first!'); window.location='login.html';</script>");
            return;
        }
        String username = (String) session.getAttribute("username");

        // ✅ Get form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String doctor = request.getParameter("doctor_name");
        String date = request.getParameter("appointment_date");   // yyyy-MM-dd (HTML gives correct format)
        String time = request.getParameter("appointment_time");   // HH:mm
        if (time != null && time.length() == 5) time = time + ":00"; // fix HH:mm -> HH:mm:ss

        try {
            
        	Connection con = DBUtil.getConnection();

            // ✅ Get user_id from users table
            int userId = 0;
            PreparedStatement ps1 = con.prepareStatement("SELECT id FROM users WHERE first_name=?");
            ps1.setString(1, username);
            ResultSet rs = ps1.executeQuery();
            if (rs.next()) userId = rs.getInt("id");

            System.out.println("DEBUG: " + date + " " + time + " user=" + userId);

            // ✅ Check for duplicate appointment (same doctor + same time/date)
            String checkQuery = "SELECT COUNT(*) FROM appointments WHERE doctor_name=? AND appointment_date=? AND appointment_time=?";
            PreparedStatement psCheck = con.prepareStatement(checkQuery);
            psCheck.setString(1, doctor);
            psCheck.setString(2, date);
            psCheck.setString(3, time);
            ResultSet rsCheck = psCheck.executeQuery();
            rsCheck.next();
            int count = rsCheck.getInt(1);

            if (count > 0) {
                out.println("<script>alert('That time slot is already booked!'); window.location='appointment.html';</script>");
                con.close();
                return;
            }

            // ✅ Insert appointment
            String insertQuery = "INSERT INTO appointments (name, email, doctor_name, appointment_date, appointment_time, user_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps2 = con.prepareStatement(insertQuery);
            ps2.setString(1, name);
            ps2.setString(2, email);
            ps2.setString(3, doctor);
            ps2.setString(4, date);
            ps2.setString(5, time);
            ps2.setInt(6, userId);

            int row = ps2.executeUpdate();

            if (row > 0) {
                out.println("<script>alert('Appointment booked successfully!'); window.location='index.html';</script>");
            } else {
                out.println("<script>alert('Failed to book appointment!'); window.location='appointment.html';</script>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.location='appointment.html';</script>");
        }
    }
}
