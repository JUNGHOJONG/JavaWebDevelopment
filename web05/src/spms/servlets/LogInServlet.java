package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet( "/auth/login" )
public class LogInServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher( "/auth/LogInForm.jsp" ); // 작성 필요
		rd.forward( request, response );
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Connection connection = ( Connection ) this.getServletContext().getAttribute( "connection" );
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement( 
					"SELECT EMAIL, PWD FROM MEMBERS WHERE EMAIL=? AND PWD=?" );
			preparedStatement.setString( 1, request.getParameter( "email" ) );
			preparedStatement.setString( 2, request.getParameter( "password" ) );
			resultSet = preparedStatement.executeQuery();
			if( resultSet.next() ) {
				Member member = new Member().setEmail( 
						resultSet.getString( "EMAIL" ) ).
						setPassword( resultSet.getString( "PWD" ) );
				HttpSession session = request.getSession();
				session.setAttribute( "member", member );
				
				response.sendRedirect( "../member/list" );
			}else {
				RequestDispatcher rd = request.getRequestDispatcher( "/auth/LogInFail.jsp" ); // 작성 필요
				rd.forward( request, response );
			}
		} catch ( Exception e ) {
			throw new ServletException( e );
		} finally {
			try { if( resultSet != null ) resultSet.close(); } catch( Exception e ) {}
			try { if( preparedStatement != null ) preparedStatement.close(); } catch( Exception e ) {}			
		}
	}

}
