/**
 * 
 */
package test.korotkevich.provider.validator;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;

import com.korotkevich.provider.command.ServicePlanParameter;

/**
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class ServicePlanDataProvider {

	private static Map<String, String> prepareServicePlanValuesMap(String idValue, String nameValue,
			String trafficLimit, String monthlyFee, String descriptionValue, String accessCost) {

		Map<String, String> servicePlanMap = new HashMap<String, String>();
		servicePlanMap.put(ServicePlanParameter.ID.getParameterName(), idValue);
		servicePlanMap.put(ServicePlanParameter.NAME.getParameterName(), nameValue);
		servicePlanMap.put(ServicePlanParameter.TRAFFIC_LIMIT.getParameterName(), trafficLimit);
		servicePlanMap.put(ServicePlanParameter.MONTHLY_FEE.getParameterName(), monthlyFee);
		servicePlanMap.put(ServicePlanParameter.ACCESS_COST.getParameterName(), accessCost);
		servicePlanMap.put(ServicePlanParameter.DESCRIPTION.getParameterName(), descriptionValue);

		return servicePlanMap;
	}

	@DataProvider(name = "servicePlanDataTrue")
	public static Object[][] fillInPromoDataTrue() {
		String id = "1000";
		String name = "SP name";
		String limit = "20";
		String fee = "19.99";
		String description = "some details";
		String cost = "9.99";

		Map<String, String> promoParamMap = prepareServicePlanValuesMap(id, name, limit, fee, description, cost);

		return new Object[][] { { promoParamMap } };
	}

	@DataProvider(name = "servicePlanDataFalse")
	public static Object[][] fillInPromoDataFalse() {
		String stringValueFalse = "false";
		String nameValueFalse = "{false}";

		Map<String, String> promoParamMap = prepareServicePlanValuesMap(stringValueFalse, nameValueFalse,
				stringValueFalse, stringValueFalse, stringValueFalse, stringValueFalse);

		return new Object[][] { { promoParamMap } };
	}
}
