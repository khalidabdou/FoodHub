package com.recipes.foodhub.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.recipes.foodhub.model.user;

public class doInBackgroundTask extends AsyncTask <user,Integer,Integer > {
    Context mContext;



    public doInBackgroundTask(Context mContext) {
        this.mContext=mContext;
    }
    user_manager manager=new user_manager(mContext);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(mContext, "pre", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Toast.makeText(mContext, integer.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Toast.makeText(mContext, "update", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Integer doInBackground(user... users) {

        manager.addUser(users[0]);
        manager.adduserShered(users[0]);
        return null;
    }
}
