package cz.tomucha.gae.proxy;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Use this example to integrate {@Proxy} with your project.
 */
public class ProxyServlet extends javax.servlet.http.HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Proxy proxy;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);		
		this.proxy = new Proxy(config.getInitParameter("target"));
	}
	
	protected void doPost(HttpServletRequest clientRequest, HttpServletResponse proxyResponse) throws javax.servlet.ServletException, IOException {
		proxy.doPost(clientRequest, proxyResponse);
	}

	protected void doGet(HttpServletRequest clientRequest, HttpServletResponse proxyResponse) throws javax.servlet.ServletException, IOException {
		proxy.doGet(clientRequest, proxyResponse);
	}

	protected void doDelete(HttpServletRequest clientRequest, HttpServletResponse proxyResponse) throws javax.servlet.ServletException, IOException {
		proxy.doDelete(clientRequest, proxyResponse);
	}

	protected void doPut(HttpServletRequest clientRequest, HttpServletResponse proxyResponse) throws javax.servlet.ServletException, IOException {
		proxy.doPut(clientRequest, proxyResponse);
	}

}