package com.example;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class DoctorServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        

        JSONArray doctorsArray = new JSONArray();

        try {
            
        	Connection con = DBUtil.getConnection();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM doctors");

            while (rs.next()) {
                JSONObject doc = new JSONObject();
                doc.put("id", rs.getInt("id"));
                doc.put("name", rs.getString("name"));
                doc.put("specialization", rs.getString("specialization"));
                doc.put("experience", rs.getInt("experience"));
                doc.put("image", rs.getString("image")); // ✅ ADD THIS
                doctorsArray.put(doc);
            }

            out.print(doctorsArray.toString());
            con.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
