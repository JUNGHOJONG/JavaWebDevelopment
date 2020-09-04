package spms.servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AppInitServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println( "AppInitServlet ¡ÿ∫Ò..." );
		super.init(config);
		try {
			ServletContext sc = this.getServletContext();
			Class.forName( sc.getInitParameter( "driver" ) );
			Connection connection = DriverManager.getConnection( 
					sc.getInitParameter( "url" ) 
					+ TimeZoneValue.getTimeZoneValue(), 
					sc.getInitParameter( "username" ), 
					sc.getInitParameter( "password" ) );
			sc.setAttribute( "connection", connection );
		} catch ( Exception e ) {
			throw new ServletException( e );
		}
	}
	
	@Override
	public void destroy() {
		System.out.println( "AppInitServlet º“∏Í..." );
		super.destroy();
		Connection connection = (Connection) this.getServletContext().
				getAttribute( "connection" );
		try {
			if( connection != null && !connection.isClosed() ) {
				connection.close();
			}
		} catch ( Exception e ) {}
	}

}
