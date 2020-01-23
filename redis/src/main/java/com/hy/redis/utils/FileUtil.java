package com.hy.redis.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {
    public static String getScript(String path){
        FileInputStream file = null;
        BufferedReader reader = null;
        try{
            file = new FileInputStream(path);
            reader = new BufferedReader(new InputStreamReader(file, "utf-8"));
            StringBuffer script = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null){
                script.append(line + "\n");
            }
            return script.toString();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
