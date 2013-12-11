/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language;

import java.io.File;

/**
 *
 * @author Reynold
 */
public interface UIFrameListener {
    
    File getFirstFile();
    File getNextFile();
    boolean updateFileName(File toUpdate,String withThis);
    String tryWithThis(String edited);
    void finish();
    int getNumOfFiles();
    int getFileNumber();
    int getFileRemaining();
    File [] getAllFiles();
    
}
