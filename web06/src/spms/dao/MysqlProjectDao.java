package spms.dao;

import java.sql.Connection;
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
		Connection connection = null; // 여기서 종료 후 닫아줘야한다.
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ds.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(
					"SELECT PNO, PNAME, START_DATE, END_DATE, STATE"
					+ " FROM PROJECTS ORDER BY CREATION_DATE DESC");
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

}
