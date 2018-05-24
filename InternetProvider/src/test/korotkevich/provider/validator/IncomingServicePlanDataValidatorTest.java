/**
 * 
 */
package test.korotkevich.provider.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.korotkevich.provider.validator.IncomingServicePlanDataValidator;

/**
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class IncomingServicePlanDataValidatorTest {

	@Test(dataProvider = "servicePlanDataTrue", dataProviderClass = ServicePlanDataProvider.class)
	public void validateServicePlanDataTrue(Map<String, String> promoParamMap) {
		List<String> validationErrorList = new ArrayList<>();
		IncomingServicePlanDataValidator.validateSpData(promoParamMap, validationErrorList);
		boolean condition = validationErrorList.isEmpty();
		Assert.assertTrue(condition);
	}

	@Test(dataProvider = "servicePlanDataFalse", dataProviderClass = ServicePlanDataProvider.class)
	public void validateServicePlanDataFalse(Map<String, String> promoParamMap) {
		List<String> validationErrorList = new ArrayList<>();
		IncomingServicePlanDataValidator.validateSpData(promoParamMap, validationErrorList);
		boolean condition = validationErrorList.isEmpty();
		Assert.assertFalse(condition);
	}

}
