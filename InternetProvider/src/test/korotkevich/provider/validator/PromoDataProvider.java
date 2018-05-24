/**
 * 
 */
package test.korotkevich.provider.validator;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;

import com.korotkevich.provider.command.PromoParameter;

/**
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class PromoDataProvider {

	private static Map<String, String> preparePromoValuesMap(String idValue, String nameValue, String trafficBonus,
			String accessDiscount) {

		Map<String, String> promoParamMap = new HashMap<String, String>();
		promoParamMap.put(PromoParameter.PROMO_ID.getParameterName(), idValue);
		promoParamMap.put(PromoParameter.NAME.getParameterName(), nameValue);
		promoParamMap.put(PromoParameter.TRAFFIC_BONUS.getParameterName(), trafficBonus);
		promoParamMap.put(PromoParameter.ACCESS_DISCOUNT.getParameterName(), accessDiscount);

		return promoParamMap;
	}

	@DataProvider(name = "promoDataTrue")
	public static Object[][] fillInPromoDataTrue() {
		String id = "1000";
		String name = "Promo name";
		String bonus = "50";
		String discount = "25";

		Map<String, String> promoParamMap = preparePromoValuesMap(id, name, bonus, discount);

		return new Object[][] { { promoParamMap } };
	}

	@DataProvider(name = "promoDataFalse")
	public static Object[][] fillInPromoDataFalse() {
		String stringValueFalse = "false";
		String nameValueFalse = "{false}";

		Map<String, String> promoParamMap = preparePromoValuesMap(stringValueFalse, nameValueFalse, stringValueFalse,
				stringValueFalse);

		return new Object[][] { { promoParamMap } };
	}
}
