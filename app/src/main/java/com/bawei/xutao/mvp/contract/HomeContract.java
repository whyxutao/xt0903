package com.bawei.xutao.mvp.contract;

public interface HomeContract {

    //Model
    interface IModel{

        void getModelData(String path,IModelCallBack iModelCallBack);

        interface IModelCallBack{
            void onSuccess(String why);
            void onError(String msg);
        }
    }

    //View
    interface IView{
        void onSuccess(String why);
        void onError(String msg);
    }

    //Presenter
    interface IPresenter{
        //绑定View
        void attachView(HomeContract.IView iView);
        //解绑
        void detachView();
        //初始化Presenter
        void getPresenter(String path);
    }

}
