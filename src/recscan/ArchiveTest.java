package recscan;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArchiveTest {

	@Test
	public void testIsEmpty() {
		Archive testArc = new Archive();
		
		assertTrue(testArc.isEmpty());
	}
	
	@Test
	public void testArcDoesCompress() {
		Archive testArc1 = new Archive();
		Archive testArc2 = new Archive();
		
		testArc1.init(); testArc2.init();
		
		testArc1.setArcType(RecScan.ZIP);
		testArc2.setArcType(RecScan.GZ);
		
		assertTrue(testArc1.arcDoesCompress());
		assertFalse(testArc2.arcDoesCompress());
	}

}
