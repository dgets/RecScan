package RecScan;

import java.util.HashMap;

/**
 * 
 * @author Damon Getsman
 * 
 *
 */
public class ArcStruct {
	//'constants'
	public static final String CO = "justComp";
	public static final String D = "dFlag";
	public static final String SO = "sOFlag";
	public static final String SI = "sIFlag";
	public static final String L = "lFlag";
	//private static final String X = "xFlag";
	public static final String O = "oFlag";
	
	//fields
	private static HashMap<String, HashMap<String, String>> algo = 
			new HashMap<String, HashMap<String, String>>();

	//accessors
	public HashMap<String, String> getAlgo(String algorithm) {
		return algo.get(algorithm);
	}
	
	//methods
	/**
	 * Method initializes an ArcStruct with the proper HashMap values
	 */
	public void init() {
		HashMap<String, String> stuffIt = 
				new HashMap<String, String>();
		
		/*
		 * we'll just start out with the values for gzip, tar, and zip; this
		 * will provide a chance to try out cases for both separate comp/arc
		 * algorithms and comp/arc handled in tandem
		 */
		
		//gzip
		stuffIt.put(RecScan.EXE, "/bin/gzip");
		stuffIt.put(CO, "true");	//will be null if false; kind of sloppy, I
									//guess, but for the life of me I can't
									//figger out what would be appropriate :P
		stuffIt.put(D, "-d");
		stuffIt.put(SO, "--stdout");
		stuffIt.put(SI, null);
		stuffIt.put(L, null);
		//stuffIt.put(X, "-d");
		stuffIt.put(O, null);
		algo.put(RecScan.TARGZ, stuffIt);
		
		//tar
		stuffIt.put(RecScan.EXE, "/bin/tar");
		stuffIt.put(CO, null);
		stuffIt.put(D, "x");
		stuffIt.put(SO, null);
		stuffIt.put(SI, "-");
		stuffIt.put(L, "t");
		stuffIt.put(O, "f");
		algo.put(RecScan.TAR, stuffIt);
		
		//zip
	}
}
