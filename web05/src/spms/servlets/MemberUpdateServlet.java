package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( "/member/update" )
@SuppressWarnings("serial")
public class MemberUpdateServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ( Connection ) this.getServletContext().getAttribute( "connection" );
			statement = connection.createStatement();
			resultSet = statement.executeQuery(
					"SELECT MNO, EMAIL, MNAME, CRE_DATE FROM MEMBERS"
					+ " WHERE MNO = " + request.getParameter( "no" ) );
			resultSet.next();
			
			response.setContentType( "text/html; charset=utf-8" );		
			PrintWriter out = response.getWriter();
			out.println( "<html><head><title>회원정보</title></head><body>" );
			out.println( "<h1>회원정보</h1>" );
			out.println( "<form action='update' method='post'>" );
			out.println( "번호: <input type='text' name='no' value='"
					+ request.getParameter( "no" ) + "' readonly><br>" );
			out.println( "이름: <input type='text' name='name' value='"
					+ resultSet.getString( "MNAME" ) +"'><br>" );
			out.println( "이메일: <input type='text' name='email' value='"
					+ resultSet.getString( "EMAIL" ) + "'><br>" );
			out.println( "가입일: " + resultSet.getDate( "CRE_DATE" ) + "<br>" );
			out.println( "<input type='submit' value='저장'>" );
			out.println( "<input type='button' value='삭제'"
					+ " onclick='location.href=\"remove?no= " 
					+ request.getParameter( "no" ) + "\"'>" );
			out.println( "<input type='button' value='취소'"
					+ " onclick='location.href=\"list\"'>" );
			out.println( "</form>" );
			out.println( "</body></html>" );
			
		} catch ( Exception e ) {
			throw new ServletException( e );
		} finally {
			try { if( resultSet != null ) resultSet.close(); } catch( Exception e ) {}
			try { if( statement != null ) statement.close(); } catch( Exception e ) {}
//			try { if( connection != null ) connection.close(); } catch( Exception e ) {}
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding( "UTF-8" );
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try { 
			connection = ( Connection ) this.getServletContext().getAttribute( "connection" );			
			preparedStatement = connection.prepareStatement( 
					"UPDATE MEMBERS SET MNAME = ?, EMAIL = ?, MOD_DATE = NOW()"
					+ "WHERE MNO = ?" );
			preparedStatement.setString( 1, request.getParameter( "name" ) );
			preparedStatement.setString( 2, request.getParameter( "email" ) );
			preparedStatement.setString( 3, request.getParameter( "no" ) );
			preparedStatement.executeUpdate();
			
			response.sendRedirect( "list" );
		
		}catch( Exception e ) {
			throw new ServletException( e );
		}finally {
			try { if( preparedStatement != null ) preparedStatement.close(); } catch( Exception e ) {}
//			try { if( connection != null ) connection.close(); } catch( Exception e ) {}
		}
		
	}
	
}
