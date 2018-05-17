package com.korotkevich.provider.pool.propertyloader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.korotkevich.provider.exception.ConnectionPoolException;

/**
 * Class loads connection pool properties from properties file
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public class PoolPropertyLoader {
	private static Logger logger = LogManager.getLogger();
	private final static String DEFAULT_PROPERTIES_PATH = "/resources/pool.properties";

	/**
	 * Finds and uploads connection pool properties
	 * @return properties object
	 * @throws ConnectionPoolException if problems occured with interacting with properties file
	 */
	public Properties fillInProperties() throws ConnectionPoolException {
		InputStream inputStream = PoolPropertyLoader.class.getResourceAsStream(DEFAULT_PROPERTIES_PATH);
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			if (properties.isEmpty()) {
				throw new ConnectionPoolException("Unable to load pool properties", e);
			} else {
				logger.log(Level.WARN, "Unable to close input stream " + e);
			}

		}

		return properties;

	}
}
