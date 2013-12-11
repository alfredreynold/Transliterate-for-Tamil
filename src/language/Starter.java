/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import javax.swing.JOptionPane;

/**
 *
 * @author Reynold
 */
public class Starter implements UIFrameListener{
    
    private static String url;
    private static Language l;
    private static StringBuilder sb;
    private static File[] listFiles;
    private static int fileNo=0;
    private static UIFrame frame;
    private static Starter myStarter;
    
    
    public static void main(String [] args){
        url=JOptionPane.showInputDialog("Enter Folder URL ");
        if(url!=null&&!"".equals(url)){
            File myFile=new File(url);
            if(myFile.exists()){
                try {
                    myStarter=new Starter();
                    myStarter.cycleThroURL(myFile);
                    l=new Language();
                    sb=new StringBuilder();
                    myStarter.startNow();
                } catch (FileNotFoundException ex) {
                    myStarter=null;
                    myFile=null;
                    JOptionPane.showMessageDialog(null, "Closing \n Folder Empty", "No Input", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "No such Directory!!", "Error", JOptionPane.ERROR_MESSAGE);
                //System.exit(0);
            }
        } else{
            JOptionPane.showMessageDialog(null, "Closing", "No Input", JOptionPane.INFORMATION_MESSAGE);
            //System.exit(0);
        }
    }
    
    
    private void startNow(){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame=new UIFrame();
                UIFrame.lookAndFeel();
                frame.setVisible(true);
                frame.setListener(myStarter);
            }
        });        
    }
    
    public void cycleThroURL(File folder) throws FileNotFoundException{
        listFiles=folder.listFiles(new myFileNameFilter());
        //System.out.println("Length:  "+listFiles.length);
        if(listFiles.length==0){
            throw new FileNotFoundException();
        }
    }
       
    @Override
    public File getNextFile() {
        File name=null;
        if(fileNo<listFiles.length){
            name = listFiles[fileNo];
            fileNo++;
        }
        return name;
    }
    
    @Override
    public String tryWithThis(String edited) {
        sb=new StringBuilder();
        edited=edited.trim();
        String [] arrS=edited.split(" +");
            for(String e:arrS){
                e=e.trim();
                //System.out.println("letters: "+e);
                String fi=l.convert(e);
                sb.append(fi);
                sb.append(" ");
            }
        return sb.toString();
    }

    @Override
    public boolean updateFileName(File toUpdate,String withThis) {
        boolean renameTo = toUpdate.renameTo(new File(url+"\\"+withThis+".mp3"));
        return renameTo;
    }

    @Override
    public File getFirstFile() {
//        System.out.println("File_No: "+fileNo);
        if(fileNo<listFiles.length){
            File name = listFiles[fileNo];
            fileNo++;
            return name;
        }
        
        return null;
    }

    @Override
    public void finish() {
        frame.removeAll();
        frame.setVisible(false);
        frame.dispose();
        listFiles=null;
        l=null;
        sb=null;
    }

    @Override
    public int getNumOfFiles() {
        return listFiles.length;
    }

    @Override
    public int getFileNumber() {
        return fileNo;
    }

    @Override
    public int getFileRemaining() {
        return listFiles.length-(fileNo);
    }

    @Override
    public File[] getAllFiles() {
        return listFiles;
    }
    
    private class myFileNameFilter implements FilenameFilter{

        @Override
        public boolean accept(File dir, String name) {
            
            return name.endsWith(".mp3")?true:name.endsWith(".MP3")?true:name.endsWith(".wma")?true:name.endsWith(".wav");
        }
        
    }
    
    
}
