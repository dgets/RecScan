package RecScan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
	public static List<String> tarListDir(InputStream incoming) 
		throws Exception {
		TarArchiveInputStream tarInput = new TarArchiveInputStream(incoming);
		TarArchiveEntry entry = null;
		//String[] directory = null;
		List<String> ouah = new ArrayList<String>();
		int cntr = 0;
		
		try {
			while ((entry = tarInput.getNextTarEntry()) != null) {
				ouah.add(entry.getName());
				
				if (RecScan.verbose || RecScan.debugging) {
					if (entry.isFile()) {
						System.out.print("file: \t");
					} else if (entry.isDirectory()) {
						System.out.print("dir: \t");
					} else {
						System.out.print("wut: \t");
					}
					
					System.out.println(ouah.get(ouah.size() - 1));
				}
				
				//ouah.add(entry.getName());
			}
		} catch (Exception e) {
			tarInput.close();
			throw new Exception("Closed w/exception: " + e.getMessage());
		}
		
		if (RecScan.verbose || RecScan.debugging) {
			System.out.println("Closing tarInput normally");
		}
		tarInput.close();
		
		return ouah;
	}
	/*@SuppressWarnings({ "null", "deprecation" })
	public static String[] tarListDir(InputStream incoming) 
			throws Exception {
		if (RecScan.verbose || RecScan.debugging) {
			System.out.println("Opening tarInput . . .");
		}
		if (RecScan.debugging) {
			System.out.println("incoming.available(): " +
					incoming.available());
		}
		
		TarArchiveInputStream tarInput = new TarArchiveInputStream(incoming);
		TarArchiveEntry entry = null;
		String[] directory = null;

		int cntr = 0;
		
		//if (RecScan.debugging) {
		//	//System.out.println(totalEntries + " total tar entries");
		//	//System.out.println("tarInput.getCount(): " + tarInput.getCount());
		//	System.out.println(tarInput.available() + " total available");
		//}
		
		while (incoming.available() > 0) {
			try {
				entry = tarInput.getNextTarEntry();
			} catch (Exception e) {
				throw new Exception ("tarInput.getNextTarEntry()");
			}
			
			if (RecScan.debugging) {
				System.out.println("Read entry #" + (cntr + 1));
			}
			
			try {
				entry = tarInput.getNextTarEntry();	
			} catch (Exception e) {
				throw new Exception("Looping .getNextTarEntry() borked");
			}

			if (RecScan.debugging) {
				System.out.println("Pulling entry: " + entry.getName());
			}

			//other conditions?
			if (entry.isGlobalPaxHeader()) {
				if (RecScan.debugging) {
					System.out.println("-Found Global Pax Header-");
				}
				continue;
			} else if (entry.isFile()) {
				if (RecScan.debugging) {
					System.out.println("-Valid File Found-");
				}

				directory[cntr] = entry.getName();
				if (RecScan.verbose || RecScan.debugging) {
					System.out.println(directory[cntr]);
				}
			} else if (entry.isDirectory()) {
				if (RecScan.verbose || RecScan.debugging) {
					System.out.println("-Valid Directory Found-");
				}

				directory[cntr] = entry.getName();	//correct?
			} else {
				if (RecScan.verbose || RecScan.debugging) {
					System.out.println("-No Idea-");
				}
			}
			
			cntr++;
		}

		tarInput.close();
		if (RecScan.verbose || RecScan.debugging) {
			System.out.println("Exited tarInput while");
		}
        
//        } catch (NullPointerException npe) {
//        	//end of archive assumed at this point
//        	System.err.println("NP Fucked: tarListDir() " + npe);
//		} catch (Exception e) {
//        	System.err.println("Fucked: tarListDir() " + e);
//            throw new Exception(e);
//        } finally {
//        	tarInput.close();
//        }
		
		return directory;
	}*/
	
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
}
