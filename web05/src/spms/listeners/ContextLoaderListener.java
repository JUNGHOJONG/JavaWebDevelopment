package spms.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp2.BasicDataSource;

import spms.dao.MemberDao;
import spms.servlets.TimeZoneValue;

public class ContextLoaderListener implements ServletContextListener {
	BasicDataSource ds;
	@Override
	public void contextInitialized( ServletContextEvent event ) {
		try {
			ServletContext sc = event.getServletContext();
			ds = new BasicDataSource();
			ds.setDriverClassName(sc.getInitParameter("driver"));
			ds.setUrl(sc.getInitParameter("url") + TimeZoneValue.getTimeZoneValue());
			ds.setUsername(sc.getInitParameter("username"));
			ds.setPassword(sc.getInitParameter("password"));

			MemberDao memberDao = new MemberDao();
			memberDao.setDataSource(ds);
			sc.setAttribute( "memberDao", memberDao );
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed( ServletContextEvent event ) {
		try { if (ds != null) ds.close(); } catch (Exception e) {}
	}

}
