package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberAddController implements Controller {
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.containsKey("member")) {
			MemberDao memberDao = (MemberDao) model.get("memberDao");
			memberDao.insert((Member) model.get("member"));
			return "redirect:../member/list.do";
		}
		return "/member/MemberAdd.jsp";
	}

}
