package com.example.dell.firebase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;

/**
 * Created by Tasneem on 5/7/2018.
 */

public class StudentKeys {
    public void setKey(String key, String value, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public String getvalue(String key,Context context){
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        String val = pref.getString(key, null);
        return val;
    }
    public void HelpDialogue(String Message,Context context){
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(Message).setTitle("Help ?");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.setCancelable(true);
            }
        });
        alert.show();
    }
}
