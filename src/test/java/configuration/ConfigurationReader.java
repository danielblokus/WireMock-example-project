package configuration;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

public class ConfigurationReader {

	private static final String PATH_TO_CONFIGURATION_FILE = "configuration.properties";

	private Properties properties;

	public ConfigurationReader() {
		readPropertiesFromConfigurationFile();
	}

	private void readPropertiesFromConfigurationFile() {
		properties = new Properties();
		try {
			properties.load(getConfigurationFilePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private BufferedReader getConfigurationFilePath() {
		return new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader()
				.getResourceAsStream(PATH_TO_CONFIGURATION_FILE))));
	}

	public String getBasicAuthName() {
		String path = properties.getProperty("basicAuthUserName");
		if (path != null) {
			return path;
		}
		else {
			throw new RuntimeException("basicAuthUserName not specified in the configuration.properties file");
		}
	}

	public String getBasicAuthPassword() {
		String path = properties.getProperty("basicAuthPassword");
		if (path != null) {
			return path;
		}
		else {
			throw new RuntimeException("basicAuthPassword not specified in the configuration.properties file");
		}
	}
}