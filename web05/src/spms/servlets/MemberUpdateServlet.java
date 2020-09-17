package spms.servlets;

import java.io.IOException;

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
			MemberDao memberDao = ( MemberDao ) sc.getAttribute( "memberDao" );
			Member member = memberDao.selectOne( 
					Integer.parseInt( request.getParameter( "no" ) ) );
			
			response.setContentType( "text/html; charset=utf-8" );
			RequestDispatcher requestDispatcher = request.getRequestDispatcher( 
					"/member/MemberUpdate.jsp" );
			request.setAttribute( "member", member );
			requestDispatcher.include( request, response );

		} catch ( Exception e ) {
			e.printStackTrace();
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(
					"/Error.jsp" );
			request.setAttribute( "error", e );
			requestDispatcher.forward( request, response );
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try { 
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = ( MemberDao ) sc.getAttribute( "memberDao" );

			Member member = new Member();
			member.setName( request.getParameter( "name" ) )
			.setEmail( request.getParameter( "email" ) )
			.setNo( Integer.parseInt( request.getParameter( "no" ) ) );
			memberDao.update( member );
			
			response.sendRedirect( "list" );
		
		}catch( Exception e ) {
			e.printStackTrace();
			RequestDispatcher ruquestDispatcher = request.getRequestDispatcher(
					"/Error.jsp" );
			request.setAttribute( "error", e );
			ruquestDispatcher.forward( request, response );
		}
	}
	
}
