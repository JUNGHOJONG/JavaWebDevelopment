package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import spms.util.DBConnectionPool;
import spms.vo.Member;

public class MemberDao {
	
	DBConnectionPool connPool;
	public void setDbConnectionPool(DBConnectionPool connPool) {
		this.connPool = connPool;
	}
	
	// ��ü�� ��ȯ�ϴ� �Լ�, �����͸� ó���ϴ� �Լ�
	public List<Member> selectList() throws Exception{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = connPool.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery( 
					"SELECT MNO, MNAME, EMAIL, CRE_DATE FROM MEMBERS "
					+ "ORDER BY MNO ASC" );
			ArrayList<Member> members = new ArrayList<>();
			while( resultSet.next() ) {
				members.add( new Member().setNo( resultSet.getInt( "MNO" ) )
						.setName( resultSet.getString( "MNAME" ) )
						.setEmail( resultSet.getString( "EMAIL" ) )
						.setCreatedDate( resultSet.getDate( "CRE_DATE" ) ) );
			}
			return members;
		} catch ( Exception e ) {
			throw e;
		} finally {
			try{ if( resultSet != null ) resultSet.close(); } catch( Exception e ) {}
			try{ if( statement != null ) statement.close(); } catch( Exception e ) {}
			if(connection != null) connPool.returnConnection(connection);
		}
	}
	
	// ȸ�� ��� 
	public int insert( Member member ) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = connPool.getConnection();
			preparedStatement = connection.prepareStatement(
					"Insert INTO MEMBERS( EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE )"
						+ " VALUES( ?, ?, ?, NOW(), NOW() )" );
			preparedStatement.setString( 1, member.getEmail() );
			preparedStatement.setString( 2, member.getPassword() );
			preparedStatement.setString( 3, member.getName() );
			return preparedStatement.executeUpdate();
		} catch ( Exception e) {
			throw e;
		} finally {
			try { if( preparedStatement != null ) preparedStatement.close(); } catch( Exception e ) {}
			if(connection != null) connPool.returnConnection(connection);
		}
	}
	
	// ȸ�� ����
	public int delete( int no ) throws Exception{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = connPool.getConnection();
			preparedStatement = connection.prepareStatement(
					"DELETE FROM MEMBERS WHERE MNO = " + no );
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;		
		} finally {
			try { if( preparedStatement != null ) preparedStatement.close(); }
			catch( Exception e ) {}
			if(connection != null) connPool.returnConnection(connection);
		}
	}
	
	// ȸ�� �� ���� ��ȸ
	public Member selectOne( int no ) throws Exception{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = connPool.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(
					"SELECT MNO, EMAIL, MNAME, CRE_DATE FROM MEMBERS"
					+ " WHERE MNO = " + no );
			resultSet.next();
			Member member = new Member();
			member.setEmail( resultSet.getString( "EMAIL" ) )
			.setName( resultSet.getString( "MNAME" ) )
			.setCreatedDate( resultSet.getDate( "CRE_DATE" ) );
			return member;
		} catch ( Exception e ) {
			throw e;
		} finally {
			try { if( resultSet != null ) resultSet.close(); } catch( Exception e ) {}
			try { if( statement != null ) statement.close(); } catch( Exception e ) {}
			if(connection != null) connPool.returnConnection(connection);
		}
	}
	
	// ȸ�� ���� ����
	public int update( Member member ) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = connPool.getConnection();
			preparedStatement = connection.prepareStatement( 
					"UPDATE MEMBERS SET MNAME = ?, EMAIL = ?, MOD_DATE = NOW()"
					+ "WHERE MNO = ?" );
			preparedStatement.setString( 1, member.getName() );
			preparedStatement.setString( 2, member.getEmail() );
			preparedStatement.setInt( 3, member.getNo() );
			return preparedStatement.executeUpdate();
		} catch ( Exception e) {
			throw e;
		} finally {
			try { if( preparedStatement != null ) preparedStatement.close(); } 
			catch( Exception e ) {}
			if(connection != null) connPool.returnConnection(connection);
		}
	}
	
	// Member ��ü ������ ���� ������ null����
	public Member exist( String email, String password ) throws Exception{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = connPool.getConnection();
			preparedStatement = connection.prepareStatement( 
					"SELECT EMAIL, PWD FROM MEMBERS WHERE EMAIL=? AND PWD=?" );
			preparedStatement.setString( 1, email );
			preparedStatement.setString( 2, password );
			resultSet = preparedStatement.executeQuery();
			if( resultSet.next() ) {
				Member member = new Member();
				member.setEmail( email ).setPassword( password );
				return member;
			}else {
				System.out.println( "�ش� �Ǵ� ������ �����ϴ�." );
				return null;	
			}
		} catch ( Exception e) {
			throw e;
		} finally {
			try { if( resultSet != null ) resultSet.close(); } catch( Exception e ) {}
			try { if( preparedStatement != null ) preparedStatement.close(); }
			catch( Exception e ) {}
			if(connection != null) connPool.returnConnection(connection);
		}
	}
	
	public void sort() throws Exception{
		String[] query = {
				"ALTER TABLE MEMBERS AUTO_INCREMENT=1",
				"SET @COUNT = 0",
				"UPDATE MEMBERS SET MNO = @COUNT:=@COUNT+1"
		};
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = connPool.getConnection();
			for( String s : query ) {
				preparedStatement = connection.prepareStatement( s );
				preparedStatement.executeUpdate();
				preparedStatement.clearParameters();
			}
		} catch ( Exception e ) {
			throw e;
		} finally {
			try { if( preparedStatement != null ) preparedStatement.close(); }
			catch( Exception e ) {}
			if(connection != null) connPool.returnConnection(connection);
		}
	}

}
