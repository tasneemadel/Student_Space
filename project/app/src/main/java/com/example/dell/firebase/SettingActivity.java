package com.example.dell.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingActivity extends AppCompatActivity implements PostHandler.PostInterface{
Button submit;
Button cancel;
EditText editname;
EditText editdepartment;
EditText editstatus;
EditText editemail;
EditText editlevel;
String name;
String email;
String status;
String department;
String  userId="-LBhpD-yzzcC2CGBZ9rz";
String level;
    private AuthenticationStudent As;
    private StudentKeys keys;
    private FirebaseAuth auth;
DatabaseReference ref;
    DatabaseReference dbref;
StudentHandler helper;
PostHandler posthelper;
Map<String, Object> childUpdates = new HashMap<>();
    Map<String, Object> studentUpdates = new HashMap<>();
ArrayList<String> studentKey=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        auth = FirebaseAuth.getInstance();
        As=new AuthenticationStudent(auth,this);
        keys=new StudentKeys();
        userId=keys.getvalue("studentid",this);
        FloatingBtn();
        Initialization();
        onButton();
    }
    public void FloatingBtn() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
toolbar.setTitle("Settings");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Contact us on app.developer.egy@gmail.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

public void Initialization(){
        submit=(Button) findViewById(R.id.submit);
        cancel=(Button) findViewById(R.id.cancel);
        editname=(EditText) findViewById(R.id.editname);
        editdepartment=(EditText) findViewById(R.id.editdepartment);
        editemail=(EditText) findViewById(R.id.editemail);
        editstatus=(EditText) findViewById(R.id.editstatus);
        editlevel=(EditText) findViewById(R.id.editlevel);
}
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
public void update(){
    name=editname.getText().toString();
    email=editemail.getText().toString();
    status=editstatus.getText().toString();
    department=editdepartment.getText().toString();
    level=editlevel.getText().toString();
    ref=FirebaseDatabase.getInstance().getReference();
    dbref=FirebaseDatabase.getInstance().getReference("Post");
    posthelper=new PostHandler(dbref,SettingActivity.this);

    if (!email.equals("")) {
        if (!isEmailValid(email)) {
            editemail.setError("Please enter correct email");
        } else {
            childUpdates.put("/Student/" + userId + "/email", email);
            keys.setKey("email",email,this);
            As.UpdateEmail(email);
        }
    }
    posthelper.editstudent_in_post(userId);
    Log.d("FF_go",""+studentKey.size());
}
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
public void save(ArrayList<String> StudentKey){
    if(!email.equals("")){
        //updateEmail( email);
    }
    if(StudentKey.size()==0){

        Log.d("iid",userId);
        if (!name.equals("")) {
            childUpdates.put("/Student/" + userId + "/name", name);
            keys.setKey("name",name,this);

        }
        if (!level.equals("")) {
            childUpdates.put("/Student/" + userId + "/level", level);
            keys.setKey("level",level,this);

        }
        if (!department.equals("")) {
            childUpdates.put("/Student/" + userId + "/department", department);
            keys.setKey("department",department,this);
        }
        if (!status.equals("")) {
            childUpdates.put("/Student/" + userId + "/status", status);
            keys.setKey("status",status,this);

        }
        ref.updateChildren(childUpdates);
    }
    else {
    for(int i=0;i<StudentKey.size();i++) {
        String id=StudentKey.get(i);
        Log.d("Key_1",""+i);
        Log.d("iid",id);
        if (!name.equals("")) {
            childUpdates.put("/Student/" + userId + "/name", name);
            studentUpdates.put("/" + id + "/name", name);
            keys.setKey("name",name,this);
        }
        if (!level.equals("")) {
            childUpdates.put("/Student/" + userId + "/level", level);
            studentUpdates.put("/" + id + "/level", level);
            keys.setKey("level",level,this);
        }
        if (!department.equals("")) {
            childUpdates.put("/Student/" + userId + "/department", department);
            studentUpdates.put("/" + id + "/department", department);
            keys.setKey("department",department,this);
        }
        if (!status.equals("")) {
            childUpdates.put("/Student/" + userId + "/status", status);
            studentUpdates.put("/" + id + "/status", status);
            keys.setKey("status",status,this);
        }
       ref.updateChildren(childUpdates);
        dbref.updateChildren(studentUpdates);
    }
}
}
    public void onButton(){
    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            update();
            Intent i=new Intent(SettingActivity.this,NavigationActivity.class);
            startActivity(i);
        }
    });
    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i=new Intent(SettingActivity.this,NavigationActivity.class);
            startActivity(i);
        }
    });
}
public void updateEmail(String email){
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    user.updateEmail(email.trim());

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
            Intent i=new Intent(SettingActivity.this,UpdatePasswordActivity.class);
            startActivity(i);
        //    finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getPost(ArrayList<Post> p) {

    }

    @Override
    public void Editstudent_in_post(ArrayList<String> s) {
        studentKey=s;
        Log.d("Keysss",""+studentKey.size());
        save(s);
    }
}
