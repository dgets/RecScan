package recscan;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArchiveTest {

	@Test
	public void testIsEmpty() {
		Archive testArc = new Archive();
		
		assertTrue(testArc.isEmpty());
	}

}
