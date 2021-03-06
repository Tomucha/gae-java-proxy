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

- simply copy my classes into your project
- to integrate proxy with your project see [ProxyServlet](src/main/java/cz/tomucha/gae/proxy/ProxyServlet.java) example
- configure [web.xml](src/main/webapp/WEB-INF/web.xml)
- configure [appengine-web.xml](src/main/webapp/WEB-INF/appengine-web.xml)

And you are good to go.

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
