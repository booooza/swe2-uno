package ch.swe2.uno.utils;

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
			String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
			String appConfigPath = rootPath + "application.properties";

			appProps = new Properties();
			appProps.load(new FileInputStream(appConfigPath));
		} catch (IOException ioEx) {
			logger.error(String.format("Error reading int value. %s", ioEx.getMessage()));
		}
	}

	public static int readIntValueFromAppPropsBy(String propertyName) {
		try {
			if (appProps == null) {
				init();
			}
			return Integer.parseInt(appProps.getProperty(propertyName));
		} catch (NullPointerException npE) {
			logger.error(String.format("Root PATH corrupt. %s", npE.getMessage()));
		}
		return 1234;
	}

	public static String readStringValueFromAppPropsBy(String propertyName) {
		try {
			if (appProps == null) {
				init();
			}
			return appProps.getProperty(propertyName);
		} catch (NullPointerException npE) {
			logger.error(String.format("Root PATH corrupt. %s", npE.getMessage()));
		}
		return "Not found";
	}
}
