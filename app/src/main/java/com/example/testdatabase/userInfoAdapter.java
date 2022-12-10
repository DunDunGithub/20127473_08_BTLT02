package com.example.testdatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        ImageView imgSua, imgXoa;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder;
        if(view == null) {
            holder = new viewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder.tvUserInfo = (TextView) view.findViewById(R.id.tvUserInfo);
            holder.imgSua = (ImageView) view.findViewById(R.id.change);
            holder.imgXoa = (ImageView) view.findViewById(R.id.delete);
            view.setTag(holder);
        }else{
            holder = (viewHolder) view.getTag();
        }

        final userInfo user = userList.get(i);
        holder.tvUserInfo.setText(user.getUsername());

        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Sửa", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Xóa", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
