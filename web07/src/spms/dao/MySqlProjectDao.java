package spms.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import spms.annotation.Component;
import spms.vo.Project;

@Component("projectDao")
public class MySqlProjectDao implements ProjectDao {
	SqlSessionFactory sqlSessionFactory;
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public List<Project> selectList(HashMap<String, Object> map) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.selectList("spms.dao.ProjectDao.selectList", map);	
		}finally {
			sqlSession.close();
		}
	}

	public int insert(Project project) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int count = sqlSession.insert("spms.dao.ProjectDao.insert", project);
			sqlSession.commit();
			return count;
		}
		finally {
			sqlSession.close();
		}
	}

	public Project selectOne(int no) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.selectOne("spms.dao.ProjectDao.selectOne", no);
		}finally {
			sqlSession.clearCache();
		}
	}

	public int update(Project project) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			Project originalProject = selectOne(project.getNo());
			HashMap<String, Object> parameterMap = new HashMap<>();
			if(!originalProject.getTitle().equals(project.getTitle())) {
				parameterMap.put("title", project.getTitle());	
			}
			if(!originalProject.getContent().equals(project.getContent())) {
				parameterMap.put("content", project.getContent());
			}
			if(originalProject.getStartDate().compareTo(project.getStartDate()) != 0) {
				parameterMap.put("startDate", project.getStartDate());
			}
			if(originalProject.getEndDate().compareTo(project.getEndDate()) != 0) {
				parameterMap.put("endDate", project.getEndDate());
			}
			if(originalProject.getState() != project.getState()) {
				parameterMap.put("state", project.getState());
			}
			if(!originalProject.getTags().equals(project.getTags())) {
				parameterMap.put("tags", project.getTags());
			}
			if(parameterMap.size() > 0) {
				parameterMap.put("no", project.getNo());
				int count = sqlSession.update("spms.dao.ProjectDao.update", parameterMap);
				sqlSession.commit();
				return count;	
			}else {
				return 0;
			}
		}finally {
			sqlSession.close();
		}
	}

	@Override
	public int delete(int no) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int count = sqlSession.delete("spms.dao.ProjectDao.delete", no);
			sqlSession.commit();
			return count;
		}finally {
			sqlSession.close();			
		}
	}	

}
