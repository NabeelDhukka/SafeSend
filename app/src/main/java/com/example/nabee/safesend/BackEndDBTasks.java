package com.example.nabee.safesend;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Nabee on 11/30/2017.
 */

public class BackEndDBTasks extends AsyncTask<String, Void, Void>{

    Context cntx
    BackEndDBTasks(Context cntx){
        this.cntx = cntx;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(String... params) {
        return null;
    }


}
