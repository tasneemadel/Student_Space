package com.example.dell.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tasneem on 5/4/2018.
 */

public class StudentHandler {
    DatabaseReference ref;
    Boolean saved;
    ArrayList<String> arr;
    Context activity;
    List_handler adapter;
    String Key;
    String text;
    String child;
    boolean flag=false;
    String department;
    Map<String, Object> childUpdates = new LinkedHashMap<>();
    ArrayList<Student> studentlist = new ArrayList<Student>();
    ArrayList<Student> students=new ArrayList<>();
    public StudentHandler(DatabaseReference db) {
        this.ref = db;
    }
    String ID;
  public boolean save(String StudentId, final String Child, String txt){
        this.Key=StudentId;
        this.child=Child;
        this.text=txt;
       // this.flag=false;
      ref.child(Key).child(child).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              arr=(ArrayList)dataSnapshot.getValue();
              Log.i("Ret_Post"," "+arr.size());
              if (arr!=null){
                  arr.add( text);
              }
              else{
                  arr=new ArrayList<String>();
                  arr.add(text);
              }
              ref.child( Key).child(child).setValue(arr).addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      flag=true;
                  }
              });
              Log.i("Post_Test"," "+arr.get(arr.size()-1));
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });

      return flag;
  }
  public ArrayList<Student> retrieveStudent(Context context,String filter){
      activity=context;
      department=filter;
      ref.orderByChild("department").equalTo(department).addValueEventListener(new ValueEventListener() {

          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {



              Log.d("Firebase_Test", "Value is: " +dataSnapshot.getChildrenCount());
              for (final DataSnapshot snapshot :dataSnapshot.getChildren() ) {
                  Student student = new Student();
                  student.setname(snapshot.child("name").getValue().toString());
                  student.setdepartment(snapshot.child("department").getValue().toString());
                  student.setlevel(snapshot.child("level").getValue().toString());
                  student.setstatus(snapshot.child("status").getValue().toString());
                  //student.time=(ArrayList<String>)snapshot.child("time").getValue();
                  //student.posts=(ArrayList<String>)snapshot.child("posts").getValue();
                  studentlist.add(student);
                  Log.d("Time_Test", "Value is: " +student.gettime().get(student.gettime().size()-1));
                  Log.d("Post_Test", "Value is: " +student.getPost().get(student.getPost().size()-1));
              }

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
              System.out.println("The read failed: " + databaseError.getCode());
          }
      });
      return studentlist;
  }

  public boolean saveHash(String id,String postval,String timeval){
      Key=id;
      ID=ref.push().getKey();
      childUpdates.put("/"+Key+"/posts/"+ID, postval);
      childUpdates.put("/"+Key+"/time/"+ID, timeval);
      ref.updateChildren(childUpdates);
      return true;
    //  return checkStudentExistence(postval);
  }
  public ArrayList<Student> retrieveHash(String filter){
      department=filter;
      ref.orderByChild("department").equalTo(department).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              Log.i("HHHH",""+dataSnapshot.getChildrenCount());
              for (final DataSnapshot snapshot :dataSnapshot.getChildren() ) {
                  Student student = new Student();
                  student.setname(snapshot.child("name").getValue().toString());
                  student.setdepartment(snapshot.child("department").getValue().toString());
                  student.setlevel(snapshot.child("level").getValue().toString());
                  student.setstatus(snapshot.child("status").getValue().toString());
                  student.postUpdates=(HashMap<String,Object>)snapshot.child("posts").getValue();
                  student.timeUpdates=(HashMap<String,Object>)snapshot.child("time").getValue();
                  //student.posts= student.postUpdates.values();
                 // List<Object> t=(List<Object>) student.postUpdates.values();

                  Collection<Object> values = student.postUpdates.values();
                  Collection<Object> timevalues = student.timeUpdates.values();
                  ArrayList<Object> postValues = new ArrayList<Object>(values);
                  ArrayList<Object> timeValues = new ArrayList<Object>(timevalues);
                  student.posts=postValues;
                  student.time=timeValues;
                  //student.time=(ArrayList<Object>)student.timeUpdates.values();

                  studentlist.add(student);
                  Log.d("Time_Test", "Value is: " +student.gettime().get(student.gettime().size()-1));
                  Log.d("Post_Test", "Value is: " +student.getPost().get(student.getPost().size()-1));
                  Log.d("ARR_Test", "Value is: " +student.posts.size());
                  Log.d("FIRST_ITEM", "Value is: " +student.posts.get(0));
                  Log.d("SECOND_ITEM", "Value is: " +student.posts.get(1));
                  Log.d("THIRD_ITEM", "Value is: " +student.posts.get(2));
                  Log.d("FORTH_ITEM", "Value is: " +student.posts.get(3));
                  Log.d("FIFTH_ITEM", "Value is: " +student.posts.get(4));
              }

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });
      return  studentlist;
  }

  public boolean checkStudentExistence(String value){
      ref.child(Key).orderByChild(ID).equalTo(value).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              if(dataSnapshot.exists()) {
                  flag=true;
              }
              else {flag=false;}
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });
  return flag;
  }

    public boolean saveNewStudent(String key,String status,String level,String Studentname,String department,String email){
        Key=key;
        Student s=new Student();
        s.setname(Studentname);
        s.setstatus(status);
        s.setdepartment(department);
        s.setlevel(level);
        s.email=email;
        flag=false;
        ref.child(Key).setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                flag=true;
            }
        });
        return flag;
    }
}
