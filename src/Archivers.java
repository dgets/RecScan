import java.util.HashMap;


public class Archive {
	//'constants'
	public static final String BZ2 	= 	new String("bzip2");
	public static final String GZ 	= 	new String("gzip");
	public static final String LZ4	=	new String("lz4");
	public static final String RAR	=	new String("rar");
	public static final String XZ	=	new String("xz");
	
	//let's try using a HashMap
	public HashMap<String, String> compressor;
	public HashMap<String, String> flagSet;
	
	//instance properties
	private String 	arctype			=	null;
	private String 	fn				=	null;
	
	//getters/setters
	public void setArctype(String at) {
		this.arctype = at;
	}
	
	public String getArctype() {
		return arctype;
	}
	
	public void setFn(String fname) {
		this.fn = fname;
	}
	
	public String getFn() {
		return fn;
	}
	
	//misc methods
	public void init() {
		/* compressors.putAll( GZ = "/bin/gzip",
				BZ2 = "/bin/bzip2",
				LZ4 = null,
				RAR = "/usr/bin/unrar",
				XZ	= "/usr/bin/xz" ); */
		compressor.put(GZ, "/bin/gzip");
		compressor.put(BZ2, "/bin/bzip2");
		compressor.put(LZ4, null);
		compressor.put(RAR, "/usr/bin/rar");
		compressor.put(XZ, "/usr/bin/xz");
		
		flagSet.put(GZ, "-d");
		flagSet.put(BZ2, "-d");
		flagSet.put(LZ4, null);
		flagSet.put(RAR, "--extract");
		flagSet.put(XZ, "--decompress");
	}
	
	public String getExpandExecString() throws Exception {
		if (compressor.equals(null) || flagSet.equals(null) ||
				fn.equals(null)) {
			throw new Exception("empty archive object");
		}
		
		return compressor.get(arctype) + " " + flagSet.get(arctype) +
				" " + fn;
	}
}
