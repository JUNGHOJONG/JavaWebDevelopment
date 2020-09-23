package spms.servlets;

import java.io.IOException;

import spms.dao.MemberDao;

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
		try {
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = ( MemberDao ) sc.getAttribute( "memberDao" );
			request.setAttribute( "members", memberDao.selectList() );
			request.setAttribute("viewUrl", "/member/MemberList.jsp");
		} catch ( Exception e ) {
			throw new ServletException(e);
		}	
	}

}
