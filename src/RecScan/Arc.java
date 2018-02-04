package RecScan;

/**
 * 
 * @author Damon Getsman
 * Object to be instantiated for any particular archive to work with.
 * Child of ArcStruct.
 *
 */
public class Arc extends ArcStruct {
	//fields
	private String fn		=	new String();
	private String arcType	=	new String();
	
	//accessors
	public String getFn() {
		return this.fn;
	}
	
	public String getArcType() {
		return this.arcType;
	}
	
	//constructor(s)
	public Arc() { }
	
	//methods
	/**
	 * Initializes Arc object for dealing with fname
	 * @param fname String - filename of the archive
	 * @throws Exception
	 */
	public void init(String fname) throws Exception {
		super.init();
		
		this.fn = fname;
		
		try {
			setArcType();
		} catch (Exception e) {
			System.err.println("Fucked1: " + e.getMessage());
			throw new Exception("Problem in Arc.init()");
		}
	}
	
	/**
	 * Sets the archive type depending on fn's extension; probably should
	 * be returning TAR when GZ detected, though
	 * @throws Exception
	 */
	private void setArcType() throws Exception {
		if (this.fn.endsWith(RecScan.TARGZ)) {
			this.arcType = RecScan.TAR;
		} else if (fn.endsWith(RecScan.ZIP)) {
			this.arcType = RecScan.ZIP;
		} else if (fn.endsWith(RecScan.TAR)) {
			this.arcType = RecScan.TAR;
		} else {
			//not implemented
			System.err.println("Invalid/unhandled archive");
			throw new Exception("Unhandled or invalid archive");
		}
	}
	
	/**
	 * Basically a wrapper for this.fn.isEmpty()
	 * @return true for empty
	 */
	public Boolean isEmpty() {
		return this.fn.isEmpty();
	}
}
