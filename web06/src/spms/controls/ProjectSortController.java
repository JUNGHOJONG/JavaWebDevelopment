package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.dao.ProjectDao;

@Component("/project/sort.do")
public class ProjectSortController implements Controller {

	ProjectDao projectDao;
	
	public ProjectSortController setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		projectDao.sort();
		return "redirect:list.do";
	}

}
