package com.example.dell.firebase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Tasneem on 5/7/2018.
 */

public class AuthenticationStudent {
    private FirebaseAuth auth;
    private Context context;
    private boolean flag=false;
    public AuthenticationStudent(FirebaseAuth auth,Context context){
        this.auth=auth;
        this.context=context;
    }
    public void UpdateEmail(String email){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail(email.trim())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Email address is updated.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Failed to update email!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void UpdatePassword(String password, final ProgressBar progressBar){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Password is updated!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to update password!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
    public boolean Signout(){
        flag=false;
        auth.signOut();
        FirebaseAuth.AuthStateListener authListener=new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                 flag=true;
                }
            }
        };
        return flag;
    }
}
