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

import spms.bind.DataBinding;
import spms.bind.ServletDataDataBinder;
import spms.controls.Controller;

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
			model.put("session", request.getSession());
			Controller pageController = (Controller) sc.getAttribute(servletPath);
			
			if(pageController instanceof DataBinding) {
				prepareRequestData(request, model, (DataBinding) pageController);
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
	
	private void prepareRequestData(HttpServletRequest request,
			HashMap<String, Object> model, DataBinding pageController) 
				throws Exception {
		Object[] dataBinders = pageController.getDataBinders();
		String dataName = null;
		Class<?> dataType = null;
		Object dataObject = null;
		int size = dataBinders.length;
		for(int i=0; i<size; i+=2) {
			dataName = (String) dataBinders[i];
			dataType = (Class<?>) dataBinders[i+1];
			dataObject = ServletDataDataBinder.bind(request, dataName, dataType);
			model.put(dataName, dataObject);
		}
	}
	
}
