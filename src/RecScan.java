import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Array;

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
	public static final String BZ2 			= 	new String("bzip2");
	public static final String GZ 			= 	new String("gzip");
	public static final String LZ4			=	new String("lz4");
	public static final String RAR			=	new String("rar");
	public static final String XZ			=	new String("xz");
	public static final String compList[]	= 	{BZ2, GZ, LZ4, RAR, XZ};

	//archivers
	public static final String TAR			=	new String("tar");
	public static final String ZIP			=	new String("zip");
	public static final String arcList[]	=	{TAR, ZIP};
	
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
				System.out.println(e);
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
	}

	private static String determineType(String nang) throws Exception {
		for (String alg : compList) {
			if (nang.endsWith(alg)) {
				return alg;
			}
		}
		
		throw new Exception("Not a valid archive name scheme");
	}
	
	private static Archive initTarget(String fname) 
			throws Exception {
		Archive tgt	=	new Archive();
		
		try {
			tgt.setArcType(determineType(fname));
		} catch (Exception e) {
			//yeah we need to do this on stderr, but I'm not looking that
			//up right this second
			System.out.println(e);
			throw new Exception(e);
		}
		
		tgt.setFn(fname);
		return tgt;
	}
	
	private static String getArcContentList(Archive ouah) 
			throws Exception {
		String output = null;
		
		try {
			String line;
			ProcessBuilder pb = 
					new ProcessBuilder(ouah.getExpandExecString());
			
			Process p = pb.start();
			p.waitFor();
			
			BufferedReader bri =
					new BufferedReader(
							new InputStreamReader(p.getInputStream()));
			
			while ((line = bri.readLine()) != null) {
				output += line;
			}
		} catch (Exception e) {
			throw new Exception("Fucked: getArcContentList()");
		}
		
		return output;
	}
	
}
