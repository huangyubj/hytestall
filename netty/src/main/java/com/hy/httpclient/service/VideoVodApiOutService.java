package com.hy.httpclient.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hy.httpclient.compoent.HttpCompoent;
import com.hy.httpclient.result.HttpResult;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.junit.Test;

import java.io.File;

public class VideoVodApiOutService {

    /**
     * 获取服务上传信息
     * @return
     */
    public HttpResult uploadAuth(){
        String uriStr = "http://101.200.173.176/mediaauth/uploadAuth";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserId",10002);
        jsonObject.put("AuthKey","3e288e31013683f1229b18079c491e50;1591606976");
        jsonObject.put("Path","xxTest0xxTe06081801.txt");
        StringEntity stringEntity = new StringEntity(jsonObject.toJSONString(), "UTF-8");
        HttpResult result = HttpCompoent.executePost(uriStr, stringEntity);
        return result;
    }

    /**
     * 根据上传信息上传附件
     * @param localFile
     * @param dataObj
     * @return
     */
    public HttpResult upload1(String localFile, JSONObject dataObj){
        String uriStr = "http://xes-video-slice-ali.oss-cn-beijing.aliyuncs.com";
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
        HttpResult result = HttpCompoent.executePost(uriStr, reqEntity);
        return result;
    }

    /**
     * 测试
     */
    @Test
    public void test(){
        HttpResult result = uploadAuth();
        if(result.isSuccess()){
            JSONObject object = JSON.parseObject(result.getMsg());
            JSONObject dataObj = object.getJSONObject("Data").getJSONObject("Body");
            HttpResult result1 = upload1("d://video_vod_api_out.py", dataObj);
            System.out.println("执行结果：" + result1.getMsg());
        }
    }
}
