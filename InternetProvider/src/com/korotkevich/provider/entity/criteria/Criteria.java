package com.korotkevich.provider.entity.criteria;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Korotkevich Kirill 2018-05-10
 *
 * @param <E>
 */
public class Criteria<E> {

	private Map<E, Object> criteriaMap = new HashMap<E, Object>();

	public void add(E searchCriteria, Object value) {
		criteriaMap.put(searchCriteria, value);
	}
	
	public Object get(E searchCriteria) {
		Object value = criteriaMap.get(searchCriteria);
		
		return value;
	}
	
	public boolean containsCriteriaKey(E key) {
		boolean isContatinsKey = criteriaMap.containsKey(key);
		return isContatinsKey;
	}
	
	public boolean isEmpty() {
		return criteriaMap.isEmpty();
	}

}
