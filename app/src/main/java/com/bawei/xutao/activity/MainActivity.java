package com.bawei.xutao.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bawei.xutao.R;
import com.bawei.xutao.adapter.MyXlListAdapter;
import com.bawei.xutao.app.App;
import com.bawei.xutao.base.BaseActivity;
import com.bawei.xutao.bean.NewsInfo;
import com.bawei.xutao.mvp.contract.HomeContract;
import com.bawei.xutao.mvp.presenter.HomePresenter;
import com.bawei.xutao.utils.HttpUtil;
import com.bwei.xlistview.XlistView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements HomeContract.IView {


    private HomePresenter homePresenter;
    public String path = "http://blog.zhaoliang5156.cn/api/shop/shop"+ page +".json";
    public static int page = 1;
    public List<NewsInfo.DataInfo> list = new ArrayList<>();
    private XlistView xlv;
    private Handler handler = new Handler();
    private SharedPreferences sp;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        xlv = findViewById(R.id.xlv);
        sp = getSharedPreferences("有网", Context.MODE_PRIVATE);

    }

    @Override
    protected void initData() {

        homePresenter = new HomePresenter();
        if (homePresenter!=null) {
            homePresenter.attachView(MainActivity.this);
            homePresenter.getPresenter(path);
        }
    }

    @Override
    public void onSuccess(String why) {

        Gson gson = new Gson();
        NewsInfo newsInfo = gson.fromJson(why.toString(), NewsInfo.class);
        List<NewsInfo.DataInfo> data = newsInfo.getData();
        list.addAll(data);
        if (HttpUtil.getInstance().getWang(App.context)){
            getServerData(page);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("ymq",why);
            edit.commit();
            xlv.setPullLoadEnable(true);
            xlv.setPullRefreshEnable(true);
            xlv.setXListViewListener(new XlistView.IXListViewListener() {
                @Override
                public void onRefresh() {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            page = 1;

                            getServerData(page);

                            xlv.stopRefresh();

                        }
                    },2000);
                }

                @Override
                public void onLoadMore() {

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            page++;

                            getServerData(page);

                            xlv.stopLoadMore();

                        }
                    },2000);

                }
            });
        }else {

            String ymq = sp.getString("ymq", "");
            Gson gsonone = new Gson();
            NewsInfo newsInfoone = gsonone.fromJson(ymq.toString(), NewsInfo.class);
            List<NewsInfo.DataInfo> dataone = newsInfoone.getData();
            MyXlListAdapter adapter = new MyXlListAdapter(dataone,MainActivity.this);
            xlv.setAdapter(adapter);

        }



    }

    private void getServerData(int page) {

        MyXlListAdapter adapter = new MyXlListAdapter(list,MainActivity.this);
        xlv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onError(String msg) {

        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.detachView();
    }
}
