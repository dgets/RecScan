package RecScan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
 * 
 * @author Damon Getsman
 * Tar archive access routines
 * 
 */
public class Tar {
	
	/**
	 * 
	 * @param incoming InputStream - uncompressed data stream
	 * @return String[] - directory listing
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	public static String[] tarListDir(FileInputStream incoming) 
			throws Exception {
		TarArchiveInputStream tarInput = new TarArchiveInputStream(incoming);
		//FileInputStream fIncoming = (FileInputStream) incoming;
		//InputStream godFuckingOuah = (InputStream) fIncoming;

		// = new TarArchiveInputStream(incoming);
		//TarArchiveOutputStream copyOut = null;
		String[] directory = null;
		
		try {
			int cntr = 0;
			
            //tarInput = (TarArchiveInputStream) incoming;
            TarArchiveEntry entry;
            
            while ((entry = tarInput.getNextTarEntry()) != null) {
                //System.out.println(entry.getName());
            	directory[cntr++] = entry.getName();
            }
            
        } catch (Exception e) {
        	System.err.println("Fucked: tarListDir() " + e);
            throw new Exception(e);
        } /*finally {
        	tarInput.close();
        }*/
		
		return directory;
	}
	
	/**
	 * Method extracts input stream's associated tar archive to a temporary
	 * directory
	 * 
	 * @param incoming InputStream
	 * @return Path
	 * @throws Exception
	 */
	public static Path extract(InputStream incoming) throws Exception {
		Path tempDir = null;
		Path tmpHome = Paths.get("/tmp");	//needs to be conditional for
											//other OSes
		final TarArchiveInputStream tis = 
				(TarArchiveInputStream)	new 
				ArchiveStreamFactory().createArchiveInputStream(RecScan.TAR, 
						incoming);
	    TarArchiveEntry entry = null;
	    
		try {
			tempDir = Files.createTempDirectory(tmpHome, "recscan_");
		} catch (IOException e) {
			System.err.println("Fucked: createTempDirectory()");
			throw new Exception("Fucked: createTempDirectory()\n" +
					e.getMessage());
		}
		
		while ((entry = (TarArchiveEntry) tis.getNextEntry()) != null) {
	        final File outputFile = 
	        		new File(tempDir.toString(), entry.getName());
	        
	        if (entry.isDirectory()) {
	            if (!outputFile.exists()) {
	            	if (!outputFile.mkdirs()) {
	                    /*throw new IllegalStateException(String.format(
	                    		"Couldn't create directory %s.", 
	                    		outputFile.getAbsolutePath()));*/
	            		System.err.println("Couldn't create " +
	                    		entry.toString());
	            		throw new Exception("Couldn't create " +
	                    		entry.toString());
	                }
	            }
	        } else {
	            final OutputStream outputFileStream = 
	            		new FileOutputStream(outputFile); 
	            IOUtils.copy(tis, outputFileStream);
	            outputFileStream.close();
	        }
	    }
		
		return tempDir;
	}
	
	/** Untar an input file into an output file.
	 *
	 * The output file is created in the output folder, having the same name
	 * as the input file, minus the '.tar' extension. 
	 * 
	 * @param inputFile     the input .tar file
	 * @param outputDir     the output directory file. 
	 * @throws IOException 
	 * @throws FileNotFoundException
	 *  
	 * @return  The {@link List} of {@link File}s with the untared content.
	 * @throws ArchiveException 
	 */
	//method currently under modification for my purposes
	/*public static List<File> unTar(InputStream inputFile, final File outputDir) 
			throws FileNotFoundException, IOException, ArchiveException {

	    LOG.info(String.format("Untaring %s to dir %s.", inputFile.getAbsolutePath(), outputDir.getAbsolutePath()));

	    final List<File> untarredFiles = new LinkedList<File>();
	    final TarArchiveInputStream debInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", is);
	    TarArchiveEntry entry = null; 
	    while ((entry = (TarArchiveEntry)debInputStream.getNextEntry()) != null) {
	        final File outputFile = new File(outputDir, entry.getName());
	        if (entry.isDirectory()) {
	            LOG.info(String.format("Attempting to write output directory %s.", outputFile.getAbsolutePath()));
	            if (!outputFile.exists()) {
	                LOG.info(String.format("Attempting to create output directory %s.", outputFile.getAbsolutePath()));
	                if (!outputFile.mkdirs()) {
	                    throw new IllegalStateException(String.format("Couldn't create directory %s.", outputFile.getAbsolutePath()));
	                }
	            }
	        } else {
	            LOG.info(String.format("Creating output file %s.", outputFile.getAbsolutePath()));
	            final OutputStream outputFileStream = new FileOutputStream(outputFile); 
	            IOUtils.copy(debInputStream, outputFileStream);
	            outputFileStream.close();
	        }
	        untaredFiles.add(outputFile);
	    }*/
	
	/**
	 * 
	 * @param incoming InputStream - uncompressed data stream
	 * @return String - temp directory filename
	 * @throws Exception
	 */
	/*public static String extract(InputStream incoming) throws Exception {
		TarArchiveInputStream tarinput = null;
		
		try {
			
		}
	}*/
}
