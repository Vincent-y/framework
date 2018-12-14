package com.java.hdfs;

import com.java.validate.Validate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * hdfs
 */
public class HdfsUtil {

    private static final Logger logger = LoggerFactory.getLogger(HdfsUtil.class);

    public static final int BUFF_SIZE    = 4096;
    public static final boolean IS_CLOSE = true;

    private String hdfsUrl;
    private FileSystem fileSystem;

    public HdfsUtil(String hdfsUrl) {
        this.hdfsUrl = hdfsUrl;
        try {
            this.fileSystem = FileSystem.get(new URI(hdfsUrl), new Configuration());
        } catch (IOException | URISyntaxException e) {
            logger.error("init error", e);
        }
    }

    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public boolean uploadData(String localFilePath, String remoteDirector) throws IOException, FileNotFoundException {
        if (Validate.isEmpty(localFilePath)) {
            return false;
        }
        if (Validate.isEmpty(remoteDirector)) {
            return false;
        }
        try {
            remoteDirector = getHdfsPath(remoteDirector);
            // 上传文件
            final FSDataOutputStream out = this.fileSystem.create(new Path(remoteDirector)); //上传后的文件命令名
            FileInputStream in = new FileInputStream(localFilePath); //上传的文件
            IOUtils.copyBytes(in, out, BUFF_SIZE, IS_CLOSE);
        } catch (Exception e) {
            logger.error("upload error", e);
            return false;
        }
        return true;
    }


    public boolean makeDirectory(String remoteDirector) throws IOException {
        try {
            // 创建文件夹
            remoteDirector = getHdfsPath(remoteDirector);
            fileSystem.mkdirs(new Path(remoteDirector));
        } catch (Exception e) {
            logger.error("upload error", e);
            return false;
        }
        return true;
    }

    public void downloadData(String downloadUrl,String localPath) throws IOException {
        if (Validate.isEmpty(downloadUrl)) {
            return;
        }
        if (Validate.isEmpty(localPath)) {
            return;
        }

        final FSDataInputStream in = fileSystem.open(new Path(downloadUrl));
        FileOutputStream output = openOutputStream(new File(localPath));
        IOUtils.copyBytes(in, output, BUFF_SIZE, IS_CLOSE);
    }


    public boolean deleteFile(String fileUrl) throws IOException {
        return fileSystem.delete(new Path(fileUrl), IS_CLOSE);
    }


    private String getHdfsPath(String remoteDirector) {
        if (!remoteDirector.startsWith("/")) {
            remoteDirector = "/" + remoteDirector;
        }
        remoteDirector = hdfsUrl + remoteDirector;
        return remoteDirector;
    }


    private FileOutputStream openOutputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && parent.exists() == false) {
                if (parent.mkdirs() == false) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file);
    }


}
