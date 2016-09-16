package com.tt.hackextend.the23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yotamc on 15-Sep-16.
 */
public class SearchAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflator;
    List<User> userList;

    public SearchAdapter(List<User> userList, Context context) {
        this.context=context;
        this.userList = userList;
        inflator= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Random randomGenerator = new Random();

        List<Integer> imageHolder = new ArrayList<Integer>();
        imageHolder.add(R.drawable.face1);
        imageHolder.add(R.drawable.face2);
        imageHolder.add(R.drawable.face3);
        imageHolder.add(R.drawable.face4);
        imageHolder.add(R.drawable.face5);
        imageHolder.add(R.drawable.face6);
        imageHolder.add(R.drawable.face7);
        imageHolder.add(R.drawable.face8);
        imageHolder.add(R.drawable.face9);
        imageHolder.add(R.drawable.face10);
        View row= inflator.inflate(R.layout.user_item,viewGroup,false);
        ImageView imageView= (ImageView) row.findViewById(R.id.imageViewPicture);
        TextView name= (TextView) row.findViewById(R.id.textViewName);
        TextView city= (TextView) row.findViewById(R.id.textViewCity);
        User user=userList.get(i);
        name.setText(user.name);
        city.setText(user.city);
        imageView.setImageResource(imageHolder.get(randomGenerator.nextInt(10)));
        return row;
    }
}
