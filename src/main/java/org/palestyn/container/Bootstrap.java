package org.palestyn.container;

import java.time.Duration;
import java.time.Instant;
import java.util.ServiceLoader;

import org.palestyn.container.PalestynContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bootstrap {

	final String BIND_HOST;
	final int BIND_PORT;

	final String PU_NAME;
	
	private PalestynContainer.Properties containerProperties;

	final static Logger logger = LoggerFactory.getLogger(Bootstrap.class);
	
	public static void main(String[] args) {
		long now = System.currentTimeMillis();
		new Bootstrap().boot();
		long millis = Duration.between(Instant.ofEpochMilli(now), Instant.now()).toMillis();

		logger.info("~~~ Startup time {} seconds", millis / 1000D);
	}

	public Bootstrap() {
		containerProperties=new PalestynContainer.Properties();
		this.PU_NAME = containerProperties.getProperty("persistence.unit.name").get();
		this.BIND_HOST = containerProperties.getProperty("service.host").orElse("localhost");
		this.BIND_PORT = new Integer(containerProperties.getProperty("service.port").orElse("8080"));
	}
	
	public void boot() {
		ServiceLoader.load(PalestynContainer.class).iterator().next().start(Bootstrap.class.getClassLoader(), containerProperties);
	}
}
