package recscan;

import static org.junit.Assert.*;
import java.util.HashMap;
import org.junit.Test;

public class ArcStructTest {

	@Test
	public void testInit() {
		final ArcStruct godOuah = new ArcStruct();
		HashMap<String, String> nakk = new HashMap<String, String>();
		
		godOuah.init();
		nakk = godOuah.getAlgo(RecScan.GZ);
		
		//verify that gzip's nested HashMap is good
		assertEquals(RecScan.GZ, nakk.get(RecScan.EXE));
		assertNotNull(nakk.get(ArcStruct.CO));
		assertEquals("-d", nakk.get(ArcStruct.D));
		assertEquals("--stdout", nakk.get(ArcStruct.SO));
		assertNull(nakk.get(ArcStruct.SI));
		assertNull(nakk.get(ArcStruct.L));
		assertNull(nakk.get(ArcStruct.O));
		
		nakk = godOuah.getAlgo(RecScan.TAR);
		
		//verify tar's nested hash
		assertEquals(RecScan.TAR, nakk.get(RecScan.EXE));
		assertNull(nakk.get(ArcStruct.CO));
		assertEquals("x", nakk.get(ArcStruct.D));
		assertNull(nakk.get(ArcStruct.SO));
		assertEquals("-", nakk.get(ArcStruct.SI));
		assertEquals("t", nakk.get(ArcStruct.L));
		assertEquals("f", nakk.get(ArcStruct.O));
		
		//no need to add a test for zip's yet
	}

}
