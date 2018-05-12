package com.example.dell.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Tasneem on 5/4/2018.
 */
@IgnoreExtraProperties
public class Post implements Serializable{
   public  String department;
   public String id;
    public String post;
    public String time;
    public String name;
    public String status;
    public String level;
    public String studentid;
    public Post(){}
    public  Post(String department,String post){
        this.department=department;
        this.post=post;
    }
}
