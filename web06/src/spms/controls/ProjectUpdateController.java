package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.ProjectDao;
import spms.vo.Project;

@Component("/project/update.do")
public class ProjectUpdateController implements Controller, DataBinding {

	ProjectDao projectDao;
	public ProjectUpdateController setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Project project = (Project) model.get("project");
		if(project.getTitle() != null) {
			projectDao.update(project);
			return "redirect:./list.do";
		}
		model.put("project", projectDao.selectOne(project.getNo()));
		return "/project/ProjectUpdate.jsp";
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"no", Integer.class,
				"project", spms.vo.Project.class
		};
	}

}
