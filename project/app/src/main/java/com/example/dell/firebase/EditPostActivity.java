package com.example.dell.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditPostActivity extends AppCompatActivity {
EditText text;
Button Edit;
Button canceledit;
ScrollView scrollv;
    FirebaseDatabase database;
    DatabaseReference ref;
    PostHandler postHelper;
    String post;
    String postid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        text=(EditText) findViewById(R.id.editpost);
        Edit=(Button) findViewById(R.id.editnewpost);
        canceledit=(Button)findViewById(R.id.canceleditpost);
        ref=FirebaseDatabase.getInstance().getReference("Post");
        postHelper=new PostHandler(ref,EditPostActivity.this);
        Intent i=getIntent();
        Bundle bundle=i.getExtras();
        if (bundle!=null){
            post=(String)bundle.get("tempost");
            text.setText(post);
            postid=(String)bundle.get("tempostid");
        }
        Button();
    }
public void EditPost(){
        postHelper.updatePost(postid, post);
        Intent ii=new Intent(this,Myposts.class);
        startActivity(ii);
        finish();
}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ii=new Intent(this,Myposts.class);
        startActivity(ii);
        finish();
    }
public void Button(){

    Edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(text.getText().toString().equals("")){
text.setError("Cannot leave the post empty");
            }
            else{
                post=text.getText().toString();
                EditPost();
            }
        }

    });
canceledit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent ii=new Intent(EditPostActivity.this,Myposts.class);
        startActivity(ii);
        finish();
    }
});
}
}
