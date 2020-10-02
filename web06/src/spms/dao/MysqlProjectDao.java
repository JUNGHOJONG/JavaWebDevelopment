package spms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.vo.Project;

public class MysqlProjectDao implements ProjectDao {
	DataSource ds;
	public void setDateSource(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public List<Project> selectList() throws Exception {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ds.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(
					"SELECT PNO, PNAME, START_DATE, END_DATE, STATE"
					+ " FROM PROJECTS ORDER BY PNO DESC");
			List<Project> list = new ArrayList<>();
			while(resultSet.next()) {
				list.add(new Project().setNo(resultSet.getInt("PNO"))
						.setTitle(resultSet.getString("PNAME"))
						.setStartDate(resultSet.getDate("START_DATE"))
						.setEndDate(resultSet.getDate("END_DATE"))
						.setState(resultSet.getInt("STATE")));
			}
			return list;	
		}catch(Exception e) {
			throw e;
		}finally {
			try{ if(resultSet != null) resultSet.close(); } catch(Exception e) {}
			try{ if(statement != null) statement.close(); } catch(Exception e) {}
			try{ if(connection != null) connection.close(); } catch(Exception e) {}
		}
	}

	public int insert(Project project) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(
					"INSERT INTO PROJECTS(PNAME, CONTENT, START_DATE, END_DATE,"
					+ " STATE, CREATION_DATE, TAGS)"
					+ " VALUES(?, ?, ?, ?, 0, NOW(), ?);");
			preparedStatement.setString(1, project.getTitle());
			preparedStatement.setString(2, project.getContent());
			preparedStatement.setDate(3, (Date) project.getStartDate());
			preparedStatement.setDate(4, (Date) project.getEndDate());
			preparedStatement.setString(5, project.getTags());
			return preparedStatement.executeUpdate();	
		}catch(Exception e) {
			throw e;
		}finally {
			try { if(preparedStatement != null) preparedStatement.close(); } catch(Exception e) {}
			try { if(connection != null) connection.close(); } catch(Exception e) {}
		}
	}

	public Project selectOne(int no) throws Exception {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ds.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(
					"SELECT PNO, PNAME, CONTENT, START_DATE,"
					+ " END_DATE, STATE, CREATION_DATE, TAGS"
					+ " FROM PROJECTS WHERE PNO = " + no);
			if(resultSet.next()) {
				return new Project().setNo(resultSet.getInt("PNO"))
						.setTitle(resultSet.getString("PNAME"))
						.setContent(resultSet.getString("CONTENT"))
						.setStartDate(resultSet.getDate("START_DATE"))
						.setEndDate(resultSet.getDate("END_DATE"))
						.setCreatedDate(resultSet.getDate("CREATION_DATE"))
						.setState(resultSet.getInt("STATE"))
						.setTags(resultSet.getString("TAGS"));
			}
			throw new Exception("해당 번호의 프로젝트를 찾을 수 없습니다.");	
		}catch(Exception e) {
			throw e;
		}finally {
			try { if(resultSet != null) resultSet.close(); } catch(Exception e) {}
			try { if(statement != null) statement.close(); } catch(Exception e) {}
			try { if(connection != null) connection.close(); } catch(Exception e) {}
		}
	}

	public int update(Project project) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(
					"UPDATE PROJECTS SET PNAME = ?, CONTENT = ?, START_DATE = ?,"
					+ " END_DATE = ?, STATE = ?, TAGS = ?"
					+ " WHERE PNO = ?");
			preparedStatement.setString(1, project.getTitle());
			preparedStatement.setString(2, project.getContent());
			preparedStatement.setDate(3, (Date) project.getStartDate());
			preparedStatement.setDate(4, (Date) project.getEndDate());
			preparedStatement.setInt(5, project.getState());
			preparedStatement.setString(6, project.getTags());
			preparedStatement.setInt(7, project.getNo());
			return preparedStatement.executeUpdate();	
		}catch(Exception e) {
			throw e;
		}finally {
			try { if(preparedStatement != null) preparedStatement.close(); } catch(Exception e) {}
			try { if(connection != null) connection.close(); } catch(Exception e) {}
		}
	}

	@Override
	public int remove(int no) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(
					"DELETE FROM PROJECTS WHERE PNO = ?");
			preparedStatement.setInt(1, no);
			return preparedStatement.executeUpdate();
		}catch(Exception e) {
			throw e;
		}finally {
			try { if(preparedStatement != null) preparedStatement.close(); } catch(Exception e) {}
			try { if(connection != null) connection.close(); } catch(Exception e) {}			
		}
	}

	@Override
	public void sort() throws Exception {
		String[] query = {
				"ALTER TABLE PROJECTS AUTO_INCREMENT=1",
				"SET @COUNT = 0",
				"UPDATE PROJECTS SET PNO = @COUNT:=@COUNT+1"
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
