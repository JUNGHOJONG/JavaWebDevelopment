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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( "/member/list" )
@SuppressWarnings("serial")
public class MemberListServlet extends HttpServlet{
	
	/*
	   1. ����� JDBC ����̹��� ����϶�.
	   2. ����̹��� ����Ͽ� MySQL������ �����϶�.
	   3. Ŀ�ؼ� ��ü�κ��� SQL�� ���� ��ü�� �غ��϶�.
	   4. SQL�� ������ ��ü�� ����Ͽ� ������ �����϶�.
	   5. ������ ������ �����͸� ����Ͽ� HTML�� ���� �� �������� ����϶�. 
	*/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			
			ServletContext servletContext = this.getServletContext();
			
			// 1. ����� JDBC ����̹��� ����϶�.
			Class.forName( servletContext.getInitParameter( "driver" ) );
			
			// 2. ����̹��� ����Ͽ� MySQL������ �����϶�.
			connection = DriverManager.getConnection( 
					servletContext.getInitParameter( "url" ) 
					+ TimeZoneValue.getTimeZoneValue(),
					servletContext.getInitParameter( "username" ),
					servletContext.getInitParameter( "password" ) );
			
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
			out.println( "<p><a href='sort'>����</a></p>" );
			while( resultSet.next() ) {
				out.println( resultSet.getInt( "MNO" ) + ". " 
			+ "<a href='update?no=" + resultSet.getInt( "MNO" ) + "'>" 
			+ resultSet.getString( "MNAME" ) + "</a>" + ", "
			+ resultSet.getString( "EMAIL" ) + ", " 
			+ resultSet.getDate( "CRE_DATE" ) 
			+ "<a href='remove?no=" + resultSet.getInt( "MNO" ) + "'>[����]</a><br>" );
			}
			out.println( "</body></html>" );
		}catch( Exception e ) {
			throw new ServletException( e );
		}finally {
			try { if( resultSet != null ) resultSet.close(); } catch( Exception e ) {}
			try { if( statement != null ) statement.close(); } catch( Exception e ) {}
			try { if( connection != null ) connection.close(); } catch( Exception e ) {}
		}
	}

}
