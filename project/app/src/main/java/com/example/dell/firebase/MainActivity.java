package com.example.dell.firebase;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements  MaincoverFragment.OnFragmentInteractionListener,signupFragment.OnFragmentInteractionListener,SigninFragment.OnFragmentInteractionListener{
    FragmentTransaction fts;
    Button btn1;
    Button btn2;
    EditText text;
    private FirebaseAuth auth;
    static boolean variable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cover);
        //setContentView(R.layout.activity_main);

 btn1=(Button) findViewById(R.id.button);
 btn2=(Button)findViewById(R.id.button2);
 text=(EditText) findViewById(R.id.editText);
//
//
  //      auth = FirebaseAuth.getInstance();
    //    if (auth.getCurrentUser() != null) {
      //      Intent i =new Intent(this,MainActivity.class);
        //    startActivity(i);
          // finish();
       // }
//else{
        if(!variable){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        variable=true;
        }
        MaincoverFragment f=new MaincoverFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fts=fragmentManager.beginTransaction();
        fts.add(R.id.fragment,f);
        fts.commit();
    //}

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
