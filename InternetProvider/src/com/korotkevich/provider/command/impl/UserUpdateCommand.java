package com.korotkevich.provider.command.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.korotkevich.provider.command.Command;
import com.korotkevich.provider.command.JspAddress;
import com.korotkevich.provider.command.RedirectAddress;
import com.korotkevich.provider.command.UserParameter;
import com.korotkevich.provider.controller.Router;
import com.korotkevich.provider.controller.Router.RouteType;
import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.logic.UserLogic;
import com.korotkevich.provider.validator.IncomingUserDataValidator;

/**
 * Changes data of the chosen user
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class UserUpdateCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private final static String PARAM_PASSWORD_VERIFICATION ="passwordVerification";
	private final static String PARAM_PASSWORD_CURRENT ="currentPassword";
	private final static String ATTR_ERROR_LIST_NAME = "validationErrorList";
	private final static String ATTR_MSG_MAP = "messageMap";
	private final static String USER_NOT_FOUND_ERROR_MSG = "userNotFound";
	private final static String USER_UPDATE_MSG = "userUpdateSuccess";
	private final static String USER_UPDATE_ERROR_MSG = "userUpdateError";
	private final static String ATTR_USER_NO_UPDATE_MSG = "userWithoutUpdate";
	private final static String ATTR_PASS_INCORRECT_MSG = "userPasswordIncorrect";
	private final static String ATTR_PASS_NOT_MATCH_MSG = "userPasswordNotMatch";
	private static final String UPLOAD_DIR = "/avatars";
	private static final String IMAGE_TYPE = "image/";
	private static final String IMAGE_HEADER_NAME = "avatar";
	private static final String EMPTY_STRING = "";
	private static final String DELIMETER = ".";
	private UserLogic userLogic;

	public UserUpdateCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
	}
	
	@Override
	public Router execute(HttpServletRequest request) {
		String loginValue = request.getParameter(UserParameter.LOGIN.getParameterName());
		String passwordValue = request.getParameter(UserParameter.PASSWORD.getParameterName());
		String passVerificationValue = request.getParameter(PARAM_PASSWORD_VERIFICATION);
		String passCurrentValue = request.getParameter(PARAM_PASSWORD_CURRENT);
		String nameValue = request.getParameter(UserParameter.NAME.getParameterName());
		String surnameValue = request.getParameter(UserParameter.SURNAME.getParameterName());
		String emailValue = request.getParameter(UserParameter.EMAIL.getParameterName());
		String birthDateValue = request.getParameter(UserParameter.BIRTHDATE.getParameterName());
		User userDB;
		User currentUser = (User) request.getSession(true).getAttribute(UserParameter.USER.getParameterName());
		boolean isUserUpdated = false;
		Router router = new Router(JspAddress.USER_UPDATE.getPath());
		List<String> validationErrorList = new ArrayList<>();

		logger.log(Level.INFO, "name value: " + nameValue);
		
		User incomingUser = new User(currentUser.getId(), loginValue, passCurrentValue.toCharArray(), nameValue,
				surnameValue, emailValue, birthDateValue);

		boolean isNewPassValidated = false;
		boolean isUserValidated;
		boolean isPassChanging;
		
		if (!passVerificationValue.equals(passwordValue)) {
			validationErrorList.add(ATTR_PASS_NOT_MATCH_MSG);
			request.setAttribute(ATTR_ERROR_LIST_NAME, validationErrorList);
			return router;
		}

		if (passwordValue.isEmpty() || passVerificationValue.isEmpty() || passCurrentValue.isEmpty()) {
			isUserValidated = IncomingUserDataValidator.validateUserDataMin(incomingUser, validationErrorList);
			isPassChanging = false;
		} else {
			isUserValidated = IncomingUserDataValidator.validateUserData(incomingUser, validationErrorList);
			isNewPassValidated = IncomingUserDataValidator.validatePassword(passwordValue, validationErrorList);
			isPassChanging = true;
		}

		if (!isUserValidated || (isPassChanging &&!isNewPassValidated)) {
			request.setAttribute(ATTR_ERROR_LIST_NAME, validationErrorList);
			return router;
		}

		try {
			userDB = isPassChanging ? userLogic.checkUserByIdPassword(incomingUser)
					: userLogic.checkUserById(incomingUser);
		} catch (LogicException e) {
			userDB = null;
			logger.log(Level.ERROR, "Errors occured while searching user by id:" + e);
		}

		if (userDB != null) {

			if (userDB.equals(incomingUser)) {
				request.setAttribute(ATTR_USER_NO_UPDATE_MSG, true);
				return router;
			}

			try {
				if (isPassChanging) {
					incomingUser.setPassword(passVerificationValue.toCharArray());
				}
				String avatarPath = uploadAvatar(currentUser, request);
				if (avatarPath.isEmpty()) {
					avatarPath = userDB.getAvatarPath();
				}
				incomingUser.setAvatarPath(avatarPath);
				isUserUpdated = userLogic.updateUser(incomingUser);
			} catch (LogicException e) {
				logger.log(Level.ERROR, "Errors occured while updating user:" + e);
			}

			if (isUserUpdated) {
				router.setJspPath(RedirectAddress.MAIN_REDIRECT.getPath());
				router.setRoute(RouteType.REDIRECT);
				HttpSession session = request.getSession(true);
				updateCurrentUser(currentUser, incomingUser);

				@SuppressWarnings("unchecked")
				HashMap<String, Boolean> messageMap = (HashMap<String, Boolean>) session.getAttribute(ATTR_MSG_MAP);
				messageMap.put(USER_UPDATE_MSG, true);

			} else {
				router.setJspPath(JspAddress.ERROR.getPath());
				request.setAttribute(USER_UPDATE_ERROR_MSG, true);
			}

		} else {
			if (isPassChanging) {
				request.setAttribute(ATTR_PASS_INCORRECT_MSG, true);
				return router;
			} else {
				router.setJspPath(JspAddress.LOGIN.getPath());
				request.setAttribute(USER_NOT_FOUND_ERROR_MSG, true);
			}

		}

		return router;
	}

	public UserLogic getUserLogic() {
		return userLogic;
	}

	private void updateCurrentUser(User currenUser, User updatedUser) {
		String updatedLogin = updatedUser.getLogin();
		String updatedName = updatedUser.getName();
		String updatedSurname = updatedUser.getSurname();
		String updatedEmail = updatedUser.getEmail();
		String updatedBirthDate = updatedUser.getBirthDate();
		String updatedAvatarPath = updatedUser.getAvatarPath();

		if (!currenUser.getLogin().equals(updatedLogin)) {
			currenUser.setLogin(updatedLogin);
		}

		if (!currenUser.getName().equals(updatedName)) {
			currenUser.setName(updatedName);
		}

		if (!currenUser.getSurname().equals(updatedSurname)) {
			currenUser.setSurname(updatedSurname);
		}

		if (!currenUser.getEmail().equals(updatedEmail)) {
			currenUser.setEmail(updatedEmail);
		}

		if (!currenUser.getBirthDate().equals(updatedBirthDate)) {
			currenUser.setBirthDate(updatedBirthDate);
		}

		if (updatedAvatarPath != null && !currenUser.getAvatarPath().equals(updatedAvatarPath)) {
			currenUser.setAvatarPath(updatedAvatarPath);
		}
	}
	
	private String uploadAvatar(User currenUser, HttpServletRequest request) {
		String appPath = request.getServletContext().getRealPath("");
		String savePath = appPath + File.separator + UPLOAD_DIR;
		String avatarImageName = "";

		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}

		try {
			for (Part part : request.getParts()) {
				String fileName = part.getSubmittedFileName();
				if (fileName != null && !fileName.isEmpty()) {
					fileName = new File(fileName).getName();
					avatarImageName = formFileName(currenUser, part.getContentType());
					if (!avatarImageName.isEmpty()) {
						String avatarImageFullPath = savePath + File.separator + avatarImageName; 
						part.write(avatarImageFullPath);
						logger.log(Level.INFO, "file uploaded " + avatarImageFullPath);	
					}	
				}
			}
		} catch (IOException | ServletException e) {
			logger.log(Level.ERROR, "Errors occured while uploading a file:" + e);
		}
		return avatarImageName;
	}
	
	private String formFileName(User currenUser, String contentTypeFormat) {
		String avatarImageName = "";
		CharSequence type = IMAGE_TYPE;
		if (contentTypeFormat.contains(type)) {
			String fileFormat = contentTypeFormat.replace(type, EMPTY_STRING);
			avatarImageName = IMAGE_HEADER_NAME + currenUser.getId() + DELIMETER + fileFormat;
		}

		return avatarImageName;
	}
	
}
