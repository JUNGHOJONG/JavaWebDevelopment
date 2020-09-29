package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.vo.Member;

@Component("/member/add.do")
public class MemberAddController implements Controller, DataBinding {
	MemberDao memberDao;
	
	public MemberAddController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member) model.get("member");
		if(member.getName() != null) {
			memberDao.insert(member);
			return "redirect:../member/list.do";
		}
		return "/member/MemberAdd.jsp";
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[]{"member", spms.vo.Member.class};
	}
}
