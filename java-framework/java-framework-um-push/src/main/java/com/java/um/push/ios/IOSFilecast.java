package com.java.um.push.ios;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.java.um.push.IOSNotification;
import org.apache.commons.codec.digest.DigestUtils;


public class IOSFilecast extends IOSNotification {
	public IOSFilecast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "filecast");	
	}
	
	public void setFileId(String fileId) throws Exception {
    	setPredefinedKeyValue("file_id", fileId);
    }
}
