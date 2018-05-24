/**
 * 
 */
package test.korotkevich.provider.pool.propertyloader;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.korotkevich.provider.exception.ConnectionPoolException;
import com.korotkevich.provider.pool.propertyloader.PoolPropertyLoader;

/**
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class PoolPropertyLoaderTest {

	@Test
	public void loadPropertiesTrue() {

		PoolPropertyLoader loader = new PoolPropertyLoader();
		Properties properties = new Properties();
		try {
			properties = loader.fillInProperties();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}

		boolean condition = !properties.isEmpty();
		Assert.assertTrue(condition);
	}

}
