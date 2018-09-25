/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.swing.JOptionPane;

/**
 *
 * @author gerry
 */
public class GeneralMethods {

    public static void printString(String s) {
        System.out.println(s);
    }

    //inputbox
    public static String sInputBox(String sTitle, String sPrompt) {
        String s="";
        s=JOptionPane.showInputDialog(null, sTitle, sPrompt, JOptionPane.QUESTION_MESSAGE);
        return s;
    }

    public static void showMsgBox(String sMsg)
    {
        JOptionPane.showMessageDialog(null,sMsg);
    }


    public static int StringToInt(String s)
    {
        try{
            int itemp;
            itemp= Integer.parseInt(s);
            return itemp;
        }
        catch(Exception NumberFormatException)
        {
            return -1;
        }
    }
}
