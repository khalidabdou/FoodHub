package com.recipes.foodhub.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref   {

    Context context;
    String PREFS_NAME ="myshered";
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
   public SharedPref(Context context){
       this.context=context;
       sharedPref = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
       editor = sharedPref.edit();
   }

    public void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }
    public int getInt(String key){

        return sharedPref.getInt(key,0);
    }
    public void saveString(String key, String  value) {
        editor.putString(key, value);
        editor.commit();
    }
    public  String getString(String key) {
        return sharedPref.getString(key, "");
    }
}
