package spms.controls;

import java.util.HashMap;
import java.util.Map;

import spms.annotation.Component;
import spms.dao.MemberDao;

@Component("/member/list.do")
public class MemberListController implements Controller {
	MemberDao memberDao;
	
	public MemberListController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception{
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
	  	paramMap.put("orderCond", model.get("orderCond"));
	  	
	    model.put("members", memberDao.selectList(paramMap));
		return "/member/MemberList.jsp";
	}
	
	public Object[] getDataBinders() {
		    return new Object[]{
		        "orderCond", String.class
		    };
	}
		  
}
