package spms.servlets;

import java.io.IOException;

import java.sql.Connection;
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

@WebServlet( "/member/update" )
@SuppressWarnings("serial")
public class MemberUpdateServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			Connection connection = ( Connection ) sc.getAttribute( "connection" );
			MemberDao memberDao = new MemberDao();
			memberDao.setConnection( connection );
			
			Member member = memberDao.selectOne( 
					Integer.parseInt( request.getParameter( "no" ) ) );
			
			// view
			response.setContentType( "text/html; charset=utf-8" );
			RequestDispatcher requestDispatcher = request.getRequestDispatcher( 
					"/member/MemberUpdate" );
			request.setAttribute( "member", member );
			requestDispatcher.include( request, response );

		} catch ( Exception e ) {
//			throw new ServletException( e );
			e.printStackTrace();
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(
					"/Error.jsp" );
			request.setAttribute( "error", e );
			requestDispatcher.forward( request, response );
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding( "UTF-8" );
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
		try { 
			ServletContext sc = this.getServletContext();
			Connection connection = ( Connection ) sc.getAttribute( "connection" );
//			connection = ( Connection ) this.getServletContext().getAttribute( "connection" );			
//			preparedStatement = connection.prepareStatement( 
//					"UPDATE MEMBERS SET MNAME = ?, EMAIL = ?, MOD_DATE = NOW()"
//					+ "WHERE MNO = ?" );
//			preparedStatement.setString( 1, request.getParameter( "name" ) );
//			preparedStatement.setString( 2, request.getParameter( "email" ) );
//			preparedStatement.setString( 3, request.getParameter( "no" ) );
//			preparedStatement.executeUpdate();
			MemberDao memberDao = new MemberDao();
			memberDao.setConnection( connection );
			Member member = new Member();
			member.setName( request.getParameter( "name" ) )
			.setEmail( request.getParameter( "email" ) )
			.setNo( Integer.parseInt( request.getParameter( "no" ) ) );
			memberDao.update( member );
			
			response.sendRedirect( "list" );
		
		}catch( Exception e ) {
			throw new ServletException( e );
		}finally {
//			try { if( preparedStatement != null ) preparedStatement.close(); } catch( Exception e ) {}
//			try { if( connection != null ) connection.close(); } catch( Exception e ) {}
		}
		
	}
	
}
