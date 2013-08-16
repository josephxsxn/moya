package org.moya.core.memcached;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.security.SslSocketConnector;
import org.mortbay.thread.QueuedThreadPool;

public class StartJettyTest {
	
	private static final Log LOG = LogFactory.getLog(StartJettyTest.class);
	
	public StartJettyTest(){
		super();
	}

	public static void main(String[] args) {
		final Server server = new Server(8666);

		LOG.info("Starting Jetty");
		Handler handle = new testHandle();
		server.addHandler(handle);
		try {
			server.start();
			LOG.info("Jetty started");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.info(e.getMessage());
		}

	}

	public static class testHandle extends AbstractHandler {

		public void handle(String target, HttpServletRequest request,
				HttpServletResponse response, int dispatch) throws IOException,
				ServletException {
			LOG.info("Top of Handler");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println("<h1>Hello</h1>");
			((Request) request).setHandled(true);

			LOG.info("Bottom of handler");
		}
	}

}
