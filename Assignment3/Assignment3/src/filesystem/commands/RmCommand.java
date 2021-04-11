/*
 * RmCommand - command to remove file in current directory
 */
package filesystem.commands;

import filesystem.general.FileSystem;
import filesystem.hierarchy.Directory;
import filesystem.hierarchy.File;
import filesystem.hierarchy.FileSystemObject;

public class RmCommand extends AbstractCommand {
    // data
    // -- brings fs down from AbstractCommand

    // methods
    // constructors
    public RmCommand() {
        // nothing at this time
    }

    public RmCommand(FileSystem fs) {
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
        	 * 	Checks to see if params[1] is the same as any
        	 *  of the childrens that are in the current directory.
        	 *  Assuming good user input
        	 *	****I've noticed that I can remove directories as well so
        	 *	I have to see if the child that im removing is a file or not***
        	 *  By using 'instanceof', checks to see if fso is an object File.
        	 */
            if (fso.getName().equals(params[1])) 
            {
            	//params[1] is in the current directory so we check
            	// to see if fso in an object File.
            	if(fso instanceof File) 
            	{
	            	//assigns the child to same name in param[1]
	                child = fso;
	                outputString = "rm> " + params[1] + " has been removed.";
            	}
	            else 
	            {
	            	//fso is not an object File
	            	outputString = "rm>" + params[1] + " is not a file.";
	            }
            }
            else 
            {
            	//The params[1] is not found in the current directory
            	outputString = "rm> " + params[1] + " is not in the current directory.";
            }
        }
        //Gets the child in the current directory and removes it.
        currentDir.getChildren().remove(child);     

        return outputString;
    }

}    // end - class RmLinkCommand
