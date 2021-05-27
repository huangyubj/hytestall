package com.hy.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.*;

public class JSONFomate {

    public static void main(String[] args) {
        JSONFomate.formate("C:/Users/hy/Desktop/11.txt", "");
    }

    public static void formate(String fromPath, String toPath){
        File file = new File(fromPath);
//        File outFile = new File(toPath);
        BufferedReader bufferedReader;
        FileInputStream inputStream = null;
//        FileOutputStream outputStream;
        if(file.exists()){
            try {
//                if(!outFile.exists()){
//                    outFile.createNewFile();
//                }
                inputStream = new FileInputStream(file);
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str = null;
                while ((str = bufferedReader.readLine()) != null){
                    stringBuffer.append(str);
                }
                JSONObject object = JSONObject.parseObject(stringBuffer.toString());
                String pretty = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteDateUseDateFormat);
                System.out.println(pretty);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        }
    }
}
