package de.devbrain.gigs;

import de.devbrain.gigs.service.CORSFilter;
import de.devbrain.gigs.service.GigService;
import de.devbrain.gigs.service.UnitService;
import de.devbrain.gigs.service.UserService;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class DevBrainServer {
	
	public static final int FALLBACK_PORT = 4201;
	private final Server   server;
	
	public DevBrainServer(int port) throws IOException, SQLException, ClassNotFoundException {
		ResourceConfig config = new ResourceConfig();
		config.packages("de.devbrain.gigs");
		
		ServletContainer servletContainer = new ServletContainer(config);
		ServletHolder    servlet          = new ServletHolder(servletContainer);
		
		server = new Server(port <= 1
		                    ? FALLBACK_PORT
		                    : port);
		
		ServletContextHandler context = new ServletContextHandler(server, "/*");
		// http://www.codingpedia.org/ama/how-to-add-cors-support-on-the-server-side-in-java-with-jersey/
		// CORSFilter
		config.register(CORSFilter.class);
		config.register(UserService.class);
		config.register(GigService.class);
		config.register(UnitService.class);
		context.addServlet(servlet, "/*");
	}
	
	public static void main(String[] args) throws Exception {
		DevBrainServer serverWrapper = new DevBrainServer(args.length == 0
		                                                  ? FALLBACK_PORT
		                                                  : Integer.parseInt(args[0]));
		serverWrapper.start();
	}
	
	private void start() throws Exception {
		start(null);
	}
	
	public void start(Runnable callback) throws Exception {
		try {
			if(callback != null) {
				server.addLifeCycleListener(new AbstractLifeCycle.AbstractLifeCycleListener() {
					@Override
					public void lifeCycleStarted(LifeCycle event) {
						callback.run();
					}
				});
			}
			server.start();
			server.join();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.out.println("SHUTDOWN");
			stop();
		}
	}
	
	public void stop() throws Exception {
		if(!server.isStopped()) {
			server.stop();
		}
		server.destroy();
	}
	
	private static final class SessionHandler extends AbstractHandler {
		@Override
		public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			PrintWriter out = response.getWriter();
			response.setContentType(MediaType.TEXT_PLAIN);
			
			try {
				HttpSession session = request.getSession();
				if(session.isNew()) {
					out.printf("New Session: %s%n", session.getId());
				} else {
					out.printf("Old Session: %s%n", session.getId());
				}
			} catch (IllegalStateException ex) {
				out.println("Exception!" + ex);
				ex.printStackTrace(out);
				throw ex;
			}
			out.close();
		}
	}
}