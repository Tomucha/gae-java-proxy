gae-java-proxy
==============

Simple Java implementation of an HTTP proxy, prepared for deploying to Google App Engine.

I tried to make as simple as it gets. It has no dependencies, except for https://github.com/kevinsawicki/http-request,
which I copied directly into the project.

Classes
-------
- HttpRequest: simple HTTP client based on http://docs.oracle.com/javase/7/docs/api/java/net/HttpURLConnection.html
- Proxy: proxy implementation
- ProxyServlet: example servlet

Install
-------

- simply copy my classes into your project
- to integrate proxy with your web see ProxyServlet
- configure web.xml
- configure appengine-web.xml

And you are good to go.

Story
-----

Most of our mobile apps use App Engine as a backend service. One of our iOS contractors submitted one the apps to Apple's
approval, but it was connected to the test server (#facepalm). Unfortunately nobody noticed this tragic error until it was out.

With no other options left I created this proxy and saved our bottocks. And one day, it may save yours.

Usage
-----
Use it to tunnel traffic from one of your App Engine web applications to another.
It might also help you when migrating from AppEngine to another platform,
