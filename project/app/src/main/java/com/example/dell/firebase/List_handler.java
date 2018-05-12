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
 * Created by dell on 5/3/2018.
 */

public class List_handler extends BaseAdapter {
    ViewHandler viewHolder;
    private Context context;
    private Student student;
    private ArrayList<Student> s=new ArrayList<Student>();
    public List_handler(Context context, ArrayList<Student> s){
        this.s=s;
        this.context=context;
    }
    @Override
    public int getCount() {
        return s.size();
    }

    @Override
    public Object getItem(int i) {
        return s.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        viewHolder = new ViewHandler();
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.childlist2, null);

            viewHolder.post = (EditText) view.findViewById(R.id.post);
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            viewHolder.studentname = (TextView) view.findViewById(R.id.studentname);
            viewHolder.studentstatus=(TextView)view.findViewById(R.id.status);
            viewHolder.level=(TextView) view.findViewById(R.id.level);
            view.setTag(viewHolder);
        }
        else {

            viewHolder = (ViewHandler) view.getTag();
        }

        student= s.get(i);
        Log.d("Student_Test", ""+s.get(i).getname());
        //ArrayList<String> posts=student.posts;

        viewHolder.post.setText(student.posts.get(student.posts.size()-1).toString());
        viewHolder.studentname.setText(student.getname());
        viewHolder.time.setText(student.gettime().get(student.time.size()-1).toString());
        viewHolder.studentstatus.setText(student.status);
        return view;
    }
}
