package recscan;
import java.io.*;
import java.util.zip.*;

import RecScan;

/**
 * @author Damon Getsman
 * Decompression routines
 */
public class MultiZip {

	/**
	 * 
	 * @param Arc curArc - current archive
	 * @return InputStream - ready to read compressed archive contents
	 * @throws Exception
	 * 
	 */
	public static InputStream openStream(Arc curArc) throws Exception {
		try {
			FileInputStream fis = new FileInputStream(curArc.getFn());
			
			if (curArc.getArcType().equals(RecScan.GZ)) {
				GZIPInputStream zis = new GZIPInputStream(
						new BufferedInputStream(fis));
			} else if (curArc.getArcType().equals(RecScan.ZIP)){
				ZipInputStream zis = new ZipInputStream(
						new BufferedInputStream(fis));
			} else {
				throw new Exception(
						"Unsupported archive type in openStream()");
			}
		} catch (IOException e) {
			throw new Exception("openStream(): " + e.getMessage());
		}
	}
}
