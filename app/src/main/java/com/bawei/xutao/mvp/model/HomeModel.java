package com.bawei.xutao.mvp.model;

import android.content.Context;

import com.bawei.xutao.app.App;
import com.bawei.xutao.mvp.contract.HomeContract;
import com.bawei.xutao.utils.HttpUtil;
import com.bawei.xutao.utils.MyAsyncTask;

public class HomeModel implements HomeContract.IModel {
    @Override
    public void getModelData(String path, IModelCallBack iModelCallBack) {
        if (HttpUtil.getInstance().getWang(App.context)){
            new MyAsyncTask(path,iModelCallBack).execute();
        }else {

        }
    }
}
