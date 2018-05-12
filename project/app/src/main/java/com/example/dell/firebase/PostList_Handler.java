package com.example.dell.firebase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Tasneem on 5/6/2018.
 */

public class PostList_Handler extends BaseAdapter {
    ViewHandler viewHolder;
     ViewHandler viewHolder1;
    private Context context;
    private Post post;
    private customTextListener customlistener;
    private ArrayList<Post> p=new ArrayList<Post>();
public static boolean flag=false;
    public PostList_Handler(Context context,ArrayList<Post> p){
        this.context=context;
        this.p=p;

    }
    public void setPost(ArrayList<Post> p){
        this.p.clear();
        this.p=p;
    }

    public  void setListener(customTextListener listener) {
        this.customlistener = listener;
    }

    public interface customTextListener {
        public void onTextClickListner(ViewHandler v);

    }

    @Override
    public int getCount() {
        return p.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder1 = new ViewHandler();
        viewHolder = new ViewHandler();
        //if(flag){
          /*
            if (view == null) {

                LayoutInflater inflater = LayoutInflater.from(context);

                view = inflater.inflate(R.layout.mychildposts, null);
                viewHolder1.readonlypost = (TextView) view.findViewById(R.id.test);
                viewHolder1.time = (TextView) view.findViewById(R.id.time2);
                viewHolder1.studentname = (TextView) view.findViewById(R.id.name2);
                viewHolder1.studentstatus = (TextView) view.findViewById(R.id.status2);
                viewHolder1.level = (TextView) view.findViewById(R.id.level2);
                viewHolder1.mypostsubmit=(Button)view.findViewById(R.id.postbtn);
                viewHolder1.spinner=(Spinner)view.findViewById(R.id.options);
                view.setTag(viewHolder);
            } else {

                viewHolder1 = (ViewHandler) view.getTag();
            }
            post = p.get(i);
            Log.d("Student___Test", "" + p.get(i).name);
            Log.d("Student____Test", "" + post.name);
            viewHolder1.readonlypost.setText(post.post);
            viewHolder1.studentname.setText(post.name);
            viewHolder1.time.setText(post.time);
            viewHolder1.studentstatus.setText(post.status);
*/
           // if(customlistener!=null){
               // customlistener.onTextClickListner(viewHolder1);
            //}
/*
            final PopupMenu popup = new PopupMenu(context, viewHolder1.post);
            viewHolder1.post.setOnClickListener(new  View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    final ViewHandler v=viewHolder1;
                    Log.d("HEREEEEEEee","....");
                    final Context cont=context;
                    popup.getMenuInflater().inflate(R.menu.menu_list, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            final DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Post");
                            final PostHandler postHelper=new PostHandler(ref,cont);
                            Log.d("HEREEEEEEee","...P.");
                            switch(item.getItemId()) {
                                case R.id.edit:
                                    // edit stuff here
                                    v.post.setEnabled(true);
                                    v.mypostsubmit.setTextColor(0x33b5e5);
                                    v.mypostsubmit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            postHelper.updatePost(post.id,v.post.getText().toString());
                                        }
                                    });
                                    return true;
                                case R.id.delete:
                                    postHelper.removePost(post.id);
                                    return true;
                                default:
                                    return true;
                            }

                        }
                    });

                    popup.show();
                }
            });
*/
        //}
  //      else {
            if (view == null) {

                LayoutInflater inflater = LayoutInflater.from(context);

                view = inflater.inflate(R.layout.childlist2, null);
                viewHolder.post = (EditText) view.findViewById(R.id.post);
                viewHolder.time = (TextView) view.findViewById(R.id.time);
                viewHolder.studentname = (TextView) view.findViewById(R.id.studentname);
                viewHolder.studentstatus = (TextView) view.findViewById(R.id.status);
                viewHolder.level = (TextView) view.findViewById(R.id.level);
                view.setTag(viewHolder);
            } else {

                viewHolder = (ViewHandler) view.getTag();
            }
            post = p.get(i);
            Log.d("Student_Test", "" + p.get(i).name);
            viewHolder.post.setText(post.post);
            viewHolder.studentname.setText(post.name);
            viewHolder.time.setText(post.time);
            viewHolder.studentstatus.setText(post.status);

    //    }
        return view;
    }

}
