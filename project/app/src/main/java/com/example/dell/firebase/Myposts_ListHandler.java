package com.example.dell.firebase;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Tasneem on 5/7/2018.
 */

public class Myposts_ListHandler extends BaseAdapter{
    private Context context;
    private Post post;
    private  ViewHandler  viewHolder;
    DatabaseReference ref;
   // private ViewListener listener;
    PostHandler postHelper;
    private ArrayList<Post> p=new ArrayList<Post>();
    public Myposts_ListHandler(DatabaseReference ref, Context context, ArrayList<Post> p){
        this.p=p;
        this.context=context;
        postHelper=new PostHandler(ref,this.context);
        this.ref=ref;
        Log.d("jjjj",""+this.p.size());
      //  if (context instanceof Myposts_ListHandler.ViewListener) {
        //    listener = ( Myposts_ListHandler.ViewListener) context;
        //}

        //else {
        //  throw new RuntimeException(context.toString()
        //        + " must implement OnProfileFragmentInteractionListener");
        //}
    }

    @Override
    public int getCount() {
        return 0;
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
        viewHolder = new ViewHandler();
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.mychildposts, null);

            viewHolder.post = (EditText) view.findViewById(R.id.test);
            viewHolder.time = (TextView) view.findViewById(R.id.time2);
            viewHolder.studentname = (TextView) view.findViewById(R.id.name2);
            viewHolder.studentstatus=(TextView)view.findViewById(R.id.status2);
            viewHolder.level=(TextView) view.findViewById(R.id.level2);
            viewHolder.mypostsubmit=(Button)view.findViewById(R.id.postbtn);
            viewHolder.spinner=(Spinner) view.findViewById(R.id.options);
            String[] list= context.getResources().getStringArray(R.array.list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
            viewHolder.spinner.setAdapter(adapter);
            viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!adapterView.getItemAtPosition(i).toString().isEmpty()) {
                       if(adapterView.getItemAtPosition(i).toString().equals("Edit")){
                           viewHolder.post.setEnabled(true);
                           viewHolder.mypostsubmit.setTextColor(0x33b5e5);
                           viewHolder.mypostsubmit.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                   postHelper.updatePost(post.id,viewHolder.post.getText().toString());
                               }
                           });
                       }
                       else if (adapterView.getItemAtPosition(i).toString().equals("Delete")){

                       }
                       else if (adapterView.getItemAtPosition(i).toString().equals("Share")){
                           Intent share = new Intent(Intent.ACTION_SEND);
                           share.setType("text/plain");
                           share.putExtra(Intent.EXTRA_TEXT, viewHolder.post.getText().toString());

                           context.startActivity(Intent.createChooser(share, "Share your post with "));
                       }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            view.setTag(viewHolder);
        }
        else {

            viewHolder = (ViewHandler) view.getTag();
        }
        post= p.get(i);
        Log.d("Student___", ""+p.get(i).name);
        viewHolder.post.setText(post.post);
        viewHolder.studentname.setText(post.name);
        viewHolder.time.setText(post.time);
        viewHolder.studentstatus.setText(post.status);
       // listener.getview(viewHolder,post,i);
        return view;

    }

    //public interface ViewListener{
        //public void getview(ViewHandler v,Post p,int postiion);
    //}
}
