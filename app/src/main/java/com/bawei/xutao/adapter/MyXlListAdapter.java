package com.bawei.xutao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.xutao.R;
import com.bawei.xutao.bean.NewsInfo;
import com.bumptech.glide.Glide;

import java.util.List;

public class MyXlListAdapter extends BaseAdapter {

    private List<NewsInfo.DataInfo> data;

    private Context context;

    public MyXlListAdapter(List<NewsInfo.DataInfo> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.thumb);
            viewHolder.textViewname = convertView.findViewById(R.id.iname);
            viewHolder.textVieprice = convertView.findViewById(R.id.price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewname.setText(data.get(position).getGoods_name());
        viewHolder.textVieprice.setText(data.get(position).getCurrency_price());
        Glide.with(context).load(data.get(position).getGoods_thumb()).into(viewHolder.imageView);

        return convertView;
    }

    class ViewHolder{

        TextView textViewname;
        TextView textVieprice;
        ImageView imageView;

    }

}
