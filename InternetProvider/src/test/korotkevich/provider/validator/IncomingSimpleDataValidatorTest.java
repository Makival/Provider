/**
 * 
 */
package test.korotkevich.provider.validator;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.korotkevich.provider.validator.IncomingSimpleDataValidator;

/**
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class IncomingSimpleDataValidatorTest {
	
	String doubleValue = "999.99";
	String doubleNegativeValue = "-999.99";
	String intValue = "999";
	String stringValue = "string value";
	String stringValueFalse = "alert(messageValue)";
	
	@AfterClass
	public void tearDown() {
		stringValue = null;
		stringValueFalse = null;
	}

	@Test
	public void isDoubleValueValidatedTrue() {
		boolean condition = IncomingSimpleDataValidator.validateDouble(doubleValue);
		Assert.assertTrue(condition);
	}
	
	@Test
	public void isDoubleValueValidatedFalse() {
		boolean condition = IncomingSimpleDataValidator.validateDouble(stringValue);
		Assert.assertFalse(condition);
	}
	
	@Test
	public void isDoublePositiveValueValidatedTrue() {
		boolean condition = IncomingSimpleDataValidator.validatePositiveDouble(doubleValue);
		Assert.assertTrue(condition);
	}
	
	@Test
	public void isDoublePositiveValueValidatedFalse() {
		boolean condition = IncomingSimpleDataValidator.validatePositiveDouble(doubleNegativeValue);
		Assert.assertFalse(condition);
	}
	
	@Test
	public void isIntValueValidatedTrue() {
		boolean condition = IncomingSimpleDataValidator.validatePositiveInt(intValue);
		Assert.assertTrue(condition);
	}
	
	@Test
	public void isIntValueValidatedFalse() {
		boolean condition = IncomingSimpleDataValidator.validatePositiveInt(stringValue);
		Assert.assertFalse(condition);
	}
	
	@Test
	public void isStringValueValidatedTrue() {
		boolean condition = IncomingSimpleDataValidator.validateString(stringValue);
		Assert.assertTrue(condition);
	}
	
	@Test
	public void isStringValueValidatedFalse() {
		boolean condition = IncomingSimpleDataValidator.validateString(stringValueFalse);
		Assert.assertFalse(condition);
	}
}
