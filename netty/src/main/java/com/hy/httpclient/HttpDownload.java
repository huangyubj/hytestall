package com.hy.httpclient;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpDownload {
    public static final int cache = 10 * 1024;
    public static final boolean isWindows;
    public static final String splash;
    public static final String root;
    static {
        if (System.getProperty("os.name") != null && System.getProperty("os.name").toLowerCase().contains("windows")) {
            isWindows = true;
            splash = "\\";
            root = "D:";
        } else {
            isWindows = false;
            splash = "/";
            root = "/search";
        }
    }

    public static void downLoad(String uri, String filePath){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = null;
        InputStream inputStream = null;
        FileOutputStream fileout = null;
        try {

            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();
            if (filePath == null){
                filePath = getFilePath(response);
            }
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            fileout = new FileOutputStream(file);
            /**
             * 根据实际运行效果 设置缓冲区大小
             */
            byte[] buffer = new byte[cache];
            int ch = 0;
            while ((ch = inputStream.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch);
            }
            fileout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(client != null)
                    client.close();
                if(response != null)
                    response.close();
                if(inputStream != null)
                    inputStream.close();
                if(fileout != null)
                    fileout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 获取response要下载的文件的默认路径
     *
     * @param response
     * @return
     */
    public static String getFilePath(CloseableHttpResponse response) {
        String filepath = root + splash;
        String filename = getFileName(response);

        if (filename != null) {
            filepath += filename;
        } else {
            filepath += getRandomFileName();
        }
        return filepath;
    }

    /**
     * 获取response header中Content-Disposition中的filename值
     *
     * @param response
     * @return
     */
    public static String getFileName(CloseableHttpResponse response) {
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        String filename = null;
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    try {
                        //filename = new String(param.getValue().toString().getBytes(), "utf-8");
                        //filename=URLDecoder.decode(param.getValue(),"utf-8");
                        filename = param.getValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return filename;
    }
    /**
     * 获取随机文件名
     *
     * @return
     */
    public static String getRandomFileName() {
        return String.valueOf(System.currentTimeMillis());
    }
}
