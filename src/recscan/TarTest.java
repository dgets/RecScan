package recscan;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TarTest {
	Arc godOuah = new Arc();

	@Before
	public void setUp() throws Exception {
		godOuah.init("/home/khelair/tmp/test-archive.tar.gz");
	}

	@Test
	public void testTarListDir() {
		fail("Not yet implemented");
	}

	@Test
	public void testExtract() {
		fail("Not yet implemented");
	}

}
