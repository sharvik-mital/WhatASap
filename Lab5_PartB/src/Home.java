
import java.sql.*;
import java.io.IOException;
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		checksession a=new checksession();
		int sess=a.check(request,response);
		if(sess==1) {
		createnewConversationform(request,response);
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<br>");
		try(Connection conn = DriverManager.getConnection(
	    		"jdbc:postgresql://localhost:5590/WhatASap", "sharvik", "");)
		{
			try(PreparedStatement p=conn.prepareStatement("with threadid(thread_id,name) as (" + 
					"select thread_id,name " + 
					"from conversations as c join users on users.uid=c.uid2 " + 
					"where uid1=? " + 
					"union\n" + 
					"select thread_id,name \n" + 
					"from conversations as c join users on users.uid=c.uid1\n" + 
					"where uid2=?),\n" + 
					"lastmessage(thread_id,timestamp) as(\n" + 
					"select posts.thread_id,max(timestamp)\n" + 
					"from posts natural join threadid\n" + 
					"group by posts.thread_id),\n" + 
					"lastmessage1(name,text,timestamp) as(\n" + 
					"select name,text,timestamp\n" + 
					"from lastmessage natural join posts natural join threadid\n" + 
					")\n" + 
					"select threadid.name,text,timestamp,thread_id as Conversation_Link\n" + 
					"from lastmessage1 natural join threadid \n" + 
					"order by timestamp desc;");
					PreparedStatement p1=conn.prepareStatement("with threadid(thread_id,name) as (\n" + 
							"select thread_id,name\n" + 
							"from conversations as c join users on users.uid=c.uid2 \n" + 
							"where uid1=?\n" + 
							"union\n" + 
							"select thread_id,name \n" + 
							"from conversations as c join users on users.uid=c.uid1\n" + 
							"where uid2=?),\n" + 
							"lastmessage(thread_id,timestamp) as(\n" + 
							"select posts.thread_id,max(timestamp)\n" + 
							"from posts natural join threadid\n" + 
							"group by posts.thread_id)\n" + 
							"select threadid.name,text,timestamp,thread_id as Conversation_Link\n" + 
							"from posts natural right outer join threadid\n" + 
							"where not exists(select * from lastmessage as l\n" + 
							"where l.thread_id=threadid.thread_id); ");){
				HttpSession session=request.getSession(false);
				String ID=(String) session.getAttribute("id");
				p.setString(1,ID);
				p.setString(2,ID);
				p1.setString(1,ID);
				p1.setString(2,ID);
				ResultSet rst=p.executeQuery();
				ResultSet rst1=p1.executeQuery();
				toHTML(rst,response,rst1);
				out.println("<form action=\"Logout\" method=\"GET\">"+
		                "<input type=\"submit\" value = \"Logout\">" +
		            "</form>");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void createnewConversationform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<HEAD><TITLE> Home </TITLE></HED>");
		out.println("<form action=\"createConversation\" method=\"POST\">"+
                "Enter id of the user: <input type=\"text\" name = \"id\">"+
                "<input type=\"submit\" value = \"Start new Conversation\">" +
            "</form>");
	}
	public static void toHTML(ResultSet rs1,HttpServletResponse response,ResultSet rs2) throws Exception
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
	     for(int i = 1; i < result.getColumnCount(); i++) {
	           out.println("<td>" + rs1.getString(i)+"</td>");
	             
		       }
	     out.println("<td>");
	     out.println("<a href=ConversationDetails?thread_id="+rs1.getString(result.getColumnCount())+">Conversation Details </a>");
	     out.println("</tr>");
	    
        }
        while(rs2.next())
        {
        	out.println("\t");
     	   out.println("<tr>");
	     for(int i = 1; i < result.getColumnCount(); i++) {
	           out.println("<td>" + rs2.getString(i)+"</td>");
	             
		       }
	     out.println("<td>");
	     out.println("<a href=ConversationDetails?thread_id="+rs2.getString(result.getColumnCount())+">Conversation Details </a>");
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
