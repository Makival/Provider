package com.korotkevich.provider.pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.korotkevich.provider.exception.ConnectionPoolException;
import com.korotkevich.provider.pool.propertyloader.PoolPropertyLoader;

/**
 * Class stores and provides connections to data base
 * @author Korotkevich Kirill 2018-05-28
 *
 */
public class ConnectionPool {
	private static Logger logger = LogManager.getLogger();
	private static ConnectionPool instance;
	private static ReentrantLock lock = new ReentrantLock();
	private final BlockingQueue<WrapperConnection> connections;
	private final static String USERNAME_PROPERTY = "username";
	private final static String PASSWORD_PROPERTY = "password";
	private final static String DATABASE_URL_PROPERTY = "url";
	private final static String DRIVER_NAME_PROPERTY = "driver";
	private final static String CONNECTIONS_MAX_PROPERTY = "connections";
	private final static String TIME_WAIT = "timeWait";
	private static AtomicBoolean isPoolInitialized = new AtomicBoolean(false);
	
	private Properties poolProperties;

	/**
	 * Basic constructor
	 * @throws ConnectionPoolException
	 */
	private ConnectionPool() throws ConnectionPoolException {
		PoolPropertyLoader propertiesLoader = new PoolPropertyLoader();
		poolProperties = propertiesLoader.fillInProperties();
		int connectionsMaxQuantity = Integer.parseInt(poolProperties.getProperty(CONNECTIONS_MAX_PROPERTY));
		connections = new LinkedBlockingQueue<>(connectionsMaxQuantity);
		try {
			Class.forName(poolProperties.getProperty(DRIVER_NAME_PROPERTY));
		} catch (ClassNotFoundException e) {
			throw new ConnectionPoolException("Unable to register driver to data base", e);
		}

		for (int i = 0; i < connectionsMaxQuantity; i++) {
			try {
				Connection connection = DriverManager.getConnection(poolProperties.getProperty(DATABASE_URL_PROPERTY),
						poolProperties.getProperty(USERNAME_PROPERTY), poolProperties.getProperty(PASSWORD_PROPERTY));
				WrapperConnection wrappConnection = new WrapperConnection(connection);
				connections.offer(wrappConnection);
			} catch (SQLException e) {
				logger.log(Level.ERROR, "unable to create connection: " + e);
			}
		}

	}

	/**
	 * Gets instance of the pool or creates it if there are no instance
	 * @return ConnectionPool instance
	 * @throws ConnectionPoolException
	 */
	public static ConnectionPool getInstance() throws ConnectionPoolException {
		if (!isPoolInitialized.get()) {
			lock.lock();

			try {
				if (instance == null) {
					instance = new ConnectionPool();
					isPoolInitialized.set(true);
				}
			} finally {
				lock.unlock();
			}
		}

		return instance;
	}
		

	/**
	 * Gets free connection from the poll
	 * @return WrapperConnection
	 * @throws ConnectionPoolException
	 */
	public WrapperConnection getConnection() throws ConnectionPoolException{
		WrapperConnection connection = null;		
		int timeWaitValue = Integer.parseInt(poolProperties.getProperty(TIME_WAIT));
		
		try {
			connection = connections.poll(timeWaitValue, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new ConnectionPoolException("All connections to data base are in use", e);
		}

		return connection;
	}
		
	
	/**
	 * Returns connection to the pool
	 * @param wrappConnection
	 */
	public void returnConnection(WrapperConnection wrappConnection) {
		if (wrappConnection != null) {
			connections.offer(wrappConnection);
		}
	}

	/**
	 * Prepares destruction of the poll(closes connections, unloads drivers)
	 */
	public void destroy() {
		int connQuantity = Integer.parseInt(poolProperties.getProperty(CONNECTIONS_MAX_PROPERTY));
		int connClosedQuantity = 0;

		while (connClosedQuantity < connQuantity) {
			connClosedQuantity++;
			try {
				WrapperConnection wrapConnection = connections.take();
				wrapConnection.close();
			} catch (SQLException e) {
				logger.log(Level.WARN, "Unable to close connection:" + e);
			} catch (InterruptedException e) {
				logger.log(Level.WARN, "Unable to take connection:" + e);
			}
		}

		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				logger.log(Level.WARN, "Unable to deregister driver:" + e);
			}
		}
	}
}
