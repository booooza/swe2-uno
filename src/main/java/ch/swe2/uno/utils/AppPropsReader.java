package ch.swe2.uno.utils;

import ch.swe2.uno.business.server.Server;
import ch.swe2.uno.presentation.network.client.ClientRequestListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class AppPropsReader {
	private static Logger logger = LoggerFactory.getLogger(ClientRequestListenerThread.class);

	private static Properties appProps;

	public AppPropsReader() {
		AppPropsReader.init();
	}

	private static void init() {
		try {
			String rootPath = Objects.requireNonNull(Server.class.getClassLoader().getResource("")).getPath();
			String appConfigPath = rootPath + "application.properties";

			appProps = new Properties();
			try(FileInputStream fileInputStream = new FileInputStream(appConfigPath)){
				appProps.load(fileInputStream);
			}
		} catch (IOException ioEx) {
			logger.error(String.format("Error reading int value. %s", ioEx.getMessage()));
		}
	}

	public static int readIntValueFromAppPropsBy(String propertyName) {
		try {
			if (appProps == null) {
				init();
			}
			if (appProps != null) {
				return Integer.parseInt(appProps.getProperty(propertyName));
			} else {
				return 0;
			}
		} catch (Exception e) {
			logger.error(String.format("Error loading appProps. %s", e.getMessage()), e);
		}
		return 0;
	}

	public static String readStringValueFromAppPropsBy(String propertyName) {
		try {
			if (appProps == null) {
				init();
			}
			if (appProps != null) {
				return appProps.getProperty(propertyName);
			} else {
				return "";
			}
		} catch (NullPointerException npE) {
			logger.error(String.format("Root PATH corrupt. %s", npE.getMessage()));
		}
		return "";
	}
}
