package spms.dao;

import java.util.List;

import spms.vo.Project;

public interface ProjectDao {
	List<Project> selectList() throws Exception;
	int insert(Project project) throws Exception;
	Project selectOne(int no) throws Exception;
	int update(Project project) throws Exception;
	int remove(int no) throws Exception;
	void sort() throws Exception;
}