package recscan;

/**
 * 
 * @author Damon Getsman
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
	
	/*public void setFn(String fname) {
		this.fn = fname;
	}*/
	
	public String getArcType() {
		return this.arcType;
	}
	
	//constructor(s)
	public Arc(String fn) {
		//this.fn = fn;
		
		//should this just be thrown/tried from w/in init()?
		try {
			init(fn);
		} catch (Exception e) {
			System.out.println("Fucked: " + e);
		}
	}
	
	//methods
	/**
	 * Initializes Arc object for dealing with fname
	 * @param fname
	 * @throws Exception
	 */
	public void init(String fname) throws Exception {
		super.init();
		
		this.fn = fname;
		
		try {
			setArcType();
		} catch (Exception e) {
			System.out.println("Fucked: " + e);
			throw new Exception("Problem in Arc.init()");
		}
	}
	
	/**
	 * Sets the archive type depending on fn's extension
	 * @throws Exception
	 */
	public void setArcType() throws Exception {
		if (this.fn.endsWith(RecScan.GZ)) {
			this.arcType = RecScan.GZ;
		} else if (fn.endsWith(RecScan.ZIP)) {
			this.arcType = RecScan.ZIP;
		} else {
			//not implemented
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
