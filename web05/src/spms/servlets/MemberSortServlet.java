package spms.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
@WebServlet( "/member/sort" )
@SuppressWarnings("serial")
public class MemberSortServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = ( MemberDao ) sc.getAttribute( "memberDao" );
			memberDao.sort();
			request.setAttribute("viewUrl", "redirect:./list.do");
		} catch ( Exception e) {
			throw new ServletException(e);
		}
		
	}

}
