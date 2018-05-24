/**
 * 
 */
package test.korotkevich.provider.pool;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.korotkevich.provider.exception.ConnectionPoolException;
import com.korotkevich.provider.pool.ConnectionPool;

/**
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class ConnectionPoolTest {
	ConnectionPool pool = null;

	@Test
	public void isPoolCreatedTrue() {

		try {
			pool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}

		boolean condition = pool != null;
		Assert.assertTrue(condition);
	}

	@AfterClass
	public void tearDown() {
		if (pool != null) {
			pool.destroy();
		}
	}

}
