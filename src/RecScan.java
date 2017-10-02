/*
 * This one is gonna be fun.  Working on trying to create something to help me
 * search, extract, delete, and otherwise mess with the contents of archives that may
 * well be recursive (eek)
 * 
 * I think for now we're going to start with just handling tar archive format, with
 * plans to add .zip directory shit, as well.  Compression libraries for starting out
 * will be gzip, bz2, lz4, and rar
 */


public class RecScan {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if ((args.length == 0) || (args.length > 2)) {
			System.out.println("Usage:\trecscan [archive] [match string] " +
					"print fn hits in archive\n" + "\t\trecscan [archive] " +
					"list all archive contents");
			return;
		} else if (args.length == 1) {
			//let's get the entire archive's content list (with all sub-
			//archives)
			
		} else {
			//print every match of userstring from the archive's entire
			//content list
			
		}
	}

}
