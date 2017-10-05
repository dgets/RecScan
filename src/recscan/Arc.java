package recscan;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
	 * @param fname String - filename of the archive
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
