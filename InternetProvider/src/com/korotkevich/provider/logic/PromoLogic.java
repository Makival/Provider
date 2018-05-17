package com.korotkevich.provider.logic;

import java.util.List;

import com.korotkevich.provider.entity.Promo;
import com.korotkevich.provider.entity.ServicePlan;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.exception.RepositoryException;
import com.korotkevich.provider.repository.impl.PromoRepository;
import com.korotkevich.provider.specification.impl.FindPromoByIdSqlSpecification;
import com.korotkevich.provider.specification.impl.FindPromoByServicePlanIdSqlSpecification;

/**
 * Class interacts with repository layer(promo action data)
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public class PromoLogic {
	
	/**
	 * Searching promo by id
	 * @param promo promo action object
	 * @return discovered promo object
	 * @throws LogicException if problems occured while searching promo
	 */
	public Promo findPromoById(Promo promo) throws LogicException {
		Promo currentPromo = null;
		int promoIndex = 0;

		FindPromoByIdSqlSpecification specification = new FindPromoByIdSqlSpecification(promo);
		List<Promo> promoList;
		try {
			PromoRepository repository = new PromoRepository();
			promoList = repository.query(specification);
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}

		if (!promoList.isEmpty()) {
			currentPromo = promoList.get(promoIndex);
		}

		return currentPromo;

	}
	
	/**
	 * Adds new promo
	 * @param promo promo action object
	 * @return result of the operation (boolean)
	 * @throws LogicException if problems occured while adding new promo
	 */
	public boolean addPromo(Promo promo) throws LogicException {
		PromoRepository repository;
		boolean isPromoAdded;
		try {
			repository = new PromoRepository();
			repository.add(promo);
			isPromoAdded = true;
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}
		return isPromoAdded;
	}
	
	/**
	 * Searching promo by connected service plan id
	 * @param servicePlan service plan which is connected with promo(under the promo action)
	 * @return discovered promo object
	 * @throws LogicException if problems occured while searching promo
	 */
	public Promo checkPromoByServicePlanId(ServicePlan servicePlan) throws LogicException {
		Promo currentPromo = null;
		int promoIndex = 0;

		FindPromoByServicePlanIdSqlSpecification specification = new FindPromoByServicePlanIdSqlSpecification(servicePlan);
		List<Promo> promoList;
		try {
			PromoRepository repository = new PromoRepository();
			promoList = repository.query(specification);
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}

		if (!promoList.isEmpty()) {
			currentPromo = promoList.get(promoIndex);
		}

		return currentPromo;

	}
	
	/**
	 * Deletes promo permanently
	 * @param promo promo action object
	 * @return result of the operation (boolean)
	 * @throws LogicException if problems occured while deleting promo
	 */
	public boolean removePromo(Promo promo)  throws LogicException {
		PromoRepository repository;
		boolean isPromoRemoved;
		try {
			repository = new PromoRepository();
			repository.remove(promo);
			isPromoRemoved = true;
		} catch (RepositoryException e) {
			throw new LogicException(e);
		}
		return isPromoRemoved;
	}

}
