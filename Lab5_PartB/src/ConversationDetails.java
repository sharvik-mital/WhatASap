
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ConversationDetails
 */
@WebServlet("/ConversationDetails")
public class ConversationDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConversationDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		checksession a=new checksession();
		a.check(request,response);
		String thread_id=(String)request.getParameter("thread_id");
		PrintWriter out=response.getWriter();
		out.println(thread_id);
		try(Connection conn = DriverManager.getConnection(
	    		"jdbc:postgresql://localhost:5590/WhatASap", "sharvik", "");)
		{try(PreparedStatement p=conn.prepareStatement("select name,text\n" + 
				"from users natural join posts\n" + 
				"where thread_id=?\n" + 
				"order by timestamp;");){
			p.setString(1,thread_id);
			ResultSet rst=p.executeQuery();
//			out.println("<form action=\"NewMessage\" method=\"POST\">"+
//	                 "<input type=\"text\" name = \"text\">"+
//	                 "<input type=\"hidden\" name = \"thread_id\" value="+thread_id+">"+	
//	                 "<input type=\"submit\" value = \"Submit\">" +
//	             "</form>");
			while (rst.next())
			{
				out.println(rst.getString(2));
			}
//			toHTML(rst,response);			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public static void toHTML(ResultSet rs1,HttpServletResponse response) throws Exception
	{
		try
		{
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		ResultSetMetaData result = rs1.getMetaData();
        out.println("<table>");
        out.println("\t");
        out.println("<tr>");
        for(int i =1; i<= result.getColumnCount(); i++)
        {
     	  out.println("<th>"+ result.getColumnName(i)+"</th>");
        }
        out.println("</tr>");
        
        
        while(rs1.next())
        {
        	out.println("\t");
     	   out.println("<tr>");
	     for(int i = 1; i <= result.getColumnCount(); i++) {
	           out.println("<td>" + rs1.getString(i)+"</td>");
	             
		       }
	     out.println("<td>");
	     out.println("</tr>");
	    
        }
        
        out.println("</table>");
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
}
