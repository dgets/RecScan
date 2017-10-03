import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * This one is gonna be fun.  Working on trying to create something to help me
 * search, extract, delete, and otherwise mess with the contents of archives that may
 * well be recursive (eek)
 * 
 * I think for now we're going to start with just handling tar archive format, 
 * with plans to add .zip directory shit, as well.  Compression libraries for
 * starting out will be gzip, bz2, lz4, and rar
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Archive target 	= new Archive();
		String rawlist	= new String();
		
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
				rawlist = getArcContentList(target);
			} catch (Exception e) {
				//stderr, etc
				System.out.println(e);
				return;
			}
		} else {
			//print every match of userstring from the archive's entire
			//content list
			
		}
		
		System.out.println(rawlist + "\n");
	}

	private static String determineType(String nang) throws Exception {
		for (String alg : COMPLIST) {
			if (nang.endsWith(alg)) {
				return alg;
			}
		}
		
		throw new Exception("Not a valid archive name scheme");
	}
	
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
	private static String getArcContentList(Archive ouah) 
			throws Exception {
		if (ouah.isEmpty()) {
			System.out.println("WTF");
		}
		
		String output = null;
		String origDir = null;
		Path dirList[] = null;
		Path ouahful = null;	//WHY doesn't the array above work?  >:-0
		Path tmpDir = Paths.get("/tmp");
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
		
		try {
			String line;
			ProcessBuilder pb = 
					new ProcessBuilder(ouah.getExpandExecString());
			System.out.println("command string:\t" + 
					ouah.getExpandExecString().toString());
			
			pb.directory(ouahful.toFile());
			//System.out.println(pb.directory().toString());
			
			Process p = pb.start();
			p.waitFor();
			
			BufferedReader bri =
					new BufferedReader(
							new InputStreamReader(p.getInputStream()));
			
			while ((line = bri.readLine()) != null) {
				output += line;
			}
		} catch (Exception e) {
			ouahful.toFile().delete();
			throw new Exception("Fucked: getArcContentList()\n" +
					e.getMessage());
		}
		
		
		return output;
	}
	
	private static void cleanUp() {
		//remove any dingleberries here
	}
	
}
