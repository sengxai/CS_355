/*
 * CatCommand - command to display file content from current directory

 */
package filesystem.commands;

import filesystem.general.FileSystem;
import filesystem.hierarchy.Directory;
import filesystem.hierarchy.File;
import filesystem.hierarchy.FileSystemObject;
import filesystem.hierarchy.Link;

public class CatCommand extends AbstractCommand {
    // data
    // -- brings fs down from AbstractCommand

    // methods
    // constructors
    public CatCommand() {
        // nothing at this time
    }

    public CatCommand(FileSystem fs) {
        this.fs = fs;
    }

    // other methods
    public String execute(String[] params) {
    	
    	/*** Local Variables ***/
    	
    	String outputString = null;
        Directory currentDir = fs.getCurrentWorkingDirectory();
        FileSystemObject child = null;

        
        for (FileSystemObject fso : currentDir.getChildren()) {
        	/*
        	 * 	Checks to see if fso's name equals param[1]
        	 *  Assuming good user input
             *  'instanceof' checks to see if fso is a file,
             *  directory, or a link.
        	 */
            if (fso.getName().equals(params[1])) {
                if (fso instanceof Link) {
                	//Casts fso to be a Link so we can get the object
                	//that we linked it to
                	fso = ((Link) fso).getLinkedObject();
                }
                if (fso instanceof File) {
                	//assigns the child to same name in param[1]
                    child = fso;
                    
                    // Cast the child into a file be
                    File file = (File) child;
                    outputString = file.getContents();
                    /*** When I try to display the contents it just shows
                     *  like the first or just 3 characters only
                     *  for the premade files in the txt file.
                     *  But I can display the full content
                     *  from files that I make manually.
                     */
                }
                if(fso instanceof Directory) {
                	outputString = "Error this is a Directory";
                }
            }
        }

        //System.out.println(outputString);
        return outputString;
        
    }

}    // end - class CatCommand
