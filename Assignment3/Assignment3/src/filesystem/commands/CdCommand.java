/*
 * CDCommand - command to change directory to a specified directory
 *
 * Created - Paul J. Wagner, 4/27/2014 - format: cd newdir, cd .., or cd ~ (root)
 * Modified -
 */
package filesystem.commands;

import filesystem.general.FileSystem;
import filesystem.hierarchy.Directory;
import filesystem.hierarchy.File;

public class CdCommand extends AbstractCommand {
    // data
    // -- brings fs down from AbstractCommand

    // methods
    // constructors
    public CdCommand() {
        // nothing at this time
    }

    public CdCommand(FileSystem fs) {
        this.fs = fs;
    }

    // other methods
    public String execute(String[] params) {
    	/*** Local Variables ***/
    	String outputString = null;
        String cwd = fs.setCurrentWorkingDirectory(params[1]);
    	
        
        if (cwd != null) {
        	outputString = "cd> cwd changed to " + params[1];
        } 
        else {
            outputString = "cd> error: " + params[1] + " is not a directory.";
        }
        
        return outputString;
    }

}    // end - class CdCommand
