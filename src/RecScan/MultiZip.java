package RecScan;
import java.io.*;
import java.util.zip.*;

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
		InputStream fis = new FileInputStream(curArc.getFn());
		
		try {			
			if (curArc.getArcType().equals(RecScan.GZ)) {
				@SuppressWarnings("unused")
				GZIPInputStream zis = new GZIPInputStream(
						new BufferedInputStream(fis));
				return (InputStream) zis;
			} else if (curArc.getArcType().equals(RecScan.ZIP)){
				@SuppressWarnings("unused")
				ZipInputStream zis = new ZipInputStream(
						new BufferedInputStream(fis));
				return (InputStream) zis;
			} else {
				System.err.println(
						"Unsupported archive type in openStream()");
				throw new Exception(
						"Unsupported archive type in openStream()");
			}
		} catch (IOException e) {
			System.err.println("openStream(): " + e.getMessage());
			throw new Exception("openStream(): " + e.getMessage());
		} finally {
			fis.close();
		}
	}
}
