package recscan;

import java.util.HashMap;

/**
 * 
 * @author Damon Getsman
 * 
 *
 */
public class ArcStruct {
	//'constants'
	private static final String CO = "justComp";
	private static final String D = "dFlag";
	private static final String SO = "sOFlag";
	private static final String SI = "sIFlag";
	private static final String L = "lFlag";
	//private static final String X = "xFlag";
	private static final String O = "oFlag";
	
	//fields
	private static HashMap<String, HashMap> algo = 
			new HashMap<String, HashMap>();

	//methods
	/**
	 * 
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
		algo.put(RecScan.GZ, stuffIt);
		
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
