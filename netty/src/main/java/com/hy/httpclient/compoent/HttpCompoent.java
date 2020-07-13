package com.hy.httpclient.compoent;

import com.hy.httpclient.result.HttpResult;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class HttpCompoent {


    public static HttpResult executeGet(String urlStr, List<NameValuePair> params){
        return executeGet(urlStr, params, null);
    }

    public static HttpResult executeGet(String urlStr, List<NameValuePair> params, RequestConfig config){
        if(urlStr == null || "".equals(urlStr)){
            throw new IllegalArgumentException("urlStr is invalid");
        }
        CloseableHttpClient client = HttpClientBuilder.create().build();
        URI uri = null;
        CloseableHttpResponse response = null;
        HttpResult result = new HttpResult(urlStr, "", true);
        try {
            URIBuilder uriBuilder = new URIBuilder().setPath(urlStr);
            if(params != null && params.size() > 0){
                uriBuilder.setParameters(params);
            }
            uri = uriBuilder.build();
            HttpGet httpGet = new HttpGet(uri);
            if(config != null){
                httpGet.setConfig(config);
            }
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            System.out.println("状态" + statusLine);
            if(entity != null){
                String responseStr = EntityUtils.toString(entity);
                result.setMsg(responseStr);
                System.out.println(urlStr+"---responseStr--->" + responseStr);
                return result;
            }
            result.setMsg("状态" + statusLine);
            result.setSuccess(statusLine.toString().indexOf("200") != -1);
            EntityUtils.consume(entity);
            return result;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            return result;
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

    public static HttpResult executePost(String urlStr, HttpEntity httpEntity){
        return executePost(urlStr, httpEntity, null, null);
    }

    public static HttpResult executePost(String urlStr, HttpEntity httpEntity, List<NameValuePair> params){
        return executePost(urlStr, httpEntity, params, null);
    }

    public static HttpResult executePost(String urlStr, HttpEntity httpEntity, List<NameValuePair> params, RequestConfig config){
        if(urlStr == null || "".equals(urlStr)){
            throw new IllegalArgumentException("urlStr is invalid");
        }
        CloseableHttpClient client = HttpClients.createDefault();//HttpClientBuilder.create().build();
        URI uri = null;
        CloseableHttpResponse response = null;
        HttpResult result = new HttpResult(urlStr, "", true);
        URIBuilder uriBuilder = new URIBuilder().setPath(urlStr);
        if(params != null && params.size() > 0){
            uriBuilder.setParameters(params);
        }
        try {
            uri = uriBuilder.build();
            HttpPost httpPost = new HttpPost(uri);
            if(config != null)
                httpPost.setConfig(config);
            if(httpEntity != null)
                httpPost.setEntity(httpEntity);
            response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            System.out.println("状态" + statusLine);
            if(entity != null){
                String responseStr = EntityUtils.toString(entity);
                result.setMsg(responseStr);
                System.out.println(urlStr+"---responseStr--->" + responseStr);
                return result;
            }
            result.setMsg("状态" + statusLine);
            result.setSuccess(statusLine.toString().indexOf("200") != -1);
            EntityUtils.consume(entity);
            return result;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            return result;
        }finally {
            try {
                if(response != null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(client != null){
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
