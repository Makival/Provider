/**
 * 
 */
package test.korotkevich.provider.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.korotkevich.provider.validator.IncomingPromoDataValidator;

/**
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class IncomingPromoDataValidatorTest {

	@Test(dataProvider = "promoDataTrue", dataProviderClass = PromoDataProvider.class)
	public void validatePromoDataTrue(Map<String, String> promoParamMap) {
		List<String> validationErrorList = new ArrayList<>();
		IncomingPromoDataValidator.validatePromoData(promoParamMap, validationErrorList);
		boolean condition = validationErrorList.isEmpty();
		Assert.assertTrue(condition);
	}

	@Test(dataProvider = "promoDataFalse", dataProviderClass = PromoDataProvider.class)
	public void validatePromoDataFalse(Map<String, String> promoParamMap) {
		List<String> validationErrorList = new ArrayList<>();
		IncomingPromoDataValidator.validatePromoData(promoParamMap, validationErrorList);
		boolean condition = validationErrorList.isEmpty();
		Assert.assertFalse(condition);
	}

}
