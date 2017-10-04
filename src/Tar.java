//import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
//import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

public class Tar {
	@SuppressWarnings("null")
	public String[] tarListDir(InputStream incoming/*, OutputStream out*/) {
		TarArchiveInputStream tarInput = null;
		//TarArchiveOutputStream copyOut = null;
		String[] directory = null;
		
		try {
			int cntr = 0;
			
            tarInput = (TarArchiveInputStream) incoming;
            TarArchiveEntry entry;
            
            while ((entry = tarInput.getNextTarEntry()) != null) {
                System.out.println(entry.getName());
            	directory[cntr++] = entry.getName();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		/* try {
			copyOut = (TarArchiveOutputStream) copyStream(incoming, out);
		} catch (IOException e) {
			System.out.println("Fucked: tarListDir()");
		} */
		
		return directory;
	}
	
	/* public static OutputStream copyStream(InputStream input, OutputStream output)
		    throws IOException {byte[] buffer = new byte[1024]; // Adjust if you want
		int bytesRead;
		
		try {
			while ((bytesRead = input.read(buffer)) != -1)
			{
				output.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			throw e;
		}
		
		return output;
	} */
}
