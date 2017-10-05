package recscan;
import java.io.*;
import java.util.zip.*;

/*
 * Decompression routines
 */
public class Zip {

	public static InputStream zipOpenStream(String fn) {
		//final int BUFFER = 2048;
		OutputStream dest = null;
		
		try {
			FileInputStream fis = new 
					FileInputStream(fn);
			ZipInputStream zis = new 
					ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry;

			while ((entry = zis.getNextEntry()) != null) {
				System.out.println("Extracting: " +entry);
				/*int count;
				byte data[] = new byte[BUFFER];*/
			}

			zis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
			
		return dest;
	}
}
