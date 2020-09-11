package spms.listeners;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import spms.dao.MemberDao;
import spms.servlets.TimeZoneValue;

public class ContextLoaderListener implements ServletContextListener {
	Connection connection = null;
	@Override
	public void contextInitialized( ServletContextEvent event ) {
		try {
			ServletContext sc = event.getServletContext();
			Class.forName( sc.getInitParameter( "driver" ) );
			connection = DriverManager.getConnection(
					sc.getInitParameter( "url" )
					+ TimeZoneValue.getTimeZoneValue(),
					sc.getInitParameter( "username" ),
					sc.getInitParameter( "password" ) 
			);
			MemberDao memberDao = new MemberDao();
			memberDao.setConnection( connection );
			
			sc.setAttribute( "memberDao", memberDao );
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed( ServletContextEvent event ) {
		try { if( connection != null ) connection.close(); } catch( Exception e ) {}
	}

}
