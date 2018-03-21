gae-java-proxy
==============

Simple Java implementation of an HTTP proxy, prepared for deploying to Google App Engine.

I tried to make as simple as it gets. It has no dependencies, except for https://github.com/kevinsawicki/http-request,
which I copied directly into the project.

Classes
-------
- [HttpRequest](src/main/java/cz/tomucha/gae/proxy/HttpRequest.java): nice HTTP client based on http://docs.oracle.com/javase/7/docs/api/java/net/HttpURLConnection.html, written by [Kevin Sawicki](https://github.com/kevinsawicki)
- [Proxy](src/main/java/cz/tomucha/gae/proxy/Proxy.java): proxy implementation
- [ProxyServlet](src/main/java/cz/tomucha/gae/proxy/ProxyServlet.java): example servlet

Install
-------

Put on your pom.xml:

	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	
	<dependency>
	    <groupId>com.github.rafaeluchoa</groupId>
	    <artifactId>gae-java-proxy</artifactId>
	    <version>-SNAPSHOT</version>
	</dependency>
	
Include in your web.xml:
	
	<servlet>
		<servlet-name>app1</servlet-name>
		<servlet-class>cz.tomucha.gae.proxy.ProxyServlet</servlet-class>
		<init-param>
			<param-name>target</param-name>
			<param-value>http://server2/app1</param-value>
		</init-param>
	</servlet>
	<servlet-mapping>
		<servlet-name>app1</servlet-name>
		<url-pattern>/app1/*</url-pattern>
	</servlet-mapping>

Story
-----

Most of our mobile apps use App Engine as a backend service. One of our iOS contractors submitted one the apps to Apple's
approval, but it was connected to the test server (#facepalm). Unfortunately nobody noticed this tragic error until it was out.

With no other options left, I created this proxy and saved our bottocks. And one day, it may save yours.

Usage
-----
Use it to tunnel traffic from one of your App Engine web applications to another.
It might also help you when migrating from AppEngine to another platform.
It will 100% work on stateless API calls. Replacing whole website might be tricky, unless your website uses only relative links.

TODO
----
- automated testing
- test against Cloud Endpoints
