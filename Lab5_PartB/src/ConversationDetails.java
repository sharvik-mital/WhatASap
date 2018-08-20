
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

	config cfg=new config();
	private static final long serialVersionUID = 1L;
	private final String url = cfg.getProperty("url");
	private final String user = cfg.getProperty("user");
	private final String passwd = cfg.getProperty("passwd");   
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
		response.setContentType("text/html");
		checksession a=new checksession();
		int sess=a.check(request,response);
		if(sess==1) {
		String thread=(String)request.getParameter("thread_id");
		int thread_id=Integer.parseInt(thread);
		try {
			a.check(request,response,thread_id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PrintWriter out=response.getWriter();
		try(Connection conn = DriverManager.getConnection(url,user,passwd);)
		{try(PreparedStatement p=conn.prepareStatement("select name, text from users"
				+ " natural join posts "
				+ "where thread_id=? "
				+ "order by timestamp")){
			p.setInt(1,thread_id);
			ResultSet rst=p.executeQuery();
			toHTML(rst,response);		
			out.println("<form action=\"NewMessage\" method=\"POST\">"+
            "<input type=\"text\" name = \"text\">"+
            "<input type=\"hidden\" name = \"thread_id\" value="+thread_id+">"+	
            "<input type=\"submit\" value = \"Submit\">" +
		    "</form>");	

			out.println(" <form action=\"Home\" method=\"get\"> \n" + 
					"<input type=\"submit\" value = \"Home\"> \n" + 
					"       </form> ");
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
