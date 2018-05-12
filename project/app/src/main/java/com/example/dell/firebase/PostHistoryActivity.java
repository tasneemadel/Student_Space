package com.example.dell.firebase;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PostHistoryActivity extends AppCompatActivity implements PostHandler.PostInterface {
    private ListView list;
    private ArrayList<Post> myposts;
    FirebaseDatabase database;
    DatabaseReference ref;

    PostHandler postHelper;
    MypostList_handler postAdapter;
    String StudentId="-LBhpD-yzzcC2CGBZ9rz";
    private AuthenticationStudent As;
    private StudentKeys keys;
    private FirebaseAuth auth;
    private boolean flag;
    private  ViewHandler viewholder;
    private Post post;
    String [] posts;
    private String[ ] postskeys;
    private ArrayList<Post> Studentposts=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_myposts);
        ref= FirebaseDatabase.getInstance().getReference("Post");
        postHelper=new PostHandler(ref,PostHistoryActivity.this);
        keys=new StudentKeys();
       StudentId=keys.getvalue("studentid",this);
        list=(ListView) findViewById(R.id.ll);
        postHelper.getPosts_of_user(StudentId);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
     //   toolbar.setTitle("MY Posts");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Contact us on app.developer.egy@gmail.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public void getPost(ArrayList<Post> p) {
        myposts=p;
      Log.d("MYYY",""+myposts.size());
        if(myposts.size()>0){
            //postAdapter = new Myposts_ListHandler(ref,Myposts.this, myposts);
           // postAdapter=new MypostList_handler(this,myposts);
            PostList_Handler ada=new PostList_Handler(this,myposts);
            PostList_Handler.flag=true;
            //postAdapter.notifyDataSetChanged();
            ada.notifyDataSetChanged();
            list.setAdapter(ada);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getApplicationContext(),"CLICK",Toast.LENGTH_SHORT);
                    Log.e("CLICK","CLICK");
                }
            });
        }
    }

    @Override
    public void Editstudent_in_post(ArrayList<String> s) {

    }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.help) {
        keys.HelpDialogue("This app helps students to form their own room to schedule their study groups",this);
        return true;
    }
    if (id==R.id.logout){
        boolean flag=As.Signout();

        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        finish();

    }
    if (id==R.id.update) {
        Intent i=new Intent(PostHistoryActivity.this,UpdatePasswordActivity.class);
        startActivity(i);
        //   finish();
    }
    return super.onOptionsItemSelected(item);

}
}
