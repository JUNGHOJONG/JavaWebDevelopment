package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import spms.vo.Member;

public class MemberDao {
	// 데이터를 접근하는 함수
	Connection connection;
	public void setConnection( Connection connection ) {
		this.connection = connection;
	}
	// 데이터를 처리하는 함수
	// 객체를 반환하는 함수
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
	
	// 회원 등록 
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
	
	// 회원 삭제
	public int delete( int no ) {
		return 0;
	}
	
	// 회원 상세 정보 조회
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
	
	// 회원 정보 변경
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
	
	// Member 객체 있응면 리턴 없으면 null리턴
	public Member exist( String email, String password ) {
		return null;
	}

}
