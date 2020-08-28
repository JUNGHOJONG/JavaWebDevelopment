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
@WebServlet( "/member/sort" )
@SuppressWarnings("serial")
public class MemberSortServlet extends HttpServlet{
	private String[] query = {
			"ALTER TABLE MEMBERS AUTO_INCREMENT=1",
			"SET @COUNT = 0",
			"UPDATE MEMBERS SET MNO = @COUNT:=@COUNT+1"
	};
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ServletContext servletContext = this.getServletContext();
		try {
			Class.forName( servletContext.getInitParameter( "driver" ) );
			connection = DriverManager.getConnection( 
					servletContext.getInitParameter( "url" )
					+ TimeZoneValue.getTimeZoneValue(),
					servletContext.getInitParameter( "username" ),
					servletContext.getInitParameter( "password" ) );
			for( String s : query ) { // 질의문 하나씩 날릴 수 있다.
				preparedStatement = connection.prepareStatement( s );
				preparedStatement.executeUpdate();
				preparedStatement.clearParameters();
			}
			response.sendRedirect( "list" );
			
		} catch ( Exception e) {
			throw new ServletException( e );
		} finally {
			try { if( preparedStatement != null ) preparedStatement.close(); } catch( Exception e ) {}
			try { if( connection != null ) connection.close(); } catch( Exception e ) {}
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {}

}
