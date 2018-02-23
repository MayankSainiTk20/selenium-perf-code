package com.intuit.ui.perf.utilities;



import java.io.File;
import java.util.ArrayList;
 

public class DirectoryReader {
 
    /**
     * List all the files and folders from a directory
     * @param directoryName to be listed
     */
    public void listFilesAndFolders(String directoryName){
 
        File directory = new File(directoryName);
 
        //get all the files from a directory
        File[] fList = directory.listFiles();
 
        for (File file : fList){
            System.out.println(file.getName());
        }
    }
 
    /**
     * List all the files under a directory
     * @param directoryName to be listed
     */
    public ArrayList<String> listFiles(String directoryName){
    	ArrayList<String> ar = new ArrayList<String>();
        File directory = new File(directoryName);
 
        //get all the files from a directory
        File[] fList = directory.listFiles();
 
        for (File file : fList){
            if (file.isFile()){
            	if(file.getName()!=null){
            		
            
            	if(file.getName().contains("har")){
            		ar.add(file.getName());
            		 System.out.println(file.getName());
            	}
            	}
            }
        }
		return ar;
    }
 
    /**
     * List all the folder under a directory
     * @param directoryName to be listed
     */
    public void listFolders(String directoryName){
 
        File directory = new File(directoryName);
 
        //get all the files from a directory
        File[] fList = directory.listFiles();
 
        for (File file : fList){
            if (file.isDirectory()){
                System.out.println(file.getName());
            }
        }
    }
 
    /**
     * List all files from a directory and its subdirectories
     * @param directoryName to be listed
     */
    public void listFilesAndFilesSubDirectories(String directoryName){
 
        File directory = new File(directoryName);
 
        //get all the files from a directory
        File[] fList = directory.listFiles();
 
        for (File file : fList){
            if (file.isFile()){
                System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()){
                listFilesAndFilesSubDirectories(file.getAbsolutePath());
            }
        }
    }
 
    public static void main (String[] args){
 
    	DirectoryReader listFilesUtil = new DirectoryReader();
 
        final String directoryLinuxMac ="/Users/ssinghthind/sam/_har";
 
        //Windows directory example
        //final String directoryWindows ="C://test";
 
     ArrayList<String> ar =    listFilesUtil.listFiles(directoryLinuxMac);
     System.out.println(ar.size());
    }
}
