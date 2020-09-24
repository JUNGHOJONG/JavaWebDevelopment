package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;

public class MemberRemoveController implements Controller {
	MemberDao memberDao;
	
	public MemberRemoveController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		memberDao.delete((Integer)model.get("no"));
		return "redirect:list.do";
	}
}
