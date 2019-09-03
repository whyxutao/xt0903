package com.bawei.xutao.utils;

import android.os.AsyncTask;

import com.bawei.xutao.mvp.contract.HomeContract;

import java.io.IOException;

public class MyAsyncTask extends AsyncTask<Integer,Integer,String> {
    String path;
    HomeContract.IModel.IModelCallBack iModelCallBack;
    private String data;

    public MyAsyncTask(String path, HomeContract.IModel.IModelCallBack iModelCallBack) {
        this.path = path;
        this.iModelCallBack = iModelCallBack;
    }

    //子线程做异步操作
    @Override
    protected String doInBackground(Integer... integers) {
        try {
            data = HttpUtil.getInstance().getData(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    //更新UI

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s!=null) {
            iModelCallBack.onSuccess(s);
        }else {
            iModelCallBack.onError("获取异常失败");
        }
    }
}
