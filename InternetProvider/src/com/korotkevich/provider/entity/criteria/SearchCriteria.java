package com.korotkevich.provider.entity.criteria;

/**
 * 
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public final class SearchCriteria {

	public static enum ServicePlanCriteria {
		NAME("nameCriteria", "String"), 
		MONTHLY_FEE_MIN("monthlyFeeCriteriaMin", "Double"), 
		MONTHLY_FEE_MAX("monthlyFeeCriteriaMax", "Double"), 
		ACCESS_COST_MIN("accessCostCriteriaMin", "Double"), 
		ACCESS_COST_MAX("accessCostCriteriaMax", "Double");

		private String criteriaName;
		private String valueTypeName;

		ServicePlanCriteria(String criteriaName, String valueTypeName) {
			this.criteriaName = criteriaName;
			this.valueTypeName = valueTypeName;
		}

		public String getCriteriaName() {
			return criteriaName;
		}
		
		public String getValueTypeName() {
			return valueTypeName;
		}

	}

	private SearchCriteria() {
	}
}
