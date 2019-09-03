package com.bawei.xutao.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bawei.xutao.app.App;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {

    //单例模式
    private static HttpUtil httpUtil = null;
    private ByteArrayOutputStream bos;

    private HttpUtil(){}

    public static HttpUtil getInstance(){
        if (httpUtil==null) {
            synchronized (HttpUtil.class){
                if (httpUtil==null) {
                    httpUtil = new HttpUtil();
                }
            }
        }
        return httpUtil;
    }

    //网络判断
    public boolean getWang(Context context){

        ConnectivityManager manager = (ConnectivityManager) App.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info!=null) {
            return info.isAvailable();
        }
        return false;
    }

    //GET获取网络数据
    public String getData(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        int responseCode = connection.getResponseCode();
        if (responseCode == 200){
            InputStream inputStream = connection.getInputStream();
            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer))!=-1){
                bos.write(buffer,0,len);
            }
            inputStream.close();
        }
        return bos.toString();
    }

}
