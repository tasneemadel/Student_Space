package com.example.dell.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class UpdatePasswordActivity extends AppCompatActivity {
EditText oldpass;
EditText confirmOldPass;
EditText newPass;

Button update;
Button cancel;
boolean flag=false;
private FirebaseAuth auth;
AuthenticationStudent As;
private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        oldpass=(EditText) findViewById(R.id.editpass);
        confirmOldPass=(EditText) findViewById(R.id.editconfirmpass);
        newPass=(EditText) findViewById(R.id.editnewpass);
        update=(Button) findViewById(R.id.update);
        cancel=(Button) findViewById(R.id.cancelpass);
        auth = FirebaseAuth.getInstance();
        As=new AuthenticationStudent(auth,this);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
checkText();
        OnButton();
    }
public void checkText(){

      if(oldpass.getText().toString().equals("")){
          oldpass.setError("Please enter the old pass");
      flag=false;
      return;
      }
    if(confirmOldPass.getText().toString().equals("")){
        confirmOldPass.setError("Please confirm the old password");
        flag=false;
        return;
    }
    if(!oldpass.getText().toString().equals(confirmOldPass.getText().toString())){
        confirmOldPass.setError("This password does not match with the above entered password");
        flag=false;
        return;
    }
    if(newPass.getText().toString().equals("")){
        newPass.setError("Please enter your new password");
        flag=false;
        return;
    }
    else{
        flag=true;
    }
}
public void OnButton(){
    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i =new Intent(UpdatePasswordActivity.this,NavigationActivity.class);
            startActivity(i);
            finish();
        }
    });
update.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        checkText();
        if(flag){
            progressBar.setVisibility(View.VISIBLE);
           As.UpdatePassword(newPass.getText().toString(),progressBar);
           Intent i=new Intent(UpdatePasswordActivity.this,NavigationActivity.class);
           startActivity(i);
           finish();
        }
    }
});
}

}
