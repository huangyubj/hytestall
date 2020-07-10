package com.hy.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Demo {
    @Test
    public void httpGetDemo(){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("192.168.6.27")
                    .setPort(6030)
                    .setPath("/portals")
//                    .setParameter()
//                    .setParameters()
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String uriStr = "http://192.168.6.27:6030/portals";
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = null;
        try {
//            httpGet.setHeader("Content-Type", "application/json;charset=utf8");
            RequestConfig config = RequestConfig.custom().
                    setSocketTimeout(6000)
                    .setConnectTimeout(6000).build();
            httpGet.setConfig(config);
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            System.out.println("状态" + statusLine);
            if(entity != null){
                System.out.println(entity.getContentLength());
                System.out.println(EntityUtils.toString(entity));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(client != null)
                    client.close();
                if(response != null)
                    response.close();;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void httpGetPost(){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("192.168.6.27")
                    .setPort(6030)
                    .setPath("/portals")
//                    .setParameter()
//                    .setParameters()
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String uriStr = "http://192.168.6.27:6030/portals";
        HttpPost httpPost = new HttpPost(uri);
        CloseableHttpResponse response = null;
        try {
            RequestConfig config = RequestConfig.custom().
                    setSocketTimeout(6000)
                    .setConnectTimeout(6000).build();
            httpPost.setConfig(config);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            StringEntity stringEntity = new StringEntity("{a:1}", "UTF-8");
            httpPost.setEntity(stringEntity);
            response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            System.out.println("状态" + statusLine);
            if(entity != null){
                System.out.println(entity.getContentLength());
                System.out.println(EntityUtils.toString(entity));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(client != null)
                    client.close();
                if(response != null)
                    response.close();;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
