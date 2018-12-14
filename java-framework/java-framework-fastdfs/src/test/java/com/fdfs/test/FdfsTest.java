package com.fdfs.test;

import com.java.fdfs.FastDFSClient;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Iterator;
import java.util.Map;

import static org.springframework.util.StringUtils.getFilename;


/**
 * file test
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:spring/spring-fastdfs-test.xml"})
//@ActiveProfiles(profiles={"development"})
public class FdfsTest {

    private final static Logger logger = LoggerFactory.getLogger(FdfsTest.class);


    private static String filePath="/Users/apple/Documents/soft/intellij_workerplace/iot-java/java-framework/java-framework/java-framework-fastdfs/src/test/java/com/fdfs/test/a.jpg";


    public static void main(String[] args) {
        File file = new File(filePath);
        String fid = FastDFSClient.uploadFile(file,file.getName());
        System.out.println("upload local file " + file.getPath() + " ok, fileid=" + fid);
    }

    @Test
    public void updateImage(){
        File file = new File(filePath);
        String fid = FastDFSClient.uploadFile(file,file.getName());
        System.out.println("upload local file " + file.getPath() + " ok, fileid=" + fid);
    }


    @Test
    public void testSock() throws IOException {
        Socket sock = new Socket();
        sock.connect(new InetSocketAddress("images.emomo.cc", 80), 5000);
        System.out.print(sock);
    }

    /**
     * 文件下载测试
     */
    @Test
    public void testDownload() {
        int r = FastDFSClient.downloadFile("group1/M00/00/00/rBKtqlvyo-iAITZ0AABIvZcqB_s315.jpg", new File("/soft/download.jpg"));
        System.out.println(r == 0 ? "下载成功" : "下载失败");
    }

    /**
     * 获取文件元数据测试
     */
    @Test
    public void testGetFileMetadata() {
        Map<String,String> metaList = FastDFSClient.getFileMetadata("group1/M00/00/00/rBJJ2Fq5tW-ACPhKAAEJEbI2xjA754.jpg");
        for (Iterator<Map.Entry<String,String>> iterator = metaList.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String,String> entry = iterator.next();
            String name = entry.getKey();
            String value = entry.getValue();
            System.out.println(name + " = " + value );
        }
    }

    /**
     * 文件删除测试
     */
    @Test
    public void testDelete() {
        int r = FastDFSClient.deleteFile("group1/M00/00/00/rBJJ2Fq5tW-ACPhKAAEJEbI2xjA754.jpg");
        System.out.println(r == 0 ? "删除成功" : "删除失败");
    }




    @Test
    public void token(){
        String token = getToken(filePath,"fastdfs1234567890");
        logger.warn("token：" + token);
    }


    /**
     * get token
     * @param filepath
     * @param httpSecretKey
     * @return
     */
    public String getToken(String filepath, String httpSecretKey){
        //logger.warn("httpSecretKey："+httpSecretKey);
        // unix seconds
        int ts = (int) Instant.now().getEpochSecond();

        // token
        String token = "null";
        try {
            token = ProtoCommon.getToken(getFilename(filePath), ts, httpSecretKey);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        //logger.warn("token：" + token);

        StringBuilder sb = new StringBuilder();
        sb.append("token=").append(token);
        sb.append("&ts=").append(ts);
        return sb.toString();
    }


}
