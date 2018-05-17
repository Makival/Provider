package com.korotkevich.provider.controller.session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.korotkevich.provider.command.UserParameter;
import com.korotkevich.provider.entity.User;

/**
 * Contains session map (User with his session)
 * provides methods for actualization of the session map
 * @author Korotkevich Kirill 2018-05-16
 *
 */
public class SessionPool {
	private static Logger logger = LogManager.getLogger();
	private static SessionPool instance;
	private static AtomicBoolean isPoolInitialized = new AtomicBoolean(false);
	private static ReentrantLock lock = new ReentrantLock();
	private final ConcurrentHashMap<Integer, HttpSession> sessionMap;
	
	/**
	 * Basic constructor
	 */
	private SessionPool() {
		sessionMap  = new ConcurrentHashMap<Integer, HttpSession>();
	}
	
	/**
	 * Creates new instance of session pool if it is not created.
	 * @return instance of the session pool
	 */
	public static SessionPool getInstance() {
		if (!isPoolInitialized.get()) {
			lock.lock();

			try {
				if (instance == null) {
					instance = new SessionPool();
					isPoolInitialized.set(true);
				}
			} finally {
				lock.unlock();
			}
		}

		return instance;
	}
	
	/**
	 * Adds new user id - session pair to the map
	 * @param user incoming User object
	 * @param session incoming HttpSession object
	 * @return result of the operation
	 */
	public boolean addUserSession(User user, HttpSession session) {
		boolean isUserSessionAdded;
		if (!sessionMap.containsKey(user.getId())) {
			isUserSessionAdded = true;
			session.setAttribute(UserParameter.USER.getParameterName(), user);
			sessionMap.put(user.getId(), session);
		} else {
			isUserSessionAdded = false;
			logger.log(Level.WARN, "User " + user.getLogin() + " is already logged!");
		}
		return isUserSessionAdded;
	}

	/**
	 * Invalidates HttpSession object ands removes pair user id - session from the map
	 * @param session incoming HttpSession object
	 */
	public void invalidateUserSession(HttpSession session) {
		User user = (User) session.getAttribute(UserParameter.USER.getParameterName()); 	
		sessionMap.remove(user.getId());
		session.invalidate();
	}
	
	/**
	 * Invalidates HttpSession object ands removes pair user id - session from the map
	 * @param user incoming User object
	 */
	public void invalidateUserSession(User user) {
		HttpSession session = sessionMap.remove(user.getId());
		if (session != null) {
			session.invalidate();
		} else {
			logger.log(Level.WARN, "User with ID " + user.getId() + " session wasn't found!");
		}
			
	}

	/**
	 * Removes session from the map( if map contains this session)
	 * @param session incoming HttpSession object
	 */
	public void removeSession(HttpSession session) {
		if (sessionMap.containsValue(session)) {
			User user = (User) session.getAttribute(UserParameter.USER.getParameterName());
			sessionMap.remove(user.getId());
		}
	}
}
