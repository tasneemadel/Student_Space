package com.example.dell.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Myposts extends AppCompatActivity {
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
        setContentView(R.layout.content_myposts);
        ref=FirebaseDatabase.getInstance().getReference("Post");
        postHelper=new PostHandler(ref,Myposts.this);
        list=(ListView) findViewById(R.id.list4);
        auth = FirebaseAuth.getInstance();
        As=new AuthenticationStudent(auth,this);
        keys=new StudentKeys();
        KeyIntiation();

        getPosts_of_user(StudentId);


      // registerForContextMenu(list);
       // getPosts();
      // String[] lis= getResources().getStringArray(R.array.list);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lis);
        //list.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void setoptions(final String [] postval, final String []keyval){

    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            PopupMenu popup = new PopupMenu(Myposts.this, view);
            final String postvalue=postval[i];
            final String keyvalue=keyval[i];
            Log.d("TTT",""+keyval);
            popup.getMenuInflater().inflate(R.menu.menu_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Log.d("TTT","hii");
                    switch(item.getItemId()) {
                        case R.id.edit:
                            // edit stuff here
                            Intent i=new Intent(Myposts.this,EditPostActivity.class);
                            i.putExtra("tempost",postvalue);
                            i.putExtra("tempostid",keyvalue);
                            startActivity(i);
                            finish();
                            return true;
                        case R.id.delete:
                            postHelper.removePost(keyvalue);
                            getPosts_of_user(StudentId);
                            return true;
                        case R.id.share:
                            String message = "Text I want to share.";
                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.setType("text/plain");
                            share.putExtra(Intent.EXTRA_TEXT, postvalue);

                            startActivity(Intent.createChooser(share, "Share your post with "));
                            return true;
                        default:
                            return true;
                    }


                }
            });
            popup.show();
        }
    });
}
    public void onPopupButtonClick(View button) {
        PopupMenu popup = new PopupMenu(this, button);
        popup.getMenuInflater().inflate(R.menu.menu_list, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                switch(item.getItemId()) {
                    case R.id.edit:
                        // edit stuff here
                        viewholder.post.setEnabled(true);
                        viewholder.mypostsubmit.setTextColor(0x33b5e5);
                        viewholder.mypostsubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                              postHelper.updatePost(post.id,viewholder.post.getText().toString());
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
    public void test(){

        Post p=new Post();
        p.name="Tasneem";
        p.status="Student";
        p.level="Senior";
        p.post="Hellooo";
        p.time="3:00 pm";
        Studentposts.add(p);
        PostList_Handler ada=new PostList_Handler(Myposts.this,Studentposts);
        PostList_Handler.flag=true;
        ada.notifyDataSetChanged();
        list.setAdapter(ada);
    }
public void testV(View v){

Log.d("TestV",v.toString());
    PopupMenu popup = new PopupMenu(Myposts.this, v);
    popup.getMenuInflater().inflate(R.menu.menu_list, popup.getMenu());

//   Log.d("TEST_NULL",""+ PostList_Handler.viewHolder1.post.getText().toString());
  /*
    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d("here","entered");
        }
    });*/
}
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
           // MenuInflater inflater = getMenuInflater();
            //inflater.inflate(R.menu.menu_list, menu);
        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Action 1");

    }
    public void KeyIntiation(){

        StudentId=keys.getvalue("studentid",this);
        Log.d("Key_intiationS",StudentId);

    }
    public void getPosts(){
       postHelper.getPosts_of_user(StudentId);
       Log.d("Size_returned_array ",""+postHelper.getArray().size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
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
            Intent i=new Intent(Myposts.this,UpdatePasswordActivity.class);
            startActivity(i);
         //   finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit:
                // edit stuff here
                return true;
            case R.id.delete:
                // remove stuff here
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
public void Getview(ViewHandler h,Post p){
        //viewholder=new ViewHandler();
        viewholder=h;
        //post=new Post();
        post=p;

}
/*
    @Override
    public void getPost(ArrayList<Post> p) {
        myposts=p;
      Log.d("MYYY",""+myposts.size());
        if(myposts.size()>0){
            //postAdapter = new Myposts_ListHandler(ref,Myposts.this, myposts);
            postAdapter=new MypostList_handler(this,myposts);
            PostList_Handler ada=new PostList_Handler(this,myposts);
            PostList_Handler.flag=true;
            postAdapter.notifyDataSetChanged();
            list.setAdapter(ada);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("in_Post","ooooo");
                }
            });
        }
    }*/
/*
    @Override
    public void Editstudent_in_post(ArrayList<String> s) {

    }
*/


    public void getPosts_of_user(String userID){

        ref.orderByChild("studentid").equalTo(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Studentposts.clear();
                for (final DataSnapshot snapshot :dataSnapshot.getChildren() ) {
                    Post post = new Post();
                    post.id=snapshot.getKey();
                    post.department=snapshot.child("department").getValue().toString();
                    post.post=snapshot.child("post").getValue().toString();
                    post.time=snapshot.child("time").getValue().toString();
                    post.name=snapshot.child("name").getValue().toString();
                    post.level=snapshot.child("level").getValue().toString();
                    post.status=snapshot.child("status").getValue().toString();
                    post.studentid=snapshot.child("studentid").getValue().toString();
                    Studentposts.add(post);
                }

                if(Studentposts.size()>0){
                    //PostList_Handler ada=new PostList_Handler(Myposts.this,Studentposts);
                    //PostList_Handler.flag=true;
                    Collections.reverse(Studentposts);
                    posts= new String[Studentposts.size()];
                    postskeys= new String[Studentposts.size()];
                    for (int i=0;i<Studentposts.size();i++){
                        posts[i]=Studentposts.get(i).post;
                        postskeys[i]=Studentposts.get(i).id;
                    }

                   // ArrayAdapter<String> adapter = new ArrayAdapter<String>(Myposts.this, android.R.layout.simple_spinner_item, posts);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Myposts.this, R.layout.child_item, R.id.textView9,posts );
                    list.setAdapter(adapter);
                    setoptions(posts,postskeys);
                  //  ada.notifyDataSetChanged();
                    //list.setAdapter(ada);


                }
                //ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
