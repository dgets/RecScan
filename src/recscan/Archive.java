package recscan;

import java.util.HashMap;

/**
 * 
 * @author Damon Getsman
 * Archive object definition
 *
 */
public class Archive {
	//let's try using a HashMap
	//somehow I think that there should probably be a more intricate data
	//structure here... :P  er maybe a superclass to hold this?  gnahhh
	private static HashMap<String, String> compressor = 
			new HashMap<String, String>();
	private static HashMap<String, String> archiver = 
			new HashMap<String, String>();
	private static HashMap<String, String> dFlagSet =
			new HashMap<String, String>();
	private static HashMap<String, String> lFlagSet =
			new HashMap<String, String>();
	private static HashMap<String, String> sOFlagSet =
			new HashMap<String, String>();
	private static HashMap<String, String> sIFlagSet =
			new HashMap<String, String>();
	/*private static HashMap<String, String> oFlagSet =
			new HashMap<String, String>();*/
	private static HashMap<String, String> xFlagSet =
			new HashMap<String, String>();
	private static HashMap<String, Boolean> compDoesArc =
			new HashMap<String, Boolean>();
	
	private static String[]	justComp	=	null;
	private static String[]	justArc		=	null;
	
	//instance fields
	private String 	arcType			=	null;
	private String	compType		=	null;
	private String 	fn				=	null;

	//getters/setters
	public void setArcType(String at) {
		this.arcType = at;
	}
	
	public String getArcType() {
		return this.arcType;
	}
	
	public void setCompType(String comp) {
		this.compType = comp;
	}
	
	public String getCompType() {
		return this.compType;
	}
	
	public void setFn(String fname) {
		this.fn = fname;
	}
	
	public String getFn() {
		return this.fn;
	}
	
	/* private String getSOFlag(String compType) throws Exception {
		if ((compType == RecScan.RAR) || (compType == RecScan.ZIP)) {
			throw new Exception(compType + "doesn't use stdout");
		}
		
		return 
	} */
	
	//misc methods
	public void init() {
		//-->>compressors<<--
		compressor.put(RecScan.GZ, "/bin/gzip");
		compressor.put(RecScan.BZ2, "/bin/bzip2");
		compressor.put(RecScan.LZ4, null);
		compressor.put(RecScan.RAR, "/usr/bin/rar");
		compressor.put(RecScan.XZ, "/usr/bin/xz");
		compressor.put(RecScan.TAR, null);	//need to specify multiple here?
											//definitely some logic for other
											//cases of compressors
		
		//yeah this is gross
		justComp[0] = RecScan.GZ;
		justComp[1] = RecScan.BZ2;
		justComp[2] = RecScan.LZ4;
		justComp[3] = RecScan.XZ;
		
		//-->>archivers<<--
		archiver.put(RecScan.TAR, "/bin/tar");
		archiver.put(RecScan.GZ, null);	//"/bin/tar");
		archiver.put(RecScan.BZ2, null);	//"/bin/tar");
		archiver.put(RecScan.LZ4, null);	//"/bin/tar");
		archiver.put(RecScan.RAR, "/usr/bin/rar");
		archiver.put(RecScan.ZIP, "/usr/bin/unzip");
		
		justArc[0] = RecScan.TAR;
		
		//decompression flag
		dFlagSet.put(RecScan.GZ, "-d");
		dFlagSet.put(RecScan.BZ2, "-d");
		dFlagSet.put(RecScan.LZ4, null);
		dFlagSet.put(RecScan.RAR, "--extract");
		dFlagSet.put(RecScan.XZ, "--decompress");
		dFlagSet.put(RecScan.TAR, null);
		
		//stdout flag
		sOFlagSet.put(RecScan.GZ,  "--stdout");
		sOFlagSet.put(RecScan.BZ2, "--stdout");
		sOFlagSet.put(RecScan.LZ4, null);
		sOFlagSet.put(RecScan.RAR, null);
		sOFlagSet.put(RecScan.XZ, "--stdout");
		sOFlagSet.put(RecScan.TAR, null);
		
		//stdin flag
		sIFlagSet.put(RecScan.TAR, "-");
		sIFlagSet.put(RecScan.ZIP, null);
		
		//compressor handles archival
		compDoesArc.put(RecScan.GZ, false);
		compDoesArc.put(RecScan.BZ2, false);
		compDoesArc.put(RecScan.LZ4, false);	//need to verify
		compDoesArc.put(RecScan.RAR, true);
		compDoesArc.put(RecScan.ZIP, true);
		
		//list contents flag
		lFlagSet.put(RecScan.TAR, "-t");
		lFlagSet.put(RecScan.ZIP, "-l");
		lFlagSet.put(RecScan.RAR, null);	//needed
		
		//extract flag
		xFlagSet.put(RecScan.TAR, "-x");
		xFlagSet.put(RecScan.ZIP, null);
	}
	
	/**
	 * Method determines whether or not compression algorithm also handles arc
	 * @return Boolean true for handles arc as well
	 */
	public Boolean arcDoesCompress() {
		if (justComp.toString().contains(arcType)) {
			return false;
		} else {
			return true;
		}
	}
	
	/*public String[] getTarGzDir() {
		
	}*/
	
	/*public String[] getExpandExecString() throws Exception {
		String godOuah[] = null;
		
		//too simple (duh); if it's tar, for example, we need to conditionally
		//decompress and unarchive separately [ouah ouah ouah]
		godOuah[0] = compressor.get(arcType);
		godOuah[1] = dFlagSet.get(arcType);
		if (!compDoesArc.get(arcType)) {
			godOuah[2] = sOFlagSet.get(arcType);
		}
		
		/*if (!compDoesArc.get(arcType)) {
			//create piped command
			godOuah += sOFlagSet.get(arcType) + " | ";
			System.out.println(archiver.get(arcType));
			//if (archiver.get(arcType).compareTo(RecScan.TAR) == 0) {
				godOuah += archiver.get(arcType) + " " +
					xFlagSet.get(RecScan.TAR) + " " +
					oFlagSet.get(RecScan.TAR);
			//}
						//archiver.get(arcType) +	" " + xFlagSet.get(arcType) + 
						//" " + oFlagSet.get(arcType);
		}
		
		return godOuah;
	}*/
	
	/* public String getListExecString() throws Exception {
		//don't forget to bring a towel!
	} */
	
	//this probably won't be needed
	public boolean isEmpty() {
		//also: unit testing is going to make this a LOT easier by taking out
		//the following type of bullshit code
		if (compressor.equals(null) || dFlagSet.equals(null) ||
				fn.equals(null)) {
			//throw new Exception("empty archive object");
			return true;
		}
		
		return false;
	}
}
