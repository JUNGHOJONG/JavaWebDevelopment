package spms.controls;

import java.util.Map;
import javax.servlet.http.HttpSession;
import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberLogInController implements Controller {
	MemberDao memberDao;
	
	public MemberLogInController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.containsKey("member")) {
			Member member = (Member) model.get("member");
			if(memberDao.exist(member.getEmail(), member.getPassword()) != null) {
				HttpSession session = (HttpSession) model.get("session");
				session.setAttribute("member", member);
				return "redirect:../member/list.do";
			}
			return "/auth/LogInFail.jsp";
		}
		return "/auth/LogInForm.jsp";
	}
}
