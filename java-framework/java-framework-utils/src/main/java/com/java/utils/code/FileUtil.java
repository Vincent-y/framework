package com.java.utils.code;

import java.io.*;

/**
 * <p>文件
 * <p>@author DRAGON
 * <p>@date 2015年5月22日
 * <p> @version 1.0
 */
public class FileUtil {
	/**
	 * Java文件操作 获取文件扩展名
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * Java文件操作 获取不带扩展名的文件名
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}
	
	/**
	 * 读文件
	 * @param path
	 * @return
	 */
	public static byte[] readFile(String path) {  
        FileInputStream fileInputStream                 = null;  
        BufferedInputStream bufferedInputStream         = null;
        ByteArrayOutputStream bao                       = new ByteArrayOutputStream();  
        byte[] buff                                     = new byte[1024];
        try {  
            fileInputStream          = new FileInputStream(path); 
            bufferedInputStream      = new BufferedInputStream(fileInputStream);
            int bytesRead            =0;  
            
            while (-1!=(bytesRead=bufferedInputStream.read(buff,0,buff.length))) {  
                bao.write(buff,0,bytesRead);  
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                fileInputStream.close();  
                bufferedInputStream.close();  
                buff=null;  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return bao.toByteArray();  
    }  
	
	/**
	 *  写文件
	 * @param string
	 * @param bytes
	 */
	 public static void writeFile(String string,byte[] bytes) {  
	        FileOutputStream fileOutputStream        = null;
	        BufferedOutputStream bufferedOutputStream= null;  
	        try{  
	            fileOutputStream     =new FileOutputStream(string);
	            bufferedOutputStream =new BufferedOutputStream(fileOutputStream);  
	            bufferedOutputStream.write(bytes);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }finally{  
	            try {  
	                bufferedOutputStream.flush();  
	                fileOutputStream.close();  
	                bufferedOutputStream.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	
	
	
}
