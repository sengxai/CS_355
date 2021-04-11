/*
 * class Directory - represent directory type of file system object
 * 
 * Created - Paul J. Wagner, 4/10/2013
 * Modified - Paul J. Wagner, 4/24/2013 - updated comments
 */
package filesystem.hierarchy;

import java.util.ArrayList;

/**
 * @author paul
 *
 */
public class Directory extends FileSystemObject {
	// data
	ArrayList<FileSystemObject> children;		// list of children file system objects
	
	// methods
	// constructors
	// -- default constructor
	public Directory() {
		this("default");
	}
	
	// -- name constructor
	public Directory(String name) {
		this.name = name;
		this.size = 0;
		children = new ArrayList<FileSystemObject>();
	}

	// other methods
	// -- getChildren() - get the children list of this directory
	public ArrayList<FileSystemObject> getChildren() {
		return children;
	}
	
	// -- addChild(child) - add a child to this directory
	public void addChild (FileSystemObject child) {
		children.add(child);
	}
	
	// -- countChildren - count the number of children in the directory
	public int countChildren() {
		return children.size();
	}
	
	// -- toString - convert the directory to a string
	public String toString() {
		return "dir : " + name + "\n";
	}
	
	// getSize -- gets the size of each children (recursively)
	// and adds them together to get the current size
	public int getSize() {
		
		/*** Local Variables ***/
		
		int totalSize = 0;

        for (FileSystemObject fso : children) {
            totalSize += fso.getSize();
        }

        return totalSize;
	}
		
	// -- display() - display this directory and children (possibly recursively) to a String
	public String display(int level) {
		
		/*** Local Variables ***/
		
		String displayString = "";

		displayString += printHelper.genSpaces(level);
		displayString += (name + "/" + "\n");
		for (FileSystemObject fso : children) {  
			displayString += fso.display(level + 1);
		}

		return displayString;
	}
	
}	// end - class Directory
