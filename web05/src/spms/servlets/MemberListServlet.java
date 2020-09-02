package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import spms.vo.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( "/member/list" )
@SuppressWarnings("serial")
public class MemberListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding( "UTF-8" );
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			// web.xml setting ÇÊ¿ä
			ServletContext servletContext = this.getServletContext();
			Class.forName( servletContext.getInitParameter( "driver" ) );
			connection = DriverManager.getConnection( 
					servletContext.getInitParameter( "url" ) 
					+ TimeZoneValue.getTimeZoneValue(),
					servletContext.getInitParameter( "username" ),
					servletContext.getInitParameter( "password" ) );
			statement = connection.createStatement();
			resultSet = statement.executeQuery( 
					"SELECT MNO, MNAME, EMAIL, CRE_DATE FROM MEMBERS" );
			
			response.setContentType( "text/html; charSet=UTF-8" );
			ArrayList<Member> members = new ArrayList<>();
			while( resultSet.next() ) {
				members.add( new Member()
						.setNo( resultSet.getInt( "MNO" ) )
						.setName( resultSet.getString( "MNAME" ) )
						.setEmail( resultSet.getString( "EMAIL" ) )
						.setCreatedDate( resultSet.getDate( "CRE_DATE" ) ) );
			}
			request.setAttribute( "members", members );
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(
					"/member/MemberList.jsp" );
			requestDispatcher.include( request, response );
		} catch ( Exception e ) {
			throw new ServletException( e );
		} finally {
			try { if( resultSet != null ) { resultSet.close(); } } catch( Exception e ) {}
			try { if( statement != null ) { statement.close(); } } catch( Exception e ) {}
			try { if( connection != null ) { connection.close(); } } catch( Exception e ) {}
		}	
	}

}
