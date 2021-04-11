/*
 * class FileSystem - overall class for holding the entire file system
 *
 * Created - Paul J. Wagner, 4/11/2013
 * Modified - Paul J. Wagner, 4/24/2014 - updated comments
 */
package filesystem.general;

import java.util.ArrayList;

import filesystem.hierarchy.Directory;
import filesystem.hierarchy.FileSystemObject;
import filesystem.hierarchy.Link;

public class FileSystem {
    // data
    private Directory root;                            // root of the file system
    private String currentWorkingDirectoryString;    // current working directory as String in the file system
    private Directory currentWorkingDirectory;        // current working directory object
    private ArrayList<Directory> parentDirectoryStack;    // stack of parent directories
    private int parentDirectoryStackTop = -1;        // top of parent directory stack

    // methods
    // constructors
    // -- default constructor
    public FileSystem() {
        root = new Directory("");
        currentWorkingDirectoryString = "/";
        currentWorkingDirectory = root;
        parentDirectoryStack = new ArrayList<Directory>();
        parentDirectoryStack.add(new Directory("Dummy"));
        parentDirectoryStackTop = 0;
    }

    // other methods
    // -- toString() - overall top-level file system display
    public String toString() {
        String outputString = "";
        outputString += "fs: cwd is: " + currentWorkingDirectoryString;
        return outputString;
    }

    public String genDisplay() {
        String displayString = "";
        displayString = root.display(0);
        return displayString;
    }

    // -- getCurrentWorkingDirectoryString - return the current working directory as a string
    public String getCurrentWorkingDirectoryString() {
        return currentWorkingDirectoryString;
    }

    // -- getCurrentWorkingDirectory - return the current working directory as a Directory object
    public Directory getCurrentWorkingDirectory() {
        return currentWorkingDirectory;
    }

    // -- setCurrentWorkingDirectory - set the current working directory and string to the new directory
    // TODO: allow return of error (code or text) if problems
    public String setCurrentWorkingDirectory(String newDirString) {
        if (newDirString.charAt(0) == '.' && newDirString.charAt(1) == '.') {        // .. - change to parent
            // TODO: change to parent (if not already at root)
            if (!currentWorkingDirectoryString.equals("/")) {
                int lastSlashPos = currentWorkingDirectoryString.lastIndexOf('/');
                currentWorkingDirectoryString = currentWorkingDirectoryString.substring(0, lastSlashPos);
                if (currentWorkingDirectoryString.equals("")) {
                    currentWorkingDirectoryString = "/";
                }
                currentWorkingDirectory = parentDirectoryStack.get(parentDirectoryStackTop);
                parentDirectoryStack.remove(parentDirectoryStackTop);
                parentDirectoryStackTop--;
                return currentWorkingDirectoryString;
            } else {
                return null;
            }
        } else if (newDirString.charAt(0) == '~') {        // ~ - change to root
            currentWorkingDirectoryString = "/";
            currentWorkingDirectory = root;
            parentDirectoryStack.clear();
            parentDirectoryStack.add(new Directory("Dummy"));
            parentDirectoryStackTop = 0;
            return currentWorkingDirectoryString;
        } else {                                        // change to specified sub-directory if it exists
            // if child exists
            // TODO: find child with this name and change to it
            ArrayList<FileSystemObject> children = currentWorkingDirectory.getChildren();
            int index = 0;
            FileSystemObject child = null;
            while (index < children.size()) {
                FileSystemObject childObj = children.get(index);
                //System.out.println("checking child: " + child.getName());
                if (childObj.getName().equals(newDirString)) {
                    if (childObj instanceof Link) {
                        child = ((Link) childObj).getLinkedObject();
                    } else {
                        child = childObj;
                    }
                    //System.out.println("found dir: " + child.getName());
                    break;
                }
                index++;
            }
            if (!(child instanceof Directory)) {
                return null;
            } else {
                parentDirectoryStack.add(currentWorkingDirectory);
                parentDirectoryStackTop++;
                currentWorkingDirectory = (Directory) child;
                if (currentWorkingDirectoryString.equalsIgnoreCase("/")) {
                    currentWorkingDirectoryString = currentWorkingDirectoryString + newDirString;
                } else {
                    currentWorkingDirectoryString = currentWorkingDirectoryString + "/" + newDirString;
                }
            }
            return currentWorkingDirectory.getName();
        }
    }

    // -- findDir - find a given directory by name

    // -- getRoot - return the root directory of the file system
    public Directory getRoot() {
        return root;
    }

}    // end - class FileSystem
