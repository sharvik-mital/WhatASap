import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class NewMessage
 */
@WebServlet("/NewMessage")
public class NewMessage extends HttpServlet {

	config cfg=new config();
	private static final long serialVersionUID = 1L;
	private final String url = cfg.getProperty("url");
	private final String user = cfg.getProperty("user");
	private final String passwd = cfg.getProperty("passwd");    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		checksession a=new checksession();
		int sess=a.check(request,response);
		if(sess==1) {
		HttpSession session=request.getSession(false);
		String ID = (String) session.getAttribute("id");

		String thread=(String)request.getParameter("thread_id");
		int thread_id=Integer.parseInt(thread);
		int return_val=0;
		try {
			return_val=a.check(request, response,thread_id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String text=(String) request.getParameter("text");
		PrintWriter out=response.getWriter();
		out.println(ID);
		if(text.isEmpty()&& return_val==1) {
//			out.println("Empty String!");
			response.sendRedirect("ConversationDetails?thread_id="+thread);
		}
		else if(return_val==1) {
		try(Connection conn = DriverManager.getConnection(url,user,passwd);)
		{try(PreparedStatement p=conn.prepareStatement("insert into posts(thread_id,uid, timestamp,text)"
				+ " values (?,?,now(),?)")){
			p.setInt(1,thread_id);
			p.setString(2, ID);
			p.setString(3, text);
//			ResultSet rst=
			p.executeUpdate();
			// out.println(text);
//			while (rst.next())
//			{
//				out.println(rst.getString(2));
//			}
//			toHTML(rst,response);		
//			out.println("<form action=\"NewMessage\" method=\"POST\">"+
//            "<input type=\"text\" name = \"text\">"+
//            "<input type=\"hidden\" name = \"thread_id\" value="+thread_id+">"+	
//            "<input type=\"submit\" value = \"Submit\">" +
//		    "</form>");	
			response.sendRedirect("ConversationDetails?thread_id="+thread);
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
	}
}
