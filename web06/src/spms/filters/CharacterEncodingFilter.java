package spms.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {

	FilterConfig filterConfig;
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain nextFilter)
			throws IOException, ServletException {
		
		// �������� �����ϱ� ���� ������ �۾�
		
		request.setCharacterEncoding( filterConfig.getInitParameter( "encoding" ) );
		nextFilter.doFilter( request, response );
		
		// �������� ������ �� ������ �۾�
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

}