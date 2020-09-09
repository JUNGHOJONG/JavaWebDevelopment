package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import spms.dao.MemberDao;
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
		try {
			ServletContext sc = this.getServletContext();
			Connection connection = ( Connection ) sc.getAttribute( "connection" );
			MemberDao memberDao = new MemberDao();
			memberDao.setConnection( connection );
			
			request.setAttribute( "members", memberDao.selectList() );
			response.setContentType( "text/html; charset=UTF-8" );
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(
					"/member/MemberList.jsp" );
			requestDispatcher.include( request, response );
		} catch ( Exception e ) {
			e.printStackTrace();
			request.setAttribute( "error", e );
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(
					"/Error.jsp" );
			requestDispatcher.forward( request, response );
		}	
	}

}
