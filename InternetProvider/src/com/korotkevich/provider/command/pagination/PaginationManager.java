package com.korotkevich.provider.command.pagination;

import java.util.HashMap;
import java.util.Map;

import com.korotkevich.provider.validator.IncomingSimpleDataValidator;

/**
 * Class provides methods for pagination needs
 * such as calculation of page number, direction(forward, backward) etc.
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public class PaginationManager {
	private final static String DIRECTION = "direction";
	private final static String DIRECTION_PREV = "prev";
	private final static String DIRECTION_NEXT = "next";
	private final static int DEFAULT_PAGE_NUMBER = 1;
	private final static int DEFAULT_VALUE = 0;
	
	/**
	 * Provides direction according to incoming String value
	 * @param directionValue String value of a direction
	 * @return chosen direction value
	 */
	public static String takePageDirection(String directionValue) {
		
		if (directionValue == null || directionValue.isEmpty() || directionValue.equals(DIRECTION_NEXT)) {
			return DIRECTION_NEXT;
		} else if (directionValue.equals(DIRECTION_PREV)) {
			return DIRECTION_PREV;
		} else {
			return DIRECTION_NEXT;
		}
	}
	
	/**
	 * Provides default direction - forward 
	 * @return direction value (String)
	 */
	public static String takeDefaultDirection() {
		return DIRECTION_NEXT;
	}
	
	/**
	 * Calculates page number according to incoming parameters
	 * @param pageNumber previous page number (int)
	 * @param direction direction value (String) 
	 * @return calculated page number(int)
	 */
	public static int calculatePageNumber(int pageNumber, String direction) {
		int newPageNumber;

		if (direction.equals(DIRECTION_NEXT)) {
			newPageNumber = ++pageNumber;
		} else {
			newPageNumber = pageNumber == DEFAULT_PAGE_NUMBER ? DEFAULT_PAGE_NUMBER : --pageNumber;
		}
		return newPageNumber;
	}
	
	/**
	 * Parses String value to int
	 * @param value incoming String value
	 * @param defaultValue default value 0
	 * @return parsed or default int value
	 */
	public static int parsePageStringParameterToInt(String value, int defaultValue) {
		boolean isToIntParseble = IncomingSimpleDataValidator.validatePositiveInt(value);

		if (isToIntParseble) {
			return Integer.parseInt(value);
		} else {
			return defaultValue;
		}

	}
	
	/**
	 * Calculates int parameter value
	 * @param minParamValue incoming min int parameter value
	 * @param maxParamValue incoming max int parameter value
	 * @param pagingDirection incoming String paging direction value
	 * @return calculated parameter int value
	 */
	public static int calculateParameterValue(int minParamValue, int maxParamValue, String pagingDirection) {
		int parameterValue = pagingDirection.equals(DIRECTION_NEXT) ? maxParamValue : minParamValue;
		return parameterValue;
	}
	
	/**
	 * Adds incoming paging parameters to map
	 * @param paramIdName name of id parameter
	 * @param paramLastIdName name of last id parameter
	 * @param minIdValue incoming min int parameter value 
	 * @param maxIdValue incoming max int parameter value
	 * @param pageDirectionValue incoming String paging direction value
	 * @return prepared  paging parameters map
	 */
	public static Map<String, Object> preparePagingParameters(String paramIdName, String paramLastIdName,  int minIdValue, int maxIdValue, String pageDirectionValue){
		int idValue = calculateParameterValue(minIdValue, maxIdValue, pageDirectionValue);
		Map<String, Object> parametersMap = new HashMap<>();
		
		parametersMap.put(paramIdName, idValue);
		parametersMap.put(DIRECTION, pageDirectionValue);
		parametersMap.put(paramLastIdName, DEFAULT_VALUE);
		return parametersMap;
	}
	
}
