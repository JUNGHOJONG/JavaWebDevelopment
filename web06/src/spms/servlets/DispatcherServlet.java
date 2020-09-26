package spms.servlets;

import java.io.IOException;

import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.bind.DataBinding;
import spms.bind.ServletDataDataBinder;
import spms.context.ApplicationContext;
import spms.controls.Controller;
import spms.listeners.ContextLoaderListener;

@WebServlet("*.do")
@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath();
		try {
			ApplicationContext applicationContext = ContextLoaderListener.getApplicationContext();
			HashMap<String, Object> model = new HashMap<>();
			model.put("session", request.getSession());
			Controller pageController = (Controller) applicationContext.getBean(servletPath);
			if(pageController == null) {
				throw new Exception("요청한 서비를 찾을 수 없습니다.");
			}
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
