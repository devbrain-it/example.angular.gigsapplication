package de.devbrain.gigs.service;

import de.devbrain.gigs.DevBrainServer;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public abstract class RestServerTest {
	
	protected static final int PORT = 4300;
	
	private          DevBrainServer main;
	private volatile boolean        mainOK;
	
	@Before
	public void startServer() throws Exception {
		ExecutorService executors = Executors.newSingleThreadExecutor();
		executors.submit(() -> {
			try {
				main = new DevBrainServer(PORT);
			} catch (IOException | SQLException | ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
			try {
				main.start(() -> {
					mainOK = true;
				});
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		awaitServerOK();
	}
	
	protected void awaitServerOK() throws InterruptedException {
		while (!mainOK) {
			Thread.sleep(2000);
		}
	}
	
	@After
	public void endServer() throws Exception {
		main.stop();
	}
}
