package RecScan;

import java.io.InputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
//import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

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
	public static String[] tarListDir(InputStream incoming) throws Exception {
		TarArchiveInputStream tarInput = null;
		//TarArchiveOutputStream copyOut = null;
		String[] directory = null;
		
		try {
			int cntr = 0;
			
            tarInput = (TarArchiveInputStream) incoming;
            TarArchiveEntry entry;
            
            while ((entry = tarInput.getNextTarEntry()) != null) {
                //System.out.println(entry.getName());
            	directory[cntr++] = entry.getName();
            }
            
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
		
		return directory;
	}
	
	/** Untar an input file into an output file.

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
