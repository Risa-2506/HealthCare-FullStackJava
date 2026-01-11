package com.example;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class SignUpServlet extends HttpServlet {
	
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	
    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        

        // Get form values
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String bloodGroup = request.getParameter("blood_group");
        String age = request.getParameter("age");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String emergency1 = request.getParameter("emergency_contact_name");
        String emergency2 = request.getParameter("emergency_contact_tel");
        String street = request.getParameter("street_address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipcode = request.getParameter("zipcode");
        String password = request.getParameter("password");

        // Basic email validation
        if(!email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
        	out.println("<script>alert('Invalid email'); window.location='register.html' </script>"); 
        	return;
        }

        // Password length check
        if(password.length() < 8) {
        	out.println("<script>alert('Error: Password must be at least 8 characters.'); window.location='register.html' </script>");
            return;
        }

        try {
            
        	Connection con = DBUtil.getConnection();
            

            String query = "INSERT INTO users (first_name,last_name,dob,gender,blood_group,age,email,mobile,emergency_contact_name,emergency_contact_tel,street_address,city,state,zipcode,password) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, firstName);
            pst.setString(2, lastName);
            pst.setDate(3, Date.valueOf(dob));
            pst.setString(4, gender);
            pst.setString(5, bloodGroup);
            pst.setInt(6, Integer.parseInt(age));
            pst.setString(7, email);
            pst.setString(8, mobile);
            pst.setString(9, emergency1);
            pst.setString(10, emergency2);
            pst.setString(11, street);
            pst.setString(12, city);
            pst.setString(13, state);
            pst.setString(14, zipcode);
            pst.setString(15, password);

            
            int i = pst.executeUpdate();
            

            if(i>0){
            	out.println("<script>alert('Account Created Successfully'); window.location='login.html' </script>"); 
            	
            } else {
                out.println("Error: Signup failed");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
