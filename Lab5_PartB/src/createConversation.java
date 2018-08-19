
import java.sql.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class createConversation
 */
@WebServlet("/createConversation")
public class createConversation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public createConversation() {
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
		response.setContentType("text/html");
		checksession a=new checksession();
		a.check(request,response);
		String RequestID = request.getParameter("id");
		PrintWriter out=response.getWriter();
		if(RequestID.equals("")) {
			response.sendRedirect("Home");
		}
		else {
			try(Connection conn = DriverManager.getConnection(
		    		"jdbc:postgresql://localhost:5030/postgres", "rohit", "");)
			{
				try(PreparedStatement p=conn.prepareStatement("select * from users where uid=?");){
					p.setString(1,RequestID);
					ResultSet rst=p.executeQuery();
					if(rst.next()) {
						HttpSession session = request.getSession(false);
						String ID=(String) session.getAttribute("id");
						try(PreparedStatement p1=conn.prepareStatement("select * from conversations where uid1=? and uid2=?");
								PreparedStatement p2=conn.prepareStatement("insert into conversations values(?,?)");){
							int compare=RequestID.compareTo(ID);
							if(compare==0)
							{
								out.println("<Body> Haha! Why do you want to have conversation with yourself!!");
							}
							else 
							{
								String uid1;
								String uid2;
								if(compare<0) {
									uid1=RequestID;
									uid2=ID;
								}
								else {
									uid2=RequestID;
									uid1=ID;
								}
								p1.setString(1,uid1);
								p1.setString(2,uid2);
								ResultSet rst1=p1.executeQuery();
								if(rst1.next()) {
									out.println("<Body> Conversation already exists");
								}
								else {
								p2.setString(1,uid1);
								p2.setString(2,uid2);
								p2.executeUpdate();	
								out.println("<Body>Successfully added!");
								}
							
						}
						
					}
						
				}
					else
					{
						out.println("<Body> No such user");
						
					}
					out.println("<br> <a href=Home>Home</a> </Body>");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
