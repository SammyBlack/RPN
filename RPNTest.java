import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RPNTest {
	@Test
	public void testIsInteger() {
		// should only return true if input is an Integer
		assertFalse(RPN.isInteger("+"));
		assertFalse(RPN.isInteger("d"));
		assertFalse(RPN.isInteger("doge"));
		assertFalse(RPN.isInteger("2.0"));
		assertTrue(RPN.isInteger("-12"));
	}
}
