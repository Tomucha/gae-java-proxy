package cz.tomucha.gae.proxy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Logger;

/**
 *
 *
 *
 */
public class Proxy {

	private static final Logger log = Logger.getLogger(Proxy.class.getName());

	/**
	 * Which headers from the original client request we want to send to our proxy target?
	 */
	private static String[] REQUEST_HEADERS_TO_COPY = new String[]{
			HttpRequest.HEADER_ACCEPT,
			HttpRequest.HEADER_CONTENT_TYPE,
			HttpRequest.HEADER_CONTENT_LENGTH,
			HttpRequest.HEADER_CONTENT_ENCODING,
			HttpRequest.HEADER_ACCEPT_CHARSET,
			HttpRequest.HEADER_ACCEPT_ENCODING,
			HttpRequest.HEADER_AUTHORIZATION,
			HttpRequest.HEADER_REFERER,
			HttpRequest.HEADER_USER_AGENT,
			"Cookie"
	};
	/**
	 * Which headers from the original server response we want to send to client?
	 */
	private static String[] RESPONSE_HEADERS_TO_COPY = new String[]{
			HttpRequest.HEADER_CONTENT_TYPE,
			HttpRequest.HEADER_CONTENT_LENGTH,
			HttpRequest.HEADER_CONTENT_ENCODING,
			HttpRequest.HEADER_CACHE_CONTROL,
			HttpRequest.HEADER_DATE,
			HttpRequest.HEADER_ETAG,
			HttpRequest.HEADER_EXPIRES,
			HttpRequest.HEADER_LAST_MODIFIED,
			"Set-Cookie",
			"Pragma",
			"Transfer-Encoding",
			"Vary"
	};

	private String proxyTarget;

	public Proxy(String proxyTarget) {
		if (proxyTarget == null) {
			throw new IllegalArgumentException("Please specify proxy target, eq. http://myhost.example.com");
		}
		this.proxyTarget = proxyTarget;
	}

	// some configuration
	static {
		HttpRequest.setConnectionFactory(new HttpRequest.ConnectionFactory() {

			public HttpURLConnection create(URL url) throws IOException {
				HttpURLConnection u = (HttpURLConnection) url.openConnection();
				u.setReadTimeout(30 * 1000);
				return u;
			}

			@Override
			public HttpURLConnection create(URL url, java.net.Proxy proxy) throws IOException {
				HttpURLConnection u = (HttpURLConnection) url.openConnection(proxy);
				u.setReadTimeout(30 * 1000);
				return u;
			}
		});
	}

	// main Servlet methods

	protected void doPost(HttpServletRequest clientRequest, HttpServletResponse proxyResponse) throws javax.servlet.ServletException, IOException {
		String requestString = renderRequestString(clientRequest);
		HttpRequest proxyRequest = HttpRequest.post(requestString);
		doProxy(clientRequest, proxyResponse, proxyRequest, true);
	}

	protected void doGet(HttpServletRequest clientRequest, HttpServletResponse proxyResponse) throws javax.servlet.ServletException, IOException {
		String requestString = renderRequestString(clientRequest);
		HttpRequest proxyRequest = HttpRequest.get(requestString);
		doProxy(clientRequest, proxyResponse, proxyRequest, false);
	}

	protected void doDelete(HttpServletRequest clientRequest, HttpServletResponse proxyResponse) throws javax.servlet.ServletException, IOException {
		String requestString = renderRequestString(clientRequest);
		HttpRequest proxyRequest = HttpRequest.delete(requestString);
		doProxy(clientRequest, proxyResponse, proxyRequest, false);
	}

	protected void doPut(HttpServletRequest clientRequest, HttpServletResponse proxyResponse) throws javax.servlet.ServletException, IOException {
		String requestString = renderRequestString(clientRequest);
		HttpRequest proxyRequest = HttpRequest.put(requestString);
		doProxy(clientRequest, proxyResponse, proxyRequest, true);
	}

	// private utils

	private String renderRequestString(HttpServletRequest clientRequest) {
		String requestString = clientRequest.getRequestURI();
		if (clientRequest.getQueryString() != null) {
			requestString = requestString + "?" + clientRequest.getQueryString();
		}
		return proxyTarget + requestString;
	}

	private void doProxy(HttpServletRequest clientRequest, HttpServletResponse proxyResponse, HttpRequest proxyRequest, boolean copyRequestBody) throws IOException {
		// let's copy some of the client's header
		copyRequestHeaders(clientRequest, proxyRequest);

		if (copyRequestBody) {
			// tunnel client's request's data to the proxy target
			// - now we make an actual HTTP request to proxy target
			proxyRequest.send(clientRequest.getInputStream());
		}

		// server response status
		proxyResponse.setStatus(proxyRequest.code());

		// let's copy some of the response headers to our response
		copyResponseHeaders(proxyRequest, proxyResponse);

		// copy the response stream
		proxyRequest.receive(proxyResponse.getOutputStream());

		// and we are done
		proxyResponse.getOutputStream().flush();
	}

	private void copyResponseHeaders(HttpRequest targetResponse, HttpServletResponse proxyResponse) {
		for (int i = 0; i < RESPONSE_HEADERS_TO_COPY.length; i++) {
			String header = RESPONSE_HEADERS_TO_COPY[i];
			String[] values = targetResponse.headers(header.toLowerCase());
			if (values != null && values.length > 0) {
				for (int j = 0; j < values.length; j++) {
					String value = values[j];
					// log.info("Response header: "+header+"="+value);
					proxyResponse.addHeader(header, value);
				}
			}
		}
	}

	private void copyRequestHeaders(HttpServletRequest clientRequest, HttpRequest proxyRequest) {
		for (int i = 0; i < REQUEST_HEADERS_TO_COPY.length; i++) {
			String header = REQUEST_HEADERS_TO_COPY[i];
			Enumeration<String> values = clientRequest.getHeaders(header);
			if (values != null && values.hasMoreElements()) {
				while (values.hasMoreElements()) {
					String value = values.nextElement();
					// log.info("Request header: "+header+"="+value);
					proxyRequest.header(header, value);
				}
			}
		}
	}

}