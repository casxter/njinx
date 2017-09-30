package org.njinx;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author WallaceTang
 */
public class Request {

    private InputStream input;
    private String filePath;
    private String fileName;
    private String fileExt;

    public Request(InputStream input) {
        this.input = input;
    }

    //从InputStream中读取request信息，并从request中获取uri值
    public void parse() {
        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }
        System.out.print(request.toString());
        filePath = parseFilePath(request.toString());
        fileName = parseFileName(filePath);
        fileExt = parseFileExtName(fileName);
    }

    /**
     * requestString形式如下：
     * GET /index.html HTTP/1.1
     * Host: localhost:8080
     * Connection: keep-alive
     * Cache-Control: max-age=0
     * ...
     * 该函数目的就是为了获取 /index.html
     */
    private String parseFilePath(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1)
                return requestString.substring(index1 + 1, index2);
        }
        return null;
    }

    /**
     * 获取文件路径
     *
     * @param filePath
     * @return
     */
    private String parseFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf('/') + 1);
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    private String parseFileExtName(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }


    public String getFilePath() {
        return filePath;
    }

}