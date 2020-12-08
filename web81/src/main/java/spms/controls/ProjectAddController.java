package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.ProjectDao;
import spms.vo.Project;

@Component("/project/add.do")
public class ProjectAddController implements Controller, DataBinding{
	
	ProjectDao projectDao;
	
	public ProjectAddController setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Project project = (Project) model.get("project");
		if(project.getTitle() != null) {
			projectDao.insert(project);
			return "redirect:./list.do";
		}
		return "/project/ProjectAdd.jsp";
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"project", spms.vo.Project.class
		};
	}

}