package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;

public class MemberSortController implements Controller {
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		MemberDao memberDao = (MemberDao) model.get("memberDao");
		memberDao.sort();
		return "redirect:list.do";
	}

}
