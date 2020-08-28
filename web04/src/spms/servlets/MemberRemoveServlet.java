package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( "/member/remove" )
@SuppressWarnings("serial")
public class MemberRemoveServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext servletContext = this.getServletContext();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			Class.forName( servletContext.getInitParameter( "driver" ) );
			connection = DriverManager.getConnection( 
					servletContext.getInitParameter( "url" ) 
					+ TimeZoneValue.getTimeZoneValue(), 
					servletContext.getInitParameter( "username" ),
					servletContext.getInitParameter( "password" ) );
			preparedStatement = connection.prepareStatement(
					"DELETE FROM MEMBERS WHERE MNO = " + request.getParameter( "no" ) );
			preparedStatement.executeUpdate();
			
			response.sendRedirect( "list" );
			
		} catch ( Exception e) {
			throw new ServletException( e );
		} finally {
			try { if( preparedStatement != null ) preparedStatement.close(); } catch( Exception e ) {}
			try { if( connection != null ) connection.close(); } catch( Exception e ) {}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

}
