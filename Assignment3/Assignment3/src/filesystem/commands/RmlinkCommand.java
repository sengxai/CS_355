/*
 * RmlinkCommand - command to remove link in current directory
 */
package filesystem.commands;

import filesystem.general.FileSystem;
import filesystem.hierarchy.Directory;
import filesystem.hierarchy.FileSystemObject;
import filesystem.hierarchy.Link;

public class RmlinkCommand extends AbstractCommand {
    // data
    // -- brings fs down from AbstractCommand

    // methods
    // constructors
    public RmlinkCommand() {
        // nothing at this time
    }

    public RmlinkCommand(FileSystem fs) {
        this.fs = fs;
    }

    // other methods
    public String execute(String[] params) {
    	
        String outputString = null;
    	Directory currentDir = fs.getCurrentWorkingDirectory();
        FileSystemObject child = null;
        
        //Goes through the childrens in the current directory.
        for (FileSystemObject fso : currentDir.getChildren()) {
        	
        	/* Checks to see if the name of params[1] is the same as
        	 * any of the childrens that are in the current
        	 * directory. Then checks to see if fso is an object Link or
        	 * not through 'instanceof'.
        	 */
            if (fso.getName().equals(params[1])) 
            {
            	//params[1] is in the current directory so we check
            	// to see if fso in an object Link.
            	if(fso instanceof Link) 
            	{
	            	//assigns the child to same name in params[1]
	            	child = fso;
	                outputString = "rmlink> " + params[1] + " has been removed.";
            	}
	            else 
	            {
	            	//fso is not an object Link
	            	outputString = "rmlink> " + params[1] + " is not a link.";
	            }
            }
            else
            {
            	//The params[1] is not found in the current directory
            	outputString = "rmlink> " + params[1] + " is not in the current directory.";
            }
        }

        	//Gets and removes the child in the current directory.  
            currentDir.getChildren().remove(child);

        return outputString;
    }

}    // end - class RmlinkCommand
