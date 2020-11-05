package spms.servlets;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet( "/member/list" )
public class MemberListServlet extends GenericServlet implements Servlet{
	private static final long serialVersionUID = 1L;

	@Override
	public void service( ServletRequest request, ServletResponse response ) throws ServletException, IOException{
		/*
		   1. ����� JDBC ����̹��� ����϶�.
		   2. ����̹��� ����Ͽ� MySQL������ �����϶�.
		   3. Ŀ�ؼ� ��ü�κ��� SQL�� ���� ��ü�� �غ��϶�.
		   4. SQL�� ������ ��ü�� ����Ͽ� ������ �����϶�.
		   5. ������ ������ �����͸� ����Ͽ� HTML�� ���� �� �������� ����϶�. 
		*/
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			
			// 1. ����� JDBC ����̹��� ����϶�.
			DriverManager.registerDriver( new com.mysql.jdbc.Driver() );
			
			// 2. ����̹��� ����Ͽ� MySQL������ �����϶�.
			String timeZoneValue = "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			connection = DriverManager.getConnection( 
					"jdbc:mysql://localhost/madang?" + timeZoneValue,
					"madang", "madang" );
			
			// 3. Ŀ�ؼ� ��ü�κ��� SQL�� ���� ��ü�� �غ��϶�.
			statement = connection.createStatement();
			
			// 4. SQL�� ������ ��ü�� ����Ͽ� ������ �����϶�.
			resultSet = statement.executeQuery( // resultSet ��ü -> �������� �����͸� ������ ���� ��ü(O), �������� ������ �� �����͸� ������ �ִ� ��ü(X)
			"SELECT MNO,MNAME,EMAIL,CRE_DATE" +
			" FROM MEMBERS" +
			" ORDER BY MNO ASC" );
			
			// 5. ������ ������ �����͸� ����Ͽ� HTML�� ���� �� �������� ����϶�.
			response.setContentType( "text/html; charset=UTF-8" );
			PrintWriter out = response.getWriter();
			out.println( "<html><head><title>ȸ�����</title></head>" );
			out.println( "<body><h1>ȸ�����</h1>" );
			out.println( "<p><a href='add'>�ű� ȸ��</a></p>" );
			while( resultSet.next() ) {
				out.println( resultSet.getInt( "MNO" ) + ". " 
			+ "<a href='update?no=" + resultSet.getInt( "MNO" ) + "'>" 
			+ resultSet.getString( "MNAME" ) + "</a>" + ", "
			+ resultSet.getString( "EMAIL" ) + ", " 
			+ resultSet.getDate( "CRE_DATE" ) + "<br>" );
			}
			out.println( "</body></html>" );
		}catch( SQLException e ) {
			throw new ServletException( e );
		}finally {
			try { if( resultSet != null ) resultSet.close(); } catch( Exception e ) {}
			try { if( statement != null ) statement.close(); } catch( Exception e ) {}
			try { if( connection != null ) connection.close(); } catch( Exception e ) {}
		}
	}
}
