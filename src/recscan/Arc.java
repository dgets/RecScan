package recscan;

/**
 * 
 * @author Damon Getsman
 *
 */
public class Arc extends ArcStruct {
	//fields
	private String fn	=	new String();
	
	//accessors
	public String getFn() {
		return this.fn;
	}
	
	public void setFn(String fname) {
		this.fn = fname;
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
	 * 
	 * @param fname
	 * @throws Exception
	 */
	public void init(String fname) throws Exception {
		this.fn = fname;
		
		try {
			setArcType();
		} catch (Exception e) {
			System.out.println("Fucked: " + e);
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void setArcType() throws Exception {
		if (fn.endsWith(RecScan.GZ)) {
			
		}
	}
}
