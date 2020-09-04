package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/add")
@SuppressWarnings("serial")
public class MemberAddServlet extends HttpServlet {
	@Override
	protected void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {
		response.setContentType( "text/html; charset=utf-8" );
		PrintWriter out = response.getWriter();	
		out.println( "<html><head><title>회원 등록</title></head><body>" );
		out.println( "<h1>회원 등록</h1>" );
		out.println( "<form action='add' method='post'>" );
		out.println( "이름: <input type='text' name='name'><br>" );
		out.println( "이메일: <input type='text' name='email'><br>" );
		out.println( "암호: <input type='password' name='password'><br>" );
		out.println( "<input type='submit' value='추가'>" );
		out.println( "<input type='reset' value='취소'>" );
		out.println( "</form>" );
		out.println( "</body></html>" );
	}
	
	@Override
	protected void doPost( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {
//		request.setCharacterEncoding( "UTF-8" );
		Connection connection = null; 
		PreparedStatement statement = null;
		try {
			connection = ( Connection ) this.getServletContext().getAttribute( "connection" );
				
			// 3. 커넥션 객체로부터 SQL을 던질 객체를 준비
 			statement = connection.prepareStatement( "Insert INTO MEMBERS( EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE )"
 					+ " VALUES( ?, ?, ?, NOW(), NOW() )" );
 			statement.setString( 1, request.getParameter( "email" ) );
 			statement.setString( 2, request.getParameter( "password" ) );
 			statement.setString( 3, request.getParameter( "name" ) );
			
 			// 4. SQL을 던질 객체로 서버에 질의
 			statement.executeUpdate();
 			
 			response.sendRedirect( "list" ); // redirect
 			
 			// 5. 서버에서 가지고 온 데이터로 HTML을 작성하여 웹 브라우저로 출력 
 			response.setContentType( "text/html; charset=utf-8" );
 			PrintWriter out = response.getWriter();
 			out.println( "<html><head><title>회원등록결과</title>" );
 			out.println( "<meta http-equiv='Refresh' content='100; url=list'>" ); // refresh
 			out.println( "</head><body>" );
 			out.println( "<p>등록 성공입니다!!</p>" );
 			out.println( "</body></html>" );

		} catch ( Exception e ) {
			throw new ServletException( e );
		} finally {
 			try { statement.close(); } catch( Exception e ) {}
		}
	}
	
}
