package com.korotkevich.provider.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.korotkevich.provider.command.Command;
import com.korotkevich.provider.command.CommandProvider;
import com.korotkevich.provider.controller.Router.RouteType;
import com.korotkevich.provider.controller.session.SessionPool;
import com.korotkevich.provider.exception.ConnectionPoolException;
import com.korotkevich.provider.pool.ConnectionPool;

@WebServlet(urlPatterns = {"/FrontController"})
@MultipartConfig(location = ""
        , fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class FrontController extends HttpServlet {
	private static Logger logger = LogManager.getLogger();
	private static final long serialVersionUID = 1L;
	private static final String PARAM_COMMAND = "command"; 
       
    public FrontController() {
        super();
    }
    
    @Override
	public void init(ServletConfig config) throws ServletException {
		try {
			ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			logger.log(Level.ERROR, "Unable to create a connection pool" + e);
		}
		SessionPool.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		String commandName = request.getParameter(PARAM_COMMAND);

		Command command = CommandProvider.defineCommand(commandName);
		Router router = command.execute(request);
		
		if (router.getRoute() == RouteType.FORWARD) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(router.getJspPath());
			dispatcher.forward(request, response);
		}else {
			response.sendRedirect(router.getJspPath());
		}
	
	}
	
	@Override
	public void destroy(){
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			pool.destroy();
		} catch (ConnectionPoolException e) {
			logger.log(Level.ERROR, "Unable to destroy a connection pool" + e);
		}
	}

}
