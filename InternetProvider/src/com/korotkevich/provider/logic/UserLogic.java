package com.korotkevich.provider.logic;

import java.util.List;
import java.util.Map;

import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.exception.RepositoryException;
import com.korotkevich.provider.repository.impl.UserRepository;
import com.korotkevich.provider.specification.Specification;
import com.korotkevich.provider.specification.impl.FindUserByIdPassSqlSpecification;
import com.korotkevich.provider.specification.impl.FindUserByIdSqlSpecification;
import com.korotkevich.provider.specification.impl.FindUserByLoginPassSqlSpecification;
import com.korotkevich.provider.specification.impl.FindUserByLoginSqlSpecification;
import com.korotkevich.provider.specification.impl.FindUserListSqlSpecification;
import com.korotkevich.provider.specification.impl.UpdateUserFullDataSqlSpecification;
import com.korotkevich.provider.specification.impl.UpdateUserSqlSpecification;
import com.korotkevich.provider.specification.impl.UpdateUserStatusSqlSpecification;

/**
 * Class interacts with repository layer(user data)
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public class UserLogic {

	/**
	 * 
	 * @param user incoming user object
	 * @return discovered user object
	 * @throws LogicException if problems occured while searching user
	 */
	public User findUser(User user) throws LogicException {
		User currentUser = null;
		int userIndex = 0;

		FindUserByLoginPassSqlSpecification specification = new FindUserByLoginPassSqlSpecification(user);
		List<User> userList;
		try {
			UserRepository repository = new UserRepository();
			userList = repository.query(specification);
			eraseUserPassword(user);
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}

		if (!userList.isEmpty()) {
			currentUser = userList.get(userIndex);
		}

		return currentUser;

	}
	
	/**
	 * 
	 * @param user incoming user object
	 * @return discovered user object
	 * @throws LogicException if problems occured while searching user
	 */
	public User checkUserByLogin(User user) throws LogicException {
		User currentUser = null;
		int userIndex = 0;

		FindUserByLoginSqlSpecification specification = new FindUserByLoginSqlSpecification(user);
		List<User> userList;
		try {
			UserRepository repository = new UserRepository();
			userList = repository.query(specification);
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}

		if (!userList.isEmpty()) {
			currentUser = userList.get(userIndex);
		}

		return currentUser;

	}
	
	/**
	 * 
	 * @param user incoming user object
	 * @return discovered user object
	 * @throws LogicException if problems occured while searching user
	 */
	public User checkUserById(User user) throws LogicException {
		User currentUser = null;
		int userIndex = 0;

		FindUserByIdSqlSpecification specification = new FindUserByIdSqlSpecification(user);
		List<User> userList;
		try {
			UserRepository repository = new UserRepository();
			userList = repository.query(specification);
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}

		if (!userList.isEmpty()) {
			currentUser = userList.get(userIndex);
		}

		return currentUser;

	}
	
	public User checkUserByIdPassword(User user)
			throws LogicException {
		User currentUser = null;
		int userIndex = 0;

		FindUserByIdPassSqlSpecification specification = new FindUserByIdPassSqlSpecification(user);
		List<User> userList;
		try {
			UserRepository repository = new UserRepository();
			userList = repository.query(specification);
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}

		if (!userList.isEmpty()) {
			currentUser = userList.get(userIndex);
		}
		eraseUserPassword(user);

		return currentUser;
	}
	
	/**
	 * 
	 * @param parametersMap incoming pagination parameters map
	 * @return discovered list of users
	 * @throws LogicException if problems occured while searching users
	 */
	public List<User> findAllUsers(Map<String, Object> parametersMap) throws LogicException {

		FindUserListSqlSpecification specification = new FindUserListSqlSpecification(parametersMap);
		List<User> userList;
		try {
			UserRepository repository = new UserRepository();
			userList = repository.query(specification);
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}

		return userList;

	}
	
	/**
	 * 
	 * @param user incoming user object
	 * @return result of the operation (boolean)
	 * @throws LogicException if problems occured while adding user
	 */
	public boolean addUser(User user) throws LogicException {
		UserRepository repository;
		boolean isUserAdded;
		try {
			repository = new UserRepository();
			repository.add(user);
			isUserAdded = true;
			eraseUserPassword(user);
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}
		return isUserAdded;
	}
	
	/**
	 * 
	 * @param user incoming user object
	 * @return result of the operation (boolean)
	 * @throws LogicException  if problems occured while updating user
	 */
	public boolean updateUser(User user) throws LogicException{
		UserRepository repository;
		Specification specification = user.getPassword().length > 0 ? new UpdateUserFullDataSqlSpecification(user) : new UpdateUserSqlSpecification(user);
		boolean isUserUpdated;
		try {
			repository = new UserRepository();
			repository.update(specification);
			isUserUpdated = true;
			eraseUserPassword(user);
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}
		return isUserUpdated;
		
	}
	
	/**
	 * 
	 * @param user incoming user object
	 * @return result of the operation (boolean)
	 * @throws LogicException  if problems occured while updating user
	 */
	public boolean updateUserStatus(User user) throws LogicException{
		UserRepository repository;
		UpdateUserStatusSqlSpecification specification = new UpdateUserStatusSqlSpecification(user);
		boolean isUserUpdated;
		try {
			repository = new UserRepository();
			repository.update(specification);
			isUserUpdated = true;
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}
		return isUserUpdated;
		
	}
	
	/**
	 * 
	 * @param user incoming user object
	 * @return result of the operation (boolean)
	 * @throws LogicException if problems occured while updating user
	 */
	public boolean removeUser(User user)  throws LogicException {
		UserRepository repository;
		boolean isUserRemoved;
		try {
			repository = new UserRepository();
			repository.remove(user);
			isUserRemoved = true;
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}
		return isUserRemoved;
	}
	
	/**
	 * Replaces chars in array for '0' char
	 * @param user incoming User object
	 */
	private void eraseUserPassword(User user) {
		char[] password = user.getPassword();
		int passLenght = password.length;
		char zeroChar = '0';

		for (int i = 0; i < passLenght; i++) {
			password[i] = zeroChar;
		}
	}
}
