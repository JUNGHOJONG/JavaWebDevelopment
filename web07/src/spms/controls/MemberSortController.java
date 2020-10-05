package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.dao.MemberDao;

@Component("/member/sort.do")
public class MemberSortController implements Controller {
	MemberDao memberDao;
	
	public MemberSortController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		memberDao.sort();
		return "redirect:list.do";
	}
}
