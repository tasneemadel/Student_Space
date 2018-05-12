package com.example.dell.firebase;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tasneem on 5/8/2018.
 */

public class MypostList_handler extends BaseAdapter {
    ViewHandler viewHolder1;
    private Context context;
    private Post post;
    private ArrayList<Post> p=new ArrayList<Post>();

    public MypostList_handler(Context context,ArrayList<Post> p){
        this.context=context;
        this.p=p;

    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return p.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder1 = new ViewHandler();
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.childlist2, null);
            viewHolder1.post = (EditText) view.findViewById(R.id.post);
            viewHolder1.time = (TextView) view.findViewById(R.id.time);
            viewHolder1.studentname = (TextView) view.findViewById(R.id.studentname);
            viewHolder1.studentstatus=(TextView) view.findViewById(R.id.status);
            Log.d("errorrr",viewHolder1.studentstatus.toString());
            viewHolder1.level=(TextView) view.findViewById(R.id.level);
            view.setTag(viewHolder1);
        }
        else {

            viewHolder1 = (ViewHandler) view.getTag();
        }
        post= p.get(i);
        Log.d("Student_Error", ""+p.get(i).name);
        viewHolder1.post.setText(post.post);
        viewHolder1.studentname.setText(post.name);
        viewHolder1.time.setText(post.time);
        viewHolder1.studentstatus.setText(post.status);

        return view;
    }

}
