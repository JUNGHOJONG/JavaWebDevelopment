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
	   1. 사용할 JDBC 드라이버를 등록하라.
	   2. 드라이버를 사용하여 MySQL서버와 연결하라.
	   3. 커넥션 객체로부터 SQL를 던질 객체를 준비하라.
	   4. SQL을 던지는 객체를 사용하여 서버에 질의하라.
	   5. 서버에 가져온 데이터를 사용하여 HTML을 만들어서 웹 브라우저로 출력하라. 
	*/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			
			ServletContext servletContext = this.getServletContext();
			
			// 1. 사용할 JDBC 드라이버를 등록하라.
			Class.forName( servletContext.getInitParameter( "driver" ) );
			
			// 2. 드라이버를 사용하여 MySQL서버와 연결하라.
			connection = DriverManager.getConnection( 
					servletContext.getInitParameter( "url" ) 
					+ TimeZoneValue.getTimeZoneValue(),
					servletContext.getInitParameter( "username" ),
					servletContext.getInitParameter( "password" ) );
			
			// 3. 커넥션 객체로부터 SQL를 던질 객체를 준비하라.
			statement = connection.createStatement();
			
			// 4. SQL을 던지는 객체를 사용하여 서버에 질의하라.
			resultSet = statement.executeQuery( // resultSet 객체 -> 서버에서 데이터를 가지고 오는 객체(O), 서버에서 가지고 온 데이터를 가지고 있는 객체(X)
			"SELECT MNO,MNAME,EMAIL,CRE_DATE" +
			" FROM MEMBERS" +
			" ORDER BY MNO ASC" );
			
			// 5. 서버에 가져온 데이터를 사용하여 HTML을 만들어서 웹 브라우저로 출력하라.
			response.setContentType( "text/html; charset=UTF-8" );
			PrintWriter out = response.getWriter();
			out.println( "<html><head><title>회원목록</title></head>" );
			out.println( "<body><h1>회원목록</h1>" );
			out.println( "<p><a href='add'>신규 회원</a></p>" );
			out.println( "<p><a href='sort'>정렬</a></p>" );
			while( resultSet.next() ) {
				out.println( resultSet.getInt( "MNO" ) + ". " 
			+ "<a href='update?no=" + resultSet.getInt( "MNO" ) + "'>" 
			+ resultSet.getString( "MNAME" ) + "</a>" + ", "
			+ resultSet.getString( "EMAIL" ) + ", " 
			+ resultSet.getDate( "CRE_DATE" ) 
			+ "<a href='remove?no=" + resultSet.getInt( "MNO" ) + "'>[삭제]</a><br>" );
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
