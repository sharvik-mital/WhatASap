
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
	private static final long serialVersionUID = 1L;
       
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
		a.check(request,response);
		HttpSession session=request.getSession(true);
		String ID = (String) session.getAttribute("id");

		String thread=(String)request.getParameter("thread_id");
		int thread_id=Integer.parseInt(thread);
		
		String text=(String) request.getParameter("text");
		PrintWriter out=response.getWriter();
		out.println(ID);
		try(Connection conn = DriverManager.getConnection(
	    		"jdbc:postgresql://localhost:5030/postgres", "rohit", "");)
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
