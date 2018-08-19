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
	public void check(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		
		HttpSession session= request.getSession(false);
		if(session==null) {
			response.sendRedirect("Login");
		}
		// TODO Auto-generated method stub
		
	}
}
	