package spms.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.controls.Controller;
import spms.controls.MemberAddController;
import spms.controls.MemberListController;
import spms.controls.MemberLogInController;
import spms.controls.MemberLogOutController;
import spms.controls.MemberRemoveController;
import spms.controls.MemberSortController;
import spms.controls.MemberUpdateController;
import spms.dao.MemberDao;
import spms.vo.Member;

@WebServlet("*.do")
@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath();
		try {
			HashMap<String, Object> model = new HashMap<>();
			ServletContext sc = this.getServletContext();
			model.put("memberDao", sc.getAttribute("memberDao"));
			model.put("session", request.getSession());
			Controller pageController = null;
			if("/member/list.do".equals(servletPath)) {
				pageController = new MemberListController();
			}else if("/member/add.do".equals(servletPath)) {
				pageController = new MemberAddController();
				if(request.getParameter("name") != null) {
					model.put("member", new Member().
							setName(request.getParameter("name")).
							setEmail(request.getParameter("email")).
							setPassword(request.getParameter("password")));	
				}
			}else if("/member/update.do".equals(servletPath)) {
				pageController = new MemberUpdateController();
				if(request.getParameter("name") != null) {
					model.put("member", new Member().
							setName(request.getParameter("name"))
							.setEmail(request.getParameter("email"))
							.setNo(Integer.parseInt(request.getParameter("no"))));
				}else {
					model.put("no", Integer.parseInt(request.getParameter("no")));
				}
			}else if("/member/remove.do".equals(servletPath)) {
				pageController = new MemberRemoveController();
				model.put("no", Integer.parseInt(request.getParameter("no")));
			}else if("/member/sort.do".equals(servletPath)) {
				pageController = new MemberSortController();
			}else if("/auth/login.do".equals(servletPath)) {
				pageController = new MemberLogInController();
				if(request.getParameter("email") != null) {
					model.put("member", new Member()
							.setEmail(request.getParameter("email"))
							.setPassword(request.getParameter("password")));
				}
			}else if("/auth/logout.do".equals(servletPath)) { // 
				pageController = new MemberLogOutController();
			}
			String viewUrl = pageController.execute(model);
			
			for(String key : model.keySet()) {
				request.setAttribute(key, model.get(key));
			}
			if(viewUrl.startsWith("redirect:")) {	
				response.sendRedirect(viewUrl.substring(9));
				return; 
			}else {
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);	
			}	
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute( "error", e );
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(
					"/Error.jsp" );
			requestDispatcher.forward( request, response );		
		}
	}
	
}
