/*
 * RmDirCommand - command to remove directory in current directory
 * I feel like this is the same thing for the RmCommand.
 *
 */
package filesystem.commands;

import filesystem.general.FileSystem;
import filesystem.hierarchy.Directory;
import filesystem.hierarchy.FileSystemObject;

public class RmDirCommand extends AbstractCommand {
    // data
    // -- brings fs down from AbstractCommand

    // methods
    // constructors
    public RmDirCommand() {
        // nothing at this time
    }

    public RmDirCommand(FileSystem fs) {
        this.fs = fs;
    }

    // other methods
    public String execute(String[] params) {
    	/*** Local Variables ***/
        String outputString = null;
        
        /*** Instantiate ***/
        Directory currentDir = fs.getCurrentWorkingDirectory();
        FileSystemObject child = null;
        
    	
        //Goes through the childrens in the current directory
        for (FileSystemObject fso : currentDir.getChildren()) {
        	/*
        	 * 	Checks to see if fso's name equals param[1]
        	 *  Assuming good user input such as 'rm <this cannot be blank>
        	 *  otherwise it would give errors.
        	 *  'instanceof' checks to see if fso is an object Directory.
        	 */
            if (fso.getName().equals(params[1])) 
            {
            	//params[1] is in the current directory so we check
            	// to see if fso in an object Directory.
            	if(fso instanceof Directory) 
            	{
	            	//assigns the child to same name in param[1]
	                child = fso;
	                outputString = "rmDir> " + params[1] + " has been removed.";
	            }
	            else 
	            {
	            	//fso is not an object Directory
	            	outputString = "rmDir>" + params[1] + " is not a directory.";
	            }
            }
            else 
            {
            	//The params[1] is not found in the current directory
            	outputString = "rmDir>" + params[1] + " is not in the current directory.";
            }
        }
        //Gets the child in the current directory and removes it.
        currentDir.getChildren().remove(child);
        

        return outputString;
    }

}    // end - class RmDirCommand
