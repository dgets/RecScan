import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	public static final String GZ 			= 	new String("gz");
	public static final String LZ4			=	new String("lz4");
	public static final String RAR			=	new String("rar");
	public static final String XZ			=	new String("xz");
	public static final String COMPLIST[]	= 	{BZ2, GZ, LZ4, RAR, XZ};

	//archivers
	public static final String TAR			=	new String("tar");
	public static final String ZIP			=	new String("zip");
	public static final String ARCLIST[]	=	{TAR, ZIP};
	
	/**
	 * @param args[] - just the usual for main()
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Archive target 	= new Archive();
		String[] rawlist = null;
		
		if ((args.length == 0) || (args.length > 2)) {
			System.out.println("Usage:\trecscan [archive] [match string] " +
					"print fn hits in archive\n" + "\trecscan [archive] " +
					"list all archive contents");
			return;
		} else if (args.length == 1) {
			//let's get the entire archive's content list (with all sub-
			//archives)
			try {
				target = initTarget(args[0]);
			} catch (Exception e) {
				//yeah this needs to go to stderr
				//System.out.println(e);
				return;
			}
			
			try {
				//int cntr = 0;
				//do {
					rawlist = target.getTarGzDir();
				//} while (rawlist[cntr++] != null);
			} catch (Exception e) {
				//stderr, etc
				System.out.println(e);
				return;
			}
		} else {
			//print every match of userstring from the archive's entire
			//content list
			
		}
		
		System.out.println(rawlist.toString() + "\n");
	}

	/**
	 * 
	 * @param nang - archive filename
	 * @return String - archive format constant
	 * @throws Exception
	 */
	private static String determineType(String nang) throws Exception {
		for (String alg : COMPLIST) {
			if (nang.endsWith(alg)) {
				return alg;
			}
		}
		
		throw new Exception("Not a valid archive name scheme");
	}
	
	/**
	 * 
	 * @param fname - archive filename
	 * @return Archive - initialized archive object
	 * @throws Exception
	 */
	private static Archive initTarget(String fname) 
			throws Exception {
		Archive tgt	=	new Archive();
		
		tgt.init();
		
		try {
			tgt.setArcType(determineType(fname));
		} catch (Exception e) {
			//yeah we need to do this on stderr, but I'm not looking that
			//up right this second
			//System.out.println(e);
			throw new Exception(e);
		}
		
		tgt.setFn(fname);
		return tgt;
	}
	
	//NOTE: this does NOT handle any recursion yet
	/**
	 * 
	 * @param ouah - initialized archive
	 * @throws Exception
	 */
	private static void getArcContentList(Archive ouah) 
			throws Exception {
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
		dirList = tarListDir(Zip.zipOpenStream(tarFile));
		
	}
	
	/**
	 * cleans up any dingleberries laying around (not utilized)
	 */
	private static void cleanUp() {
		//remove any dingleberries here
	}
	
}
