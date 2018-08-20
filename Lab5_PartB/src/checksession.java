import java.sql.*;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class checksession {

	public checksession() {
        super();
        // TODO Auto-generated constructor stub
    }
	public int check(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		
		HttpSession session= request.getSession(false);
		if(session==null) {
			response.sendRedirect("Login");
			return 0;
		}
		return 1;
		// TODO Auto-generated method stub
		
	}
public int check(HttpServletRequest request,HttpServletResponse response, int thread) throws ServletException,IOException, SQLException {
		
		HttpSession session= request.getSession(false);
		String ID=(String) session.getAttribute("id");
		try(Connection conn = DriverManager.getConnection(
	    		"jdbc:postgresql://localhost:5590/WhatASap", "sharvik", "");)
		{
		try(PreparedStatement p=conn.prepareStatement("select uid1, uid2 from conversations "
				+ "where thread_id=? ");){
			p.setInt(1,thread);
			ResultSet rst=p.executeQuery();
			if(rst.next()) 
			{
				String uid1=rst.getString(1);
				String uid2=rst.getString(2);
				if(!(ID.equals(uid2)||ID.equals(uid1)))
				{	response.sendRedirect("Home");
					return 0; 
				}
				return 1;
			}	
			else
			{
				response.sendRedirect("Home");
				return 0;

			}
		}
		}
		// TODO Auto-generated method stub
		
	}
}
