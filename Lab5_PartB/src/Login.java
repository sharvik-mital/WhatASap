
import java.sql.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response); 
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session1=request.getSession(false);
		if(session1!=null)
			
		{
			response.sendRedirect("Home");
		}
		else 
		{		
		printform(request,response);
		
		String ID = request.getParameter("id");
		String password = request.getParameter("password");
		if(ID.equals("")||(password.equals(""))) {
			PrintWriter out = response.getWriter();
			out.close();
		}
	      
		try (
			    Connection conn = DriverManager.getConnection(
			    		"jdbc:postgresql://localhost:5590/WhatASap", "sharvik", "");
			)
			{
			PrintWriter out = response.getWriter();
				try(PreparedStatement p1= conn.prepareStatement("select count(*) from password where id=? and password=?");) 
				{
				  p1.setString(1,ID);
				  p1.setString(2,password);
				  ResultSet rst=p1.executeQuery();
				  if(rst.next()) {
					  int present=rst.getInt(1);
					  if(present==0) {
						  out.println("Wrong credentials");
					  }
					  else {
						  HttpSession session= request.getSession(true);
						  session.setAttribute("id",ID);
//						  out.println("Right Credentials");
						  response.sendRedirect("Home");
						  out.close();
					  }
				  }
				} catch ( SQLException sqle) {
					System.out.println("Could not insert tuple. " + sqle);
				}
				
			}
			catch (Exception sqle)
			{
			System.out.println("Exception : " + sqle);
			}
		}
		
	}
	
	protected void printform(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
	      PrintWriter out1 = null;
		try {
			out1 = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      out1.println("<HEAD><TITLE> Query Result</TITLE></HEAD>");
	      out1.println("<BODY>");
	      out1.println("<form action=\"Login\" method=\"POST\">"+
	                 "Enter your id: <input type=\"text\" name = \"id\">"+
	                 "Enter your password: <input type=\"text\" name = \"password\">"+
	                 "<input type=\"submit\" value = \"Submit\">" +
	             "</form>");
	}

}
