package com.example.dell.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Tasneem on 5/6/2018.
 */

public class PostHandler {
    DatabaseReference ref;
    Boolean saved;
    ArrayList<String> arr;
    List_handler adapter;
    String Key;
    String text;
    String child;
    String ID;
    boolean flag=false;
    String department;
    Map<String, Object> childUpdates = new LinkedHashMap<>();

    ArrayList<Post> posts=new ArrayList<>();
    ArrayList<Post> Studentposts=new ArrayList<>();
    ArrayList<String> StudentsKeys=new ArrayList<>();
    private PostInterface listener;

    public PostHandler(DatabaseReference db,Context context) {
        this.ref = db;

        if (context instanceof PostHandler.PostInterface) {
            listener = (PostHandler.PostInterface) context;
        } else {

        }
    }

    public boolean savePost(String userId,String level,String Studentname,String postval,String timeval,String department,String status){
        Key=ref.push().getKey();
        Post post=new Post();
        post.level=level;
        post.name=Studentname;
        post.time=timeval;
        post.department=department;
        post.post=postval;
        post.studentid=userId;
        post.status=status;
        ref.child(Key).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                flag=true;
            }
        });
        return flag;
    }
    public ArrayList<Post> retrieveHash(String filter){
        department=filter;

        Log.d("Test_posts_arr7",""+posts.size());
        ref.orderByChild("department").equalTo(department).limitToLast(15).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                posts.clear();
                Log.d("HHHH",""+dataSnapshot.getChildrenCount());
                for (final DataSnapshot snapshot :dataSnapshot.getChildren() ) {
                   Post post = new Post();
                   post.id=snapshot.getKey();
                    post.department=snapshot.child("department").getValue().toString();
                    post.post=snapshot.child("post").getValue().toString();
                    post.time=snapshot.child("time").getValue().toString();
                    post.name=snapshot.child("name").getValue().toString();
                    Log.d("DDD",""+post.name);
                    post.level=snapshot.child("level").getValue().toString();
                    post.status=snapshot.child("status").getValue().toString();
                    Log.d("DDDD",""+post.status);
                    post.studentid=snapshot.child("studentid").getValue().toString();
                    Log.d("DDDD",""+post.studentid);
                    posts.add(post);

                    Log.d("Test_posts_arr1",post.post);
                    Log.d("Test_posts_arr2",post.name);
                    Log.d("Test_posts_arr3",post.time);
                    Log.d("Test_posts_arr4",post.level);
                    Log.d("Test_posts_arr5",post.department);
                }
                Log.d("Test_posts_",""+posts.size());
                Collections.reverse(posts);
                listener.getPost(posts);
                //Log.d("Test_posts_arr6",posts.get(posts.size()-1).department);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  posts;
    }
    public ArrayList<Post> getArray(){
        return this.posts;
    }
    public void getPosts_of_user(String userID){
        ref.orderByChild("studentid").equalTo(userID).limitToLast(15).addValueEventListener(new ValueEventListener() {
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
                Collections.reverse(Studentposts);
                listener.getPost(Studentposts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
public void getPost(String filter){
    department=filter;
        ref.orderByChild("department").equalTo(department).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post p=dataSnapshot.getValue(Post.class);
               // listener.getAddedPost(p);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
}
public void removePost(String postid){
    ref.child(postid).removeValue();
}
public boolean updatePost(String postid, String post){
    flag=false;
    ref.child(postid).child("post").setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            flag=true;
        }
    });
    return flag;
}

public void editstudent_in_post(String userID){
    ref.orderByChild("studentid").equalTo(userID).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            StudentsKeys.clear();
            for (final DataSnapshot snapshot :dataSnapshot.getChildren() ) {
                StudentsKeys.add(snapshot.getKey());
                Log.d("editstudentKey",snapshot.getKey());
            }
            listener.Editstudent_in_post(StudentsKeys);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
}
    public interface PostInterface{
        public void getPost(ArrayList<Post> p);
        public void Editstudent_in_post(ArrayList<String> s);
    }

}

