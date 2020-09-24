package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberUpdateController implements Controller {
	MemberDao memberDao;
	
	public MemberUpdateController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.containsKey("member")) {
			memberDao.update((Member) model.get("member"));
			return "redirect:list.do";
		}
		Member member = memberDao.selectOne((Integer) model.get("no"));
		model.put("member", member);
		return "/member/MemberUpdate.jsp";
	}
}
