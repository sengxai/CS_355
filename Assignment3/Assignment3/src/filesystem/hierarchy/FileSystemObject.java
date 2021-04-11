/* 
 * class FileSystemObject - abstract composite class for each general file system object
 * 
 * Created - Paul J. Wagner, 4/10/2013
 * Modified - Paul J. Wagner, 4/24/2014 - additional comments
 */
package filesystem.hierarchy;

import filesystem.utilities.PrintHelper;

public abstract class FileSystemObject {
	// data
	protected String name;			// file system object name
	protected int    size;			// file system object size
	protected PrintHelper printHelper = new PrintHelper();	// print helper for file system display
	
	// methods
	// constructors
	// -- default constructor
	public FileSystemObject() {
		name = "default";
		size = 0;
	}

	// -- all-arg constructor
	public FileSystemObject(String name, int size) {
		this.name = name;
		this.size = size;
	}

	// other methods	
	// -- display() - generate display string from file system traversal
	public abstract String display(int level);
	
	// -- getName() - get the file system object name
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return size;
	}
	
}	// end - class FileSystemObject
