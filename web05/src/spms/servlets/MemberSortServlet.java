package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
@WebServlet( "/member/sort" )
@SuppressWarnings("serial")
public class MemberSortServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			Connection connection = ( Connection ) sc.getAttribute( "connection" );
			
			MemberDao memberDao = new MemberDao();
			memberDao.setConnection( connection );
			memberDao.sort();
			
			response.sendRedirect( "list" );
			
		} catch ( Exception e) {
//			throw new ServletException( e );
			e.printStackTrace();
			request.setAttribute( "error", e );
			RequestDispatcher rd = request.getRequestDispatcher( "Error.jsp" );
			rd.forward( request, response );
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {}

}
