package RecScan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import RecScan.MultiZip;
import RecScan.Tar;

/**
 * 
 * @author Damon Getsman
 * started: 1 Oct 17
 * finished: 
 * 
 * This one is gonna be fun.  Working on trying to create something to help me
 * search, extract, delete, and otherwise mess with the contents of archives that
 * may well be recursive (eek).  I've really needed a utility like this for a long
 * time, and I also know that I need the work on coding recursive algorithms, so
 * I'm not going to bother looking for someone else's utility.
 * 
 * Only working with tar as an archiver right now; compression supported at this
 * point will be gzip, bz2, lz4, xz, and rar.
 *
 */
public class RecScan {
	//'constants'
	//compressors
	public static final String BZ2 			= 	new String("bz2");
	public static final String TARGZ		= 	new String("tar.gz");	//our options need to be expanded here
	//public static final String TAR			=	new String("tar");
	public static final String LZ4			=	new String("lz4");
	public static final String RAR			=	new String("rar");
	public static final String XZ			=	new String("xz");
	public static final String COMPLIST[]	= 	{BZ2, TARGZ, LZ4, RAR, XZ};

	//archivers
	public static final String TAR			=	new String("tar");
	public static final String ZIP			=	new String("zip");
	public static final String ARCLIST[]	=	{TAR, ZIP};
	
	public static final String EXE			=	new String("path_to_exe");
	
	public static Boolean verbose 			=	null;
	
	public static final Boolean debugging	=	true;
	
	/**
	 * @param args[] - just the usual for main()
	 * @return int - 0 success/1 Arc.init() failure/2 Tar/MultiZip failure
	 */
	public static void main(String[] args) {
		//Arc target 			= new Arc();
		@SuppressWarnings("unused")
		//String[] rawlist 	= null;
		List<String> rawlist = new ArrayList<String>();
		
		if ((args.length == 3) && (isVerbose(args[0]))) {
			verbose = true;
			//verbosely search full (recursive?) listing for match string
			
		} else if (args.length == 3) {
			//bogus
			dumpUsage();
			return;
		} else if ((args.length == 2) && (isVerbose(args[0]))) {
			verbose = true;
			//verbosely get full listing in potentially recursive archive
			try {
				rawlist = getRawlist(args[1]);
			} catch (Exception e) {
				System.err.println("Borkage: " + e.getMessage());
			}
			
		} else if (args.length == 2) {
			verbose = false;
			//search full (recursive?) listing for match
			
		} else if ((args.length == 1) && (isVerbose(args[0]))) {
			//just a verbose flag doesn't help anyone much
			dumpUsage();
			return;
		} else if (args.length == 1) {
			verbose = false;
			//just get the full listing
		} else {
			if (args.length == 0) {
				dumpUsage();
				return;
			}
		}
	}
		
	public static void dumpUsage() {
		System.out.println("Usage:\trecscan [-v] [archive] [match " +
				"string] print string's hits in archive\n" + 
				"\trecscan [-v] [archive] list all archive contents");
			return;
	}
	
	private static List<String> getRawlist(String fn) throws Exception {
		Arc 		tgt 		=	new Arc();
		//String[] 	contents	=	null;
		List<String> contents = new ArrayList<String>();
		
		if (verbose || debugging) {
			System.out.println("Verbose:\nInitializing tgt\n");
		}
		try {
			tgt.init(fn);
		} catch (Exception e) {
			System.err.println("Fubar in getRawlist(): " + e.getMessage());
			throw new Exception("getRawlist() borked");
		}
		
		if (verbose || debugging) {
			System.out.println("Executing tgt.getArcType()");
		}
		if (tgt.getArcType().equals(TARGZ)) {
			System.out.println("Archive is " + TARGZ);
			try {
				contents = Tar.tarListDir(MultiZip.openStream(tgt));
			} catch (Exception e) {
				throw new Exception("Issue in getRawList()'s call to " +
						"Tar.tarListDir():\n" + e.getMessage());
			}
		}
		
		if (debugging) {
			System.out.println(contents.toString());
		}
		
		return contents;
	}
	
	//NOTE: this does NOT handle any recursion yet
	/**
	 * 
	 * @param ouah - initialized archive
	 * @throws Exception
	 */
	/*@SuppressWarnings("unused")
	private void getArcContentList(Archive ouah) throws Exception {
		if (ouah.isEmpty()) {
			System.out.println("WTF");
		}
		
		//String output = null;
		String origDir = null;
		String dirList[] = null;
		Path ouahful = null;	//WHY doesn't the array above work?  >:-0
		Path tmpDir = Paths.get("/tmp");
		String tarFile = null;
		
		//not working why? -> Paths.get(System.getProperty("java.io.tmpdir"));
		
		//create temp dir, move there
		//not going to check to see if the original fn contains a slash,
		//indicating the need for more pathwork, just yet
		origDir = System.getProperty("user.dir");
		//System.out.println("origDir:\t" + origDir + "\ttmpDir:\t" + 
		//		tmpDir.toString());
		try {
			ouahful = Files.createTempDirectory(tmpDir, "recscan_");
			//System.out.println("ouahful:\t" + ouahful);
		} catch (IOException e) {
			throw new Exception("Fucked: createTempDirectory()\n" +
					e.getMessage());
		}
		
		//System.out.println(ouahful.iterator().toString());
		tarFile = ouahful.iterator().toString();
		dirList = tarListDir(Tar.tarListDir(tarFile));
		
	}*/
	
	public static Boolean isVerbose(String arg) {
		if (arg.equals("-v")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * cleans up any dingleberries laying around (not utilized)
	 * @param tempDir File of temporary directory to remove
	 */
	@SuppressWarnings("unused")
	private void cleanUp(File tempDir) throws Exception {
		//remove any dingleberries here
		if (tempDir == null) {
			return;
		} else if (tempDir.isDirectory()) {
            String[] childFiles = tempDir.list();
            if(childFiles == null) {
                //Directory is empty. Proceed for deletion
                tempDir.delete();
            } else {
                //Directory has other files.
                //Need to delete them first
                for (String childFilePath :  childFiles) {
                    //recursive delete the files
                    cleanUp(new File(childFilePath));
                }
            }
            
        } else {
            //it is a simple file. Proceed for deletion
            tempDir.delete();
        }
	}
	
}
