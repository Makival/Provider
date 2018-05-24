package com.korotkevich.provider.command;

import com.korotkevich.provider.command.impl.ChangeLocalizationCommand;
import com.korotkevich.provider.command.impl.LoginCommand;
import com.korotkevich.provider.command.impl.LogoutCommand;
import com.korotkevich.provider.command.impl.UserRegistrationCommand;
import com.korotkevich.provider.command.impl.UserUpdateCommand;
import com.korotkevich.provider.command.impl.UserBlockCommand;
import com.korotkevich.provider.command.impl.RefillBalanceCommand;
import com.korotkevich.provider.command.impl.GoToRegPageCommand;
import com.korotkevich.provider.command.impl.GoToUserUpdatePageCommand;
import com.korotkevich.provider.command.impl.GoToMainPageCommand;
import com.korotkevich.provider.command.impl.GoToLoginPageCommand;
import com.korotkevich.provider.command.impl.FindAllUsersCommand;
import com.korotkevich.provider.command.impl.FindServicePlansByCriteriaCommand;
import com.korotkevich.provider.command.impl.ChangeUserServicePlanCommand;
import com.korotkevich.provider.command.impl.ServicePlanAddCommand;
import com.korotkevich.provider.command.impl.ServicePlanUpdateCommand;
import com.korotkevich.provider.command.impl.ServicePlanRemoveCommand;
import com.korotkevich.provider.command.impl.GoToServicePlanListPageCommand;
import com.korotkevich.provider.command.impl.GoToErrorPageCommand;
import com.korotkevich.provider.command.impl.PromoAddCommand;
import com.korotkevich.provider.command.impl.PromoRemoveCommand;
import com.korotkevich.provider.logic.ClientAccountLogic;
import com.korotkevich.provider.logic.PromoLogic;
import com.korotkevich.provider.logic.ServicePlanLogic;
import com.korotkevich.provider.logic.UserLogic;

/**
 * Contains definition of the commands for servlet
 * @author Korotkevich Kirill 2018-05-11
 *
 */
public enum CommandType {
	LOG_IN(new LoginCommand(new UserLogic(), new ServicePlanLogic(), new ClientAccountLogic()), 0), 
	LOG_OUT(new LogoutCommand(), 1), 
	USER_REGISTRATION(new UserRegistrationCommand(new UserLogic()), 2),
	USER_UPDATE(new UserUpdateCommand(new UserLogic()), 1),
	USER_BLOCK(new UserBlockCommand(new UserLogic()), 2),
	REFILL_BALANCE(new RefillBalanceCommand(new ClientAccountLogic()),1),
	CHANGE_LOCALIZATION(new ChangeLocalizationCommand(), 0),
	GO_TO_REG_PAGE(new GoToRegPageCommand(), 2),
	GO_TO_USER_UPDATE_PAGE(new GoToUserUpdatePageCommand(), 1),
	GO_TO_MAIN_PAGE(new GoToMainPageCommand(new UserLogic(), new ServicePlanLogic(), new ClientAccountLogic()), 1),
	GO_TO_LOGIN_PAGE(new GoToLoginPageCommand(), 0),
	FIND_USERS(new FindAllUsersCommand(new UserLogic()), 2),
	FIND_SERVICE_PLANS(new FindServicePlansByCriteriaCommand(new ServicePlanLogic()), 1),
	CHOOSE_SERVICE_PLAN(new ChangeUserServicePlanCommand(new ServicePlanLogic(), new ClientAccountLogic()), 1),
	SERVICE_PLAN_ADD(new ServicePlanAddCommand( new ServicePlanLogic()), 2),
	SERVICE_PLAN_REMOVE(new ServicePlanRemoveCommand( new ServicePlanLogic()), 2),
	SERVICE_PLAN_UPDATE(new ServicePlanUpdateCommand( new ServicePlanLogic()), 2),
	GO_TO_SERVICE_PLAN_LIST_PAGE(new GoToServicePlanListPageCommand( new ServicePlanLogic()), 1),
	GO_TO_ERROR_PAGE(new GoToErrorPageCommand(), 0),	
	PROMO_ADD(new PromoAddCommand( new PromoLogic(), new ServicePlanLogic()), 2),
	PROMO_REMOVE(new PromoRemoveCommand( new PromoLogic()), 2);

	private Command command;
	private int restrictionLevel;

	/**
	 * Constructor with command, restrictionLevel
	 * @param command Command object
	 * @param restrictionLevel level needed for command execution (int)
	 */
	CommandType(Command command, int restrictionLevel) {
		this.command = command;
		this.restrictionLevel = restrictionLevel;
	}

	/**
	 * Get the Command object
	 * @return command Command object
	 */
	public Command getCommand() {
		return command;
	}
	
	/**
	 * Get the restrictionLevel need for access to the command
	 * @return restrictionLevel
	 */
	public int getRestrictionLevel() {
		return restrictionLevel;
	}

}
