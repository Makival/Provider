package com.korotkevich.provider.logic;

import java.util.List;

import com.korotkevich.provider.entity.ClientAccount;
import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.exception.RepositoryException;
import com.korotkevich.provider.repository.impl.ClientAccountRepository;
import com.korotkevich.provider.specification.impl.FindClientAccountByUserIdSqlSpecification;
import com.korotkevich.provider.specification.impl.UpdateUserAccountSqlSpecification;
import com.korotkevich.provider.specification.impl.UpdateUserBalanceSqlSpecification;

/**
 * Class interacts with repository layer(user data)
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public class ClientAccountLogic {

	/**
	 * Records arrival of funds to the  clients account
	 * @param account incoming client account object
	 * @return result of the operation (boolean)
	 * @throws LogicException if problems occured with updating client account balance
	 */
	public boolean refillBalance(ClientAccount account)  throws LogicException {
		ClientAccountRepository repository;
		UpdateUserBalanceSqlSpecification specification = new UpdateUserBalanceSqlSpecification(account);
		boolean isBalanceRefilled;
		try {
			repository = new ClientAccountRepository();
			repository.update(specification);
			isBalanceRefilled = true;
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}
		return isBalanceRefilled;
		
	}
	
	/**
	 * Records changes of the client account 
	 * @param account incoming client account object
	 * @return result of the operation (boolean)
	 * @throws LogicException if problems occured with updating client account
	 */
	public boolean updateUserAccount(ClientAccount account)  throws LogicException {
		ClientAccountRepository repository;
		UpdateUserAccountSqlSpecification specification = new UpdateUserAccountSqlSpecification(account);
		boolean isAccountUpdated;
		try {
			repository = new ClientAccountRepository();
			repository.update(specification);
			isAccountUpdated = true;
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}
		return isAccountUpdated;
		
	}
	
	/**
	 * Searches client account by user id
	 * @param user incoming user object which account is searching
	 * @return discovered client account object
	 * @throws LogicException if problems occured while searching client account
	 */
	public ClientAccount findAccountByUserId(User user) throws LogicException {
		ClientAccount currentAccount = null;

		FindClientAccountByUserIdSqlSpecification specification = new FindClientAccountByUserIdSqlSpecification(user);
		ClientAccountRepository repository;
		try {
			repository = new ClientAccountRepository();
			currentAccount = findCurrentAccount(repository, specification, user);
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}
			
		return currentAccount;

	}
	
	/**
	 * Searches clients account and adds a new one if there are no account exists
	 * @param repository ClientAccountRepository
	 * @param specification FindClientAccountByUserIdSqlSpecification
	 * @param user incoming user object which account is searching
	 * @return discovered client account object
	 * @throws RepositoryException if problems occured while searching client account
	 */
	private ClientAccount findCurrentAccount(ClientAccountRepository repository,
			FindClientAccountByUserIdSqlSpecification specification, User user) throws RepositoryException {
		int userIndex = 0;
		ClientAccount currentAccount = null;
		List<ClientAccount> accountList;
		accountList = repository.query(specification);

		if (!accountList.isEmpty()) {
			currentAccount = accountList.get(userIndex);
		} else {
			addClientAccount(user);
			accountList = repository.query(specification);
			if (!accountList.isEmpty()) {
				currentAccount = accountList.get(userIndex);
			}
		}
		return currentAccount;

	}
	
	/**
	 * Adds new client account according to user
	 * @param user incoming user object which account is adding
	 * @throws RepositoryException if problems occured while adding client account
	 */
	private void addClientAccount(User user) throws RepositoryException {
		ClientAccountRepository repository;
		ClientAccount account = new ClientAccount(user.getId());
		repository = new ClientAccountRepository();
		repository.add(account);
	}

	
}
