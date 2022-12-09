package com.example.testdatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class userInfoAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<userInfo> userList;

    public userInfoAdapter(Context context, int layout, List<userInfo> userList){
        this.context = context;
        this.layout = layout;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class viewHolder {
        TextView tvUserInfo;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder;
        if(view == null) {
            holder = new viewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder.tvUserInfo = (TextView) view.findViewById(R.id.tvUserInfo);
            view.setTag(holder);
        }else{
            holder = (viewHolder) view.getTag();
        }

        userInfo user = userList.get(i);
        holder.tvUserInfo.setText(user.getUsername());
        return view;
    }
}
