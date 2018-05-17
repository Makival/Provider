package com.korotkevich.provider.controller.session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Listener fixates creation and destruction of the sessions (HttpSession)
 * @author Korotkevich Kirill 2018-05-16
 *
 */
@WebListener
public class SessionStateListener implements HttpSessionListener {
	private static Logger logger = LogManager.getLogger();
	private SessionPool sessionPool;

	/**
	 * Basic constructor
	 */
	public SessionStateListener() {
		this.sessionPool = SessionPool.getInstance();
	}

	/**
	 * Fixates session creation event
	 */
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		logger.log(Level.INFO, "New session is created");
	}

	/**
	 * Fixates session destruction event and removes session from the session pool object
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		sessionPool.removeSession(session);
		logger.log(Level.INFO, "Session destroyed");	
	}
}
