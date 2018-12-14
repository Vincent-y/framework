package com.java.zk;

import java.io.IOException;

public interface ZkClient {

    void init(String hostPort, String parentNodeName) throws IOException;

    String createNode(String nodeName);

    void close();

    void dowatch();

}
