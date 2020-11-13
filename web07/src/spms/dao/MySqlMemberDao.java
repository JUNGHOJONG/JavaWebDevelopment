package spms.dao;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.vo.Member;

public class MySqlMemberDao implements MemberDao{
	
	DataSource ds;
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	public List<Member> selectList() throws Exception{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ds.getConnection();
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
			try{ if(connection != null) connection.close(); } catch(Exception e) {}
		}
	}
	
	public int insert( Member member ) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ds.getConnection();
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
			try { if( preparedStatement != null ) preparedStatement.close(); } 
			catch( Exception e ) {}
			try{ if(connection != null) connection.close(); } catch(Exception e) {}
		}
	}
	
	public int delete( int no ) throws Exception{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(
					"DELETE FROM MEMBERS WHERE MNO = " + no );
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;		
		} finally {
			try { if( preparedStatement != null ) preparedStatement.close(); }
			catch( Exception e ) {}
			try{ if(connection != null) connection.close(); } catch(Exception e) {}
		}
	}
	
	// 회원 상세 정보 조회
	public Member selectOne( int no ) throws Exception{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ds.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(
					"SELECT MNO, EMAIL, MNAME, CRE_DATE FROM MEMBERS"
					+ " WHERE MNO = " + no );
			if(resultSet.next()) {
				return new Member().setNo(resultSet.getInt("MNO"))
				.setEmail( resultSet.getString( "EMAIL" ) )
				.setName( resultSet.getString( "MNAME" ) )
				.setCreatedDate( resultSet.getDate( "CRE_DATE" ) );
			}
			throw new Exception("해당 번호의 프로젝트를 찾을 수 없습니다.");
		} catch ( Exception e ) {
			throw e;
		} finally {
			try { if( resultSet != null ) resultSet.close(); } catch( Exception e ) {}
			try { if( statement != null ) statement.close(); } catch( Exception e ) {}
			try{ if(connection != null) connection.close(); } catch(Exception e) {}
		}
	}
	
	public int update( Member member ) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ds.getConnection();
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
			try{ if(connection != null) connection.close(); } catch(Exception e) {}
		}
	}
	
	public Member exist( String email, String password ) throws Exception{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement( 
					"SELECT MNAME, EMAIL FROM MEMBERS WHERE EMAIL=? AND PWD=?" );
			preparedStatement.setString( 1, email );
			preparedStatement.setString( 2, password );
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return new Member().setName(resultSet.getString("MNAME"))
						.setEmail(resultSet.getString("EMAIL"));
			}else {
				System.out.println( "해당 되는 정보가 없습니다." );
				return null;	
			}
		} catch ( Exception e) {
			throw e;
		} finally {
			try { if( resultSet != null ) resultSet.close(); } catch( Exception e ) {}
			try { if( preparedStatement != null ) preparedStatement.close(); }
			catch( Exception e ) {}
			try{ if(connection != null) connection.close(); } catch(Exception e) {}
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
			connection = ds.getConnection();
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
			try{ if(connection != null) connection.close(); } catch(Exception e) {}
		}
	}

}
