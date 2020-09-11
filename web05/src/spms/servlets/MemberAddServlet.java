package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import spms.vo.Member;

@WebServlet("/member/add")
@SuppressWarnings("serial")
public class MemberAddServlet extends HttpServlet {
	@Override
	protected void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {
		response.setContentType( "text/html; charset=UTF-8" );
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(
				"/member/MemberAdd.jsp" );
		requestDispatcher.include( request, response );
	}
	
	@Override
	protected void doPost( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = ( MemberDao ) sc.getAttribute( "memberDao" );	

			Member member = new Member();
			member.setName( request.getParameter( "name" ) )
			.setEmail( request.getParameter( "email" ) )
			.setPassword( request.getParameter( "password" ) );
			memberDao.insert( member );
 		
 			response.sendRedirect( "list" ); // redirect

		} catch ( Exception e ) {
			e.printStackTrace();
			request.setAttribute( "error", e );
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(
					"/Error.jsp" );
			requestDispatcher.forward( request, response );
		}
	}
	
}
