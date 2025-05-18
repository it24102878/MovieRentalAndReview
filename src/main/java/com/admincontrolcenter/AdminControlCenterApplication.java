package com.admincontrolcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.context.ConfigurableApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.BindException;

@SpringBootApplication
public class AdminControlCenterApplication {
	private static final Logger logger = LoggerFactory.getLogger(AdminControlCenterApplication.class);
	private static final int DEFAULT_PORT = 8081;
	private static final int FALLBACK_PORT = 0; // 0 means random available port

	public static void main(String[] args) {
		try {
			// Try to start with the configured port
			ConfigurableApplicationContext context = SpringApplication.run(AdminControlCenterApplication.class, args);
			logger.info("Application started successfully");
		} catch (WebServerException e) {
			if (e.getCause() instanceof BindException) {
				logger.warn("Port {} is already in use, trying with fallback port", DEFAULT_PORT);

				// Set system property to use fallback port
				System.setProperty("server.port", String.valueOf(FALLBACK_PORT));

				// Restart application with new port
				ConfigurableApplicationContext context = SpringApplication.run(AdminControlCenterApplication.class, args);
				logger.info("Application started successfully on fallback port");
			} else {
				// If it's not a port binding issue, rethrow the exception
				throw e;
			}
		}
	}
}
