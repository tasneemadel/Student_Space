package com.example.dell.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Tasneem on 5/3/2018.
 */
@IgnoreExtraProperties
public class Student implements Serializable {
    public String name;
    public String email;
    public String password;
    public String status;
    public String level;
    public  String department;
    public  ArrayList<Object> posts;
    public ArrayList<Object> time;
    public String id;
    Map<String, Object> postUpdates = new LinkedHashMap<>();
    Map<String, Object> timeUpdates = new LinkedHashMap<>();
    public Student(){
        posts=new ArrayList<Object>();
        time=new ArrayList<Object>();
    }
    public Student(String name,String email,String password,String level,String department ){
        this.name=name;
        this.email=email;
        this.department=department;
        this.password=password;
        this.level=level;
        posts=new ArrayList<Object>();
        time=new ArrayList<Object>();
    }
public void setPassword(String pass){
        this.password=pass;
}


    public void setPost(String post){
        this.posts.add(post);
    }

    public void settime(String time){
        this.time.add(time);
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setname(String name){
        this.name=name;
    }
    public void setstatus(String name){
        this.status=name;
    }
    public void setlevel(String level){
        this.level=level;
    }


    public void setdepartment(String department){
        this.department=department;
    }
    public void setId(String Id){
        this.id=Id;
    }


    public String getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
    }

    public String getname(){
        return this.name;
    }

    public String getlevel(){
        return this.level;
    }


    public String getdepartment(){
        return this.department;
    }
    public String getId(){
        return this.id;
    }



    public ArrayList<Object> getPost(){
        return this.posts;
    }

    public ArrayList<Object> gettime(){
       return this.time;
    }
}
