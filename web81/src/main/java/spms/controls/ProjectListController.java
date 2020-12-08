package spms.controls;

import java.util.HashMap;
import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.ProjectDao;

@Component("/project/list.do")
public class ProjectListController implements Controller, DataBinding{
	
	ProjectDao projectDao;
	
	public ProjectListController setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) 
			throws Exception {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("orderCond", model.get("orderCond"));
		model.put("projects", projectDao.selectList(paraMap));
		return "/project/ProjectList.jsp";
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[]{
			"orderCond", String.class
		};
	}

}
