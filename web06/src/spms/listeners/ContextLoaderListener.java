package spms.listeners;

import javax.naming.InitialContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import spms.controls.MemberAddController;
import spms.controls.MemberListController;
import spms.controls.MemberLogInController;
import spms.controls.MemberLogOutController;
import spms.controls.MemberRemoveController;
import spms.controls.MemberSortController;
import spms.controls.MemberUpdateController;
import spms.dao.MysqlMemberDao;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	@Override
	public void contextInitialized( ServletContextEvent event ) {
		try {
			ServletContext sc = event.getServletContext();
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource)initialContext.lookup("java:comp/env/jdbc/madang");

			MysqlMemberDao memberDao = new MysqlMemberDao();
			memberDao.setDataSource(ds);
			sc.setAttribute("/member/list.do", new MemberListController().setMemberDao(memberDao));
			sc.setAttribute("/member/add.do", new MemberAddController().setMemberDao(memberDao));
			sc.setAttribute("/auth/login.do", new MemberLogInController().setMemberDao(memberDao));
			sc.setAttribute("/auth/logout.do", new MemberLogOutController());
			sc.setAttribute("/member/remove.do", new MemberRemoveController().setMemberDao(memberDao));
			sc.setAttribute("/member/update.do", new MemberUpdateController().setMemberDao(memberDao));
			sc.setAttribute("/member/sort.do", new MemberSortController().setMemberDao(memberDao));

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed( ServletContextEvent event ) {}

}
