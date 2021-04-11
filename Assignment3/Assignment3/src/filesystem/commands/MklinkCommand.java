/*
 * MkfileCommand - command to make file in current directory
 *
 * Created - Paul J. Wagner, 4/10/2013, format: mkfile name size contents
 * Modified - Paul J. Wagner, 4/24/2014 - changed to return output as string
 */
package filesystem.commands;

import filesystem.general.FileSystem;
import filesystem.hierarchy.*;

public class MklinkCommand extends AbstractCommand {
    // data
    // -- brings fs down from AbstractCommand

    // methods
    // constructors
    public MklinkCommand() {
        // nothing at this time
    }

    public MklinkCommand(FileSystem fs) {
        this.fs = fs;
    }

    // other methods
    public String execute(String[] params) {
        String outputString;
    	Directory currentDir = fs.getCurrentWorkingDirectory();
        FileSystemObject child = null;

        /*** Goes through all the children within the current
         *   directory. Then checks if param[2] is the same as
         *   one of the childrens that are within the current
         *   directory.
         */
        for (FileSystemObject fso : currentDir.getChildren()) {
            if (fso.getName().equals(params[2])) {
            	//then assigns fso to child so we can add the child later
                child = fso;
            }
        }

        /*** 
		 * 	Now we just add the child into the current directory
		 *  as a Link.
         */
        currentDir.addChild(new Link(params[1], child));
        outputString = "mklink> " + params[1] + " link has been added.";
        

        return outputString;
    }

}    // end - class MkfileCommand
