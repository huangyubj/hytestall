package com.hy.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
//import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class VideoVodApiOut {
    private static final String URL_STR = "http://xes-video-slice-ali.oss-cn-beijing.aliyuncs.com";
    @Test
    public void httpGetDemo(){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        String uriStr = "http://v.xueersi.com/api/vod/upload";
        URI uri = null;
        try {
            // 将参数放入键值对类NameValuePair中,再放入集合中
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", "admin"));
            params.add(new BasicNameValuePair("filename", "test07102352.mp4"));
            params.add(new BasicNameValuePair("label", "test"));
            params.add(new BasicNameValuePair("type", "100"));
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("v.xueersi.com")
                    .setPath("/api/vod/upload")
//                    .setParameter()
                    .setParameters(params)
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
//        HttpGet httpGet = new HttpGet(uriStr);
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
    public void httpPost(){
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
        String uriStr = "http://101.200.173.176/mediaauth/uploadAuth";
        HttpPost httpPost = new HttpPost(uriStr);
        CloseableHttpResponse response = null;
        try {
            RequestConfig config = RequestConfig.custom().
                    setSocketTimeout(6000)
                    .setConnectTimeout(6000).build();
//            httpPost.setConfig(config);
            httpPost.setHeader("Content-Type", "text/plain; charset=utf-8");
            JSONObject jsonObject = new JSONObject();
//            {
//                "UserId": 10002,
//                    "AuthKey": "3e288e31013683f1229b18079c491e50;1591606976",
//                    "Path": "xxTest0xxTe06081801.txt"
//            }
            jsonObject.put("UserId",10002);
            jsonObject.put("AuthKey","3e288e31013683f1229b18079c491e50;1591606976");
            jsonObject.put("Path","xxTest0xxTe06081801.txt");
            StringEntity stringEntity = new StringEntity(jsonObject.toJSONString(), "UTF-8");
            httpPost.setEntity(stringEntity);
            response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            System.out.println("状态" + statusLine);
            if(entity != null){
                System.out.println(entity.getContentLength());
                String responseStr = EntityUtils.toString(entity);
                System.out.println(responseStr);
                JSONObject object = JSON.parseObject(responseStr);
                JSONObject dataObj = object.getJSONObject("Data").getJSONObject("Body");
                upload("d://video_vod_api_out.py", dataObj);
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
//    @Test
    public void upload(String localFile, JSONObject dataObj){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();

            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(URL_STR);

            // 把文件转换成流对象FileBody
            FileBody bin = new FileBody(new File(localFile));

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addTextBody("callback", dataObj.getString("callback"))
                    .addTextBody("key", dataObj.getString("key"))
                    .addTextBody("policy", dataObj.getString("policy"))
                    .addTextBody("signature", dataObj.getString("signature"))
                    .addTextBody("OssAccessKeyId", dataObj.getString("OssAccessKeyId"))
                    .addPart("file", bin)
                    .build();

            httpPost.setEntity(reqEntity);

            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);

            System.out.println("The response value of token:" + response.getFirstHeader("token"));

            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                // 打印响应长度
                System.out.println("Response content length: " + resEntity.getContentLength());
                // 打印响应内容
                System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
            }

            // 销毁
            EntityUtils.consume(resEntity);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(response != null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(httpClient != null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
