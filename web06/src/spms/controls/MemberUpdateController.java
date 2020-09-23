package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberUpdateController implements Controller {
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		MemberDao memberDao = (MemberDao) model.get("memberDao");
		if(model.containsKey("member")) {
			memberDao.update((Member) model.get("member"));
			return "redirect:list.do";
		}
		Member member = memberDao.selectOne((Integer) model.get("no"));
		model.put("member", member);
		return "/member/MemberUpdate.jsp";
	}

}
