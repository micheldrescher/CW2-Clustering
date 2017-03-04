package uk.ac.ox.oerc.clustering.webapp.filter;

import java.io.IOException;

import javax.faces.application.ViewExpiredException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthFilter implements Filter {

	public AuthFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			// check whether session variable is set
			HttpSession ses = req.getSession(false);
			//  allow user to proceed if URL is login.xhtml or user logged in or user is accessing any page in //public folder
            String reqURI = req.getRequestURI();
            if (reqURI.contains("/admin/") && ses != null && ses.getAttribute("username") == null) {
            	res.sendRedirect(req.getContextPath() + "/Login.xhtml");
            } else {
            	chain.doFilter(request, response);
            }
		} catch (ViewExpiredException vee) {
			// redirect to index
			res.sendRedirect(req.getContextPath() + "/index.xhtml");
		} catch (ServletException se) {
			// seem to be the same as above, but wrapped in a servlet exception...
			if (se.getRootCause() instanceof ViewExpiredException) {
				res.sendRedirect(req.getContextPath() + "/index.xhtml");
			}
		}
	} // doFilter

	@Override
	public void destroy() {

	}
}
