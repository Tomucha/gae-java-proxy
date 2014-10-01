package cz.tomucha.gae.proxy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

/**
 * Use this example to integrate {@Proxy} with your project.
 */
public class ProxyServlet extends javax.servlet.http.HttpServlet {

	/**
	 * Don't forget to change this constant!
	 */
	public static String PROXY_TARGET = "https://github.com/";

	private static Proxy proxy = new Proxy(PROXY_TARGET);

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