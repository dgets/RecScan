import java.util.HashMap;


public class Archive {
	//let's try using a HashMap
	private HashMap<String, String> compressor, archiver;
	private HashMap<String, String> dFlagSet, lFlagSet, sOFlagSet, sIFlagSet;
	private HashMap<String, Boolean> compDoesArc;
	
	//instance properties
	private String 	arcType			=	null;
	private String	compType		=	null;
	private String 	fn				=	null;
	
	//getters/setters
	public void setArcType(String at) {
		this.arcType = at;
	}
	
	public String getArctype() {
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
		//-->>compressor<<--
		compressor.put(RecScan.GZ, "/bin/gzip");
		compressor.put(RecScan.BZ2, "/bin/bzip2");
		compressor.put(RecScan.LZ4, null);
		compressor.put(RecScan.RAR, "/usr/bin/rar");
		compressor.put(RecScan.XZ, "/usr/bin/xz");
		
		//decompression flag
		dFlagSet.put(RecScan.GZ, "-d");
		dFlagSet.put(RecScan.BZ2, "-d");
		dFlagSet.put(RecScan.LZ4, null);
		dFlagSet.put(RecScan.RAR, "--extract");
		dFlagSet.put(RecScan.XZ, "--decompress");
		
		//stdout flag
		sOFlagSet.put(RecScan.GZ,  "--stdout");
		sOFlagSet.put(RecScan.BZ2, "--stdout");
		sOFlagSet.put(RecScan.LZ4, null);
		sOFlagSet.put(RecScan.RAR, null);
		sOFlagSet.put(RecScan.XZ, "--stdout");
		
		//stdin flag
		sIFlagSet.put(RecScan.TAR, "-t");
		sIFlagSet.put(RecScan.ZIP, null);		
		
		//-->>archiver<<--
		archiver.put(RecScan.TAR, "/bin/tar");
		archiver.put(RecScan.ZIP, "/usr/bin/unzip");		
		
		//compressor handles archival
		compDoesArc.put(RecScan.TAR, false);
		compDoesArc.put(RecScan.ZIP, true);
		
		//list contents flag
		lFlagSet.put(RecScan.TAR, "-t");
		lFlagSet.put(RecScan.ZIP, "-l");
	}
	
	public String getExpandExecString() throws Exception {
		String godOuah = new String();
		
		//too simple (duh); if it's tar, for example, we need to conditionally
		//decompress and unarchive separately [ouah ouah ouah]
		godOuah = compressor.get(arcType) + " " + dFlagSet.get(arcType) + " ";
		if (!compDoesArc.get(compressor)) {
			//create piped command
			godOuah += "sOFlagSet.get(arcType) + " | " + archiver.get(arcType) +
						+ " " + 
		}
	}
	
	public String getListExecString() throws Exception {
		//don't forget to bring a towel!
	}
	
	//this probably won't be needed
	private boolean isEmpty() {
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
