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
		out.println( "<html><head><title>ȸ�� ���</title></head><body>" );
		out.println( "<h1>ȸ�� ���</h1>" );
		out.println( "<form action='add' method='post'>" );
		out.println( "�̸�: <input type='text' name='name'><br>" );
		out.println( "�̸���: <input type='text' name='email'><br>" );
		out.println( "��ȣ: <input type='password' name='password'><br>" );
		out.println( "<input type='submit' value='�߰�'>" );
		out.println( "<input type='reset' value='���'>" );
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
				
			// 3. Ŀ�ؼ� ��ü�κ��� SQL�� ���� ��ü�� �غ�
 			statement = connection.prepareStatement( "Insert INTO MEMBERS( EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE )"
 					+ " VALUES( ?, ?, ?, NOW(), NOW() )" );
 			statement.setString( 1, request.getParameter( "email" ) );
 			statement.setString( 2, request.getParameter( "password" ) );
 			statement.setString( 3, request.getParameter( "name" ) );
			
 			// 4. SQL�� ���� ��ü�� ������ ����
 			statement.executeUpdate();
 			
 			response.sendRedirect( "list" ); // redirect
 			
 			// 5. �������� ������ �� �����ͷ� HTML�� �ۼ��Ͽ� �� �������� ��� 
 			response.setContentType( "text/html; charset=utf-8" );
 			PrintWriter out = response.getWriter();
 			out.println( "<html><head><title>ȸ����ϰ��</title>" );
 			out.println( "<meta http-equiv='Refresh' content='100; url=list'>" ); // refresh
 			out.println( "</head><body>" );
 			out.println( "<p>��� �����Դϴ�!!</p>" );
 			out.println( "</body></html>" );

		} catch ( Exception e ) {
			throw new ServletException( e );
		} finally {
 			try { statement.close(); } catch( Exception e ) {}
		}
	}
	
}
