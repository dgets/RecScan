package RecScan;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArcTest {

	@SuppressWarnings("null")
	@Test
	public void testInit() {
		Arc bleh = null;
		
		try {
			bleh.init("ouah");
		} catch (Exception e) {
			fail("bleh.init(\"ouah\") threw exception: " + e);
		}
		assertEquals("ouah", bleh.getFn());
	}

	@SuppressWarnings("null")
	@Test
	public void testSetArcType() {
		Arc bleh = null;
		Arc nang = null;
		
		try {
			bleh.init("ouah.tar.gz");
			nang.init("godouah.zip");
		} catch (Exception e) {
			fail("bleh.init() or nang.init() threw exception: " + e);
		}
		assertEquals(RecScan.GZ, bleh.getArcType());
		assertEquals(RecScan.ZIP, nang.getArcType());
	}

	@SuppressWarnings("null")
	@Test
	public void testIsEmpty() {
		Arc bleh = null;
		
		try {
			bleh.init("ouah.tar.gz");
		} catch (Exception e) {
			fail("bleh.init() threw exception: " + e);
		}
		assertFalse(bleh.isEmpty());
	}

}
