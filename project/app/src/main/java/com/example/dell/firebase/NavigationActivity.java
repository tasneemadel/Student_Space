package com.example.dell.firebase;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NavigationActivity extends AppCompatActivity
        implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener ,PostHandler.PostInterface{
ListView list;
    List_handler adapter;
    PostList_Handler postAdapter;
    ArrayList<Student>students=new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference ref;
    DatabaseReference postref;
    StudentHandler helper;
    PostHandler postHelper;
    EditText addpost;
    Button post;
    ScrollView scroll;
    ArrayList<String> posts;
    ArrayList<String> time;
   String  userId="-LBhpD-yzzcC2CGBZ9rz";
   String level="Senior";
   String status="Student";
   String name="Tasneem Adel";
   Date currentTime;
   String department="Computer Engineering";
   ArrayList<Post> sentposts;
   private FirebaseAuth auth;
   private EditText searchtext;
   private ImageButton search;
   private AuthenticationStudent As;
   private StudentKeys keys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_navigation);

         fieldInitiation();
         dbIntiation();
        KeyIntiatio();


         Navigationdrawer();
         onClick(list);
        getPosts();
         addpost();

    }
    public void fieldInitiation(){
        list=(ListView)findViewById(R.id.list3);
        addpost=(EditText) findViewById(R.id.addpost);
        scroll=(ScrollView) findViewById(R.id.scrollpost);
        scroll.fullScroll(View.FOCUS_DOWN);
        post=(Button) findViewById(R.id.postag);
        searchtext=(EditText)findViewById(R.id.searchbyname);
        search=(ImageButton)findViewById(R.id.search);
    }
    public void dbIntiation(){
        auth = FirebaseAuth.getInstance();
        As=new AuthenticationStudent(auth,this);
        keys=new StudentKeys();
        database=FirebaseDatabase.getInstance();
        ref= FirebaseDatabase.getInstance().getReference("Student");
        ref.keepSynced(true);
        postref=FirebaseDatabase.getInstance().getReference("Post");
        helper=new StudentHandler(ref);
        postHelper=new PostHandler(postref,NavigationActivity.this);
    }
    public void KeyIntiatio(){

        userId=keys.getvalue("studentid",this);
        level=keys.getvalue("level",this);
        department=keys.getvalue("department",this);
        name=keys.getvalue("name",this);
        status=keys.getvalue("status",this);
        Log.d("departmenttttt",level);
        Log.d("studentid",userId);
    }
    public void addpost(){

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentTime = Calendar.getInstance().getTime();
                String time=currentTime.toString();
            if (!addpost.getText().toString().equals("")) {
                 postHelper.savePost(userId, level, name, addpost.getText().toString(), time, department,status);
               // helper.saveHash(userId, addpost.getText().toString(), time);
                addpost.setText("");
            }
            else{
                addpost.setError("Please write your post first");
            }

            }});
        }

public void Navigationdrawer(){
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle("Home");
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Contact us on app.developer.egy@gmail.com", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    });

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();
    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
}

    @Override
    protected void onStart() {
        super.onStart();
       // getPosts();
    }

    public void getPosts(){
        ArrayList<Post> postlist;
        postlist=postHelper.retrieveHash(department);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            Intent i=new Intent(NavigationActivity.this,UpdatePasswordActivity.class);
            startActivity(i);
          //  finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i;
      if (id == R.id.nav_gallery) {
          i=new Intent(NavigationActivity.this,PostHistoryActivity.class);
          startActivity(i);
       //   finish();
        }
        else if (id == R.id.home) {

        } else if (id == R.id.nav_manage) {
            i=new Intent(NavigationActivity.this,SettingActivity.class);
            startActivity(i);
        } else if (id == R.id.editposts) {
          i=new Intent(NavigationActivity.this,Myposts.class);
          startActivity(i);
      }else if (id == R.id.nav_share) {

            String message = "Text I want to share.";
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, message);

            startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));

        } else if (id == R.id.nav_send) {
          Intent sendIntent = new Intent(Intent.ACTION_VIEW);
          sendIntent.putExtra("address"  , new String("01111770715"));
          sendIntent.putExtra("sms_body", "default content");
          sendIntent.setType("vnd.android-dir/mms-sms");
          startActivity(sendIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void getPost(ArrayList<Post> p) {
        sentposts=p;
        Log.d("SSS_Test", "Value is: " +p.size());
        PostList_Handler.flag=false;
            postAdapter = new PostList_Handler(NavigationActivity.this, sentposts);
            postAdapter.notifyDataSetChanged();
            list.setAdapter(postAdapter);

    }

    @Override
    public void Editstudent_in_post(ArrayList<String> s) {

    }


    @Override
    public void onClick(View view) {
        Log.d("never","");
    }
}
