package org.palestyn.container;

import java.time.Duration;
import java.time.Instant;
import java.util.ServiceLoader;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bootstrap {

	private PalestynContainer.Properties containerProperties=null;

	final static Logger logger = LoggerFactory.getLogger(Bootstrap.class);
	
	public static void main(String[] args) {
		long now = System.currentTimeMillis();
		new Bootstrap(false).boot();
		long millis = Duration.between(Instant.ofEpochMilli(now), Instant.now()).toMillis();

		logger.info("~~~ Startup time {} seconds", millis / 1000D);
	}

	public Bootstrap() {
	}
	
	public Bootstrap(boolean cdi) {
		this.containerProperties=new PalestynContainer.Properties();		
	}
	
	
	public void boot() {
		ServiceLoader.load(PalestynContainer.class).iterator().next().start(Bootstrap.class.getClassLoader(), containerProperties);
	}
	
	// as soon as cdi container context is initialized
	public void onContextIntialized(@Observes @Initialized(ApplicationScoped.class) Object event) {
		// additional bootstrap stuff required to continue in CDI environment
		logger.info("Resumg cdi enviroment initialization ..");
	}
}
