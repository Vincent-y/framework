package com.java.fdfs;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * fdfs工具类
 */
public class FdfsClient {


    private StorageClient1 storageClient1 = null;
    private TrackerServer trackerServer = null;
    private TrackerClient trackerClient = null;

    public FdfsClient(Properties properties) {
        try {
            ClientGlobal.initByProperties(properties);
            trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
        }
    }

    private void connect(){
        try {
            trackerServer = trackerClient.getConnection();
            if (trackerServer == null) {
                throw new IllegalStateException("getConnection return null");
            }
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer,"group1");
            if (storageServer == null) {
                throw new IllegalStateException("getStoreStorage return null");
            }
            storageClient1 = new StorageClient1(trackerServer,storageServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disConnect(){
        try {
            trackerServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 上传文件
     * @param file 文件对象
     * @param fileName 文件名
     * @return
     */
    public String uploadFile(File file, String fileName) {
        String path =  uploadFile(file,fileName,null);
        return path;
    }


    /**
     * 上传文件
     * @param file 文件对象
     * @param fileName 文件名
     * @param metaList 文件元数据
     * @return
     */
    public String uploadFile(File file, String fileName, Map<String,String> metaList) {
        try {
            connect();
            byte[] buff = IOUtils.toByteArray(new FileInputStream(file));
            NameValuePair[] nameValuePairs = null;
            if (metaList != null) {
                nameValuePairs = new NameValuePair[metaList.size()];
                int index = 0;
                for (Iterator<Map.Entry<String,String>> iterator = metaList.entrySet().iterator(); iterator.hasNext();) {
                    Map.Entry<String,String> entry = iterator.next();
                    String name = entry.getKey();
                    String value = entry.getValue();
                    nameValuePairs[index++] = new NameValuePair(name,value);
                }
            }
            String path = storageClient1.upload_file1(buff,FilenameUtils.getExtension(fileName),nameValuePairs);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            disConnect();
        }
        return null;
    }

    /**
     * 上传文件
     * @param fileName
     * @param extName
     * @param bytes
     * @return
     */
    public String uploadFile(String fileName,String extName,byte[] bytes){
        connect();
        NameValuePair[] metaArr = new NameValuePair[3]; //描述信息
        metaArr[0] = new NameValuePair("fileName", fileName);
        metaArr[1] = new NameValuePair("fileExt", extName);
        metaArr[2] = new NameValuePair("fileSize", String.valueOf(bytes.length));
        String path = null; //返回存储的文件路径
        try {
            path = storageClient1.upload_file1(bytes, extName, metaArr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            disConnect();
        }
        return path;
    }


    /**
     * 获取文件元数据
     * @param fileId 文件ID
     * @return
     */
    public Map<String,String> getFileMetadata(String fileId) {
        try {
            NameValuePair[] metaList = storageClient1.get_metadata1(fileId);
            if (metaList != null) {
                HashMap<String,String> map = new HashMap<String, String>();
                for (NameValuePair metaItem : metaList) {
                    map.put(metaItem.getName(),metaItem.getValue());
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     * @param fileId 文件ID
     * @return 删除失败返回-1，否则返回0
     */
    public int deleteFile(String fileId) {
        try {
            return storageClient1.delete_file1(fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 下载文件
     * @param fileId 文件ID（上传文件成功后返回的ID）
     * @param outFile 文件下载保存位置
     * @return
     */
    public int downloadFile(String fileId, File outFile) {
        FileOutputStream fos = null;
        try {
            byte[] content = storageClient1.download_file1(fileId);
            fos = new FileOutputStream(outFile);

            InputStream input = new ByteArrayInputStream(content);
            IOUtils.copy(input,fos);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }



}
