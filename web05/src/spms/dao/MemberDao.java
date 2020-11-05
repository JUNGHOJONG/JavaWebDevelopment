package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import spms.vo.Member;

public class MemberDao {
	// �����͸� �����ϴ� �Լ�
	Connection connection;
	public void setConnection( Connection connection ) {
		this.connection = connection;
	}
	// �����͸� ó���ϴ� �Լ�
	// ��ü�� ��ȯ�ϴ� �Լ�
	public List<Member> selectList() throws Exception{
		Statement statement = null;
		ResultSet resultSet = null;
		try {
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
		}
	}
	
	// ȸ�� ��� 
	public int insert( Member member ) throws Exception {
		PreparedStatement preparedStatement = null;
		try {
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
		}
	}
	
	// ȸ�� ����
	public int delete( int no ) {
		return 0;
	}
	
	// ȸ�� �� ���� ��ȸ
	public Member selectOne( int no ) throws Exception{
		Statement statement = null;
		ResultSet resultSet = null;
		try {
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
		}
	}
	
	// ȸ�� ���� ����
	public int update( Member member ) throws Exception {
		PreparedStatement preparedStatement = null;
		try {
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
		}
	}
	
	// Member ��ü ������ ���� ������ null����
	public Member exist( String email, String password ) {
		return null;
	}

}
