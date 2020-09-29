package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.annotation.Component;

@Component("/auth/logout.do")
public class MemberLogOutController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		HttpSession httpSession = (HttpSession) model.get("session");
		httpSession.invalidate();
		return "redirect:../auth/login.do";
	}

}
