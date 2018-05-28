package com.korotkevich.provider.logic;

import java.util.List;
import java.util.Map;

import com.korotkevich.provider.entity.ServicePlan;
import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.entity.criteria.Criteria;
import com.korotkevich.provider.entity.criteria.SearchCriteria;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.exception.RepositoryException;
import com.korotkevich.provider.repository.impl.ServicePlanRepository;
import com.korotkevich.provider.specification.SqlSpecification;
import com.korotkevich.provider.specification.impl.AddUserServicePlanSqlSpecification;
import com.korotkevich.provider.specification.impl.FindServicePlanByIdSqlSpecification;
import com.korotkevich.provider.specification.impl.FindServicePlanByUserIdSqlSpecification;
import com.korotkevich.provider.specification.impl.FindServicePlanListSqlSpecification;
import com.korotkevich.provider.specification.impl.UpdateServicePlanSqlSpecification;
import com.korotkevich.provider.specification.impl.UpdateUserServicePlanSqlSpecification;

/**
 * Class interacts with repository layer(service plan data)
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public class ServicePlanLogic {
	
	/**
	 * Searches service plan by user id
	 * @param user incoming user object
	 * @return discovered service plan object
	 * @throws LogicException if problems occured while searching service plan
	 */
	public ServicePlan findServicePlanByUserId(User user) throws LogicException {
		ServicePlan currentServicePlan = null;
		int servicePlanIndex = 0;

		FindServicePlanByUserIdSqlSpecification specification = new FindServicePlanByUserIdSqlSpecification(user);
		List<ServicePlan> servicePlanList;
		try {
			ServicePlanRepository repository = new ServicePlanRepository();
			servicePlanList = repository.query(specification);
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}

		if (!servicePlanList.isEmpty()) {
			currentServicePlan = servicePlanList.get(servicePlanIndex);
		}

		return currentServicePlan;

	}
	
	/**
	 * Searches service plan by id
	 * @param servicePlan incoming service plan object
	 * @return discovered service plan object
	 * @throws LogicException if problems occured while searching service plan by id
	 */
	public ServicePlan checkServicePlanById(ServicePlan servicePlan) throws LogicException {
		ServicePlan currentServicePlan = null;
		int servicePlanIndex = 0;

		FindServicePlanByIdSqlSpecification specification = new FindServicePlanByIdSqlSpecification(servicePlan);
		List<ServicePlan> servicePlanList;
		try {
			ServicePlanRepository repository = new ServicePlanRepository();
			servicePlanList = repository.query(specification);
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}

		if (!servicePlanList.isEmpty()) {
			currentServicePlan = servicePlanList.get(servicePlanIndex);
		}

		return currentServicePlan;

	}
	
	/**
	 * Searches service plans by criteria
	 * @param parametersMap incoming pagination parameters map
	 * @param servicePlanCriteria incoming search criteria object
	 * @return list of discovered service plans
	 * @throws LogicException if problems occured while searching service plan by criteria
	 */
	public List<ServicePlan> findServicePlansByCriteria(Map<String, Object> parametersMap, Criteria<SearchCriteria.ServicePlanCriteria> servicePlanCriteria) throws LogicException {

		FindServicePlanListSqlSpecification specification = new FindServicePlanListSqlSpecification(parametersMap, servicePlanCriteria);
		List<ServicePlan> servicePlanList;
		try {
			ServicePlanRepository repository = new ServicePlanRepository();
			servicePlanList = repository.query(specification);
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}

		return servicePlanList;

	}
	
	/**
	 * Replase users service plan
	 * @param user incoming user object
	 * @param servicePlan incoming service plan object
	 * @return result of the operation (boolean)
	 * @throws LogicException if problems occured while changing service plan
	 */
	public boolean changeUserServicePlan(User user, ServicePlan servicePlan) throws LogicException{
		ServicePlanRepository repository;
		
		SqlSpecification [] specifications = {new UpdateUserServicePlanSqlSpecification(user), new AddUserServicePlanSqlSpecification(user, servicePlan)};
		boolean isServicePlanChosen = false;
		try {
			repository = new ServicePlanRepository();
			repository.update(specifications);
			isServicePlanChosen = true;
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}
		return isServicePlanChosen;
		
	}
	
	/**
	 * Adds new service plan
	 * @param servicePlan incoming service plan object
	 * @return result of the operation (boolean)
	 * @throws LogicException if problems occured while adding service plan
	 */
	public boolean addServicePlan(ServicePlan servicePlan) throws LogicException {
		ServicePlanRepository repository;
		boolean isSpAdded;
		try {
			repository = new ServicePlanRepository();
			repository.add(servicePlan);
			isSpAdded = true;
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}
		return isSpAdded;
	}
	
	/**
	 * Updates service plan
	 * @param servicePlan incoming service plan object
	 * @return result of the operation (boolean)
	 * @throws LogicException if problems occured while updating service plan
	 */	 
	public boolean updateServicePlan(ServicePlan servicePlan) throws LogicException{
		ServicePlanRepository repository;
		UpdateServicePlanSqlSpecification specification = new UpdateServicePlanSqlSpecification(servicePlan);
		boolean isServicePlanUpdated;
		try {
			repository = new ServicePlanRepository();
			repository.update(specification);
			isServicePlanUpdated = true;
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}
		return isServicePlanUpdated;
		
	}
	
	/**
	 * Removes service plan
	 * @param servicePlan incoming service plan object
	 * @return result of the operation (boolean)
	 * @throws LogicException if problems occured while changing service plan
	 */
	public boolean removeServicePlan(ServicePlan servicePlan)  throws LogicException {
		ServicePlanRepository repository;
		boolean isServicePlanRemoved;
		try {
			repository = new ServicePlanRepository();
			repository.remove(servicePlan);
			isServicePlanRemoved = true;
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}
		return isServicePlanRemoved;
	}
}
