package com.bawei.xutao.mvp.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.bawei.xutao.app.App;
import com.bawei.xutao.mvp.contract.HomeContract;
import com.bawei.xutao.mvp.model.HomeModel;
import com.bawei.xutao.utils.HttpUtil;

import java.lang.ref.WeakReference;

public class HomePresenter implements HomeContract.IPresenter {

    private Context context;
    private HomeModel homeModel;
    private WeakReference<HomeContract.IView> iViewWeakReference;

    @Override
    public void attachView(HomeContract.IView iView) {
        //实例化Model层
        homeModel = new HomeModel();
        //弱引用
        iViewWeakReference = new WeakReference<>((HomeContract.IView) iView);
    }

    public HomeContract.IView getView(){
        return iViewWeakReference.get();
    }

    @Override
    public void detachView() {

        if (iViewWeakReference!=null) {
            iViewWeakReference.clear();
            iViewWeakReference=null;
        }

    }

    @Override
    public void getPresenter(String path) {


        homeModel.getModelData(path, new HomeContract.IModel.IModelCallBack() {
            @Override
            public void onSuccess(String why) {

                    getView().onSuccess(why);

            }

            @Override
            public void onError(String msg) {

                getView().onError("获取数据异常");

            }
        });

    }
}
