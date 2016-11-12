package com.example.hp.cpcnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by HP on 11/5/2016.
 */
public class CustomAdapter extends BaseAdapter {

    Context mContext;

    public CustomAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder myHolder = null;
        LayoutInflater mInflator =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            // ตอนที่เริ่มแสดงผล row แรกๆ
            convertView = mInflator.inflate(R.layout.activity_news_list, null);
            myHolder = new ViewHolder();

            // เริ่ม bind widget
            myHolder.tvTopic = (TextView) convertView.findViewById(R.id.tvTopic);
            myHolder.tvDate = (TextView)  convertView.findViewById(R.id.tvDate);
            myHolder.imgPic = (ImageView) convertView.findViewById(R.id.imgPic);

            // ใช้คำสั่ง setTag เพื่อเก็บข้อมูล state ปัจจุบันของแต่ละ widget
            convertView.setTag(myHolder);
        }else {
            // ตอนที่เริ่ม recycle view แล้ว
            // เมื่อถูก recycle view ก็ให้เอาข้อมูล tag ที่เคยเก็บไว้ออกมา
            myHolder = (ViewHolder) convertView.getTag();
        }

        ////
        //set tag
        //myHolder.tvTopic.setTag(..);
        //myHolder.tvDate.setTag(..);
        //myHolder.imgPic.setImageResource(..);
        return convertView;
    }

    public class ViewHolder {
        TextView tvTopic;
        TextView tvDate;
        ImageView imgPic;
    }
}
