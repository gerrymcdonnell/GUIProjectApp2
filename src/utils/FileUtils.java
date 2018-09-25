/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Color;
import java.io.File;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author gerry
 */
public class FileUtils {

    //is the filename a validimage
    public static boolean bisFileAnImage(String sFile) {
        if (sFile.endsWith(".jpg") || sFile.endsWith(".gif")) {
            return true;
        } else {
            return false;
        }
    }

    public static String saveFileDialog() {
        JFileChooser fc;
        fc = new JFileChooser();

        FileFilter filterAlbumFiles = new ExtensionFilter("Image files", new String[]{".jpg", ".gif", "jpeg"});
        fc.setFileFilter(filterAlbumFiles);
        int returnVal = fc.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would save the file.
            System.out.println("Saving: " + file.getName());
            return file.toString();
        } else {
            System.out.println("Save command cancelled by user.");
            return "";
        }
    }//end

    //load the file open dialog
    public static String openFileDialog(boolean imgFilter)//flag for saying we want to have the filter for album files or just images
    {
        JFileChooser fc;
        fc = new JFileChooser();

        FileFilter filterImgFiles = new ExtensionFilter("Image files", new String[]{".jpg", ".gif", "jpeg"});
        FileFilter filterXmlFiles = new ExtensionFilter("Album files", new String[]{".xml"});

        //Uncomment one of the following lines to try a different
        //file selection mode.  The first allows just directories
        //to be selected (and, at least in the Java look and feel,
        //shown).  The second allows both files and directories
        //to be selected.  If you leave these lines commented out,
        //then the default mode (FILES_ONLY) will be used.
        //
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);





        if (imgFilter == true) {
            fc.addChoosableFileFilter(filterImgFiles);
        } else {
            fc.addChoosableFileFilter(filterXmlFiles);
        }


        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //System.out.println("get path " + file.getAbsoluteFile());
            //This is where a real application would open the file.
            System.out.println("File " + file.toString());
            return file.toString();//returns file name eg G:\wallpapers\AG-PhotoCollection-04_www.softarchive.net\AG-PhotoCollection-04 (1).jpg
        } else {
            return "";
        }

    }//end open dialog

    public static Color ShowColorDialog() {
        Color newColor = JColorChooser.showDialog(null, "Choose Background Color", null);
        return newColor;
    }
}
