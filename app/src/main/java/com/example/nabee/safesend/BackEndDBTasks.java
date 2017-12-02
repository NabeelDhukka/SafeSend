package com.example.nabee.safesend;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import static android.content.ContentValues.TAG;

/**
 * Created by Nabee on 11/30/2017.
 */

public class BackEndDBTasks extends AsyncTask<String,Void , String>{
    AlertDialog alertDialog;
    Context cntx;
    public BackEndDBTasksInterface delegate = null;

    BackEndDBTasks(Context cntx){
        this.cntx = cntx;

    }

    @Override
    protected void onPreExecute() {

        alertDialog = new AlertDialog.Builder(cntx).create();
            alertDialog.setTitle("Loggin Information");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        if(result == null){
            Log.d(TAG, "RESULT IS NULL ");
        }
        if (result.equals("Registration Success")) {
            Toast.makeText(cntx, result, Toast.LENGTH_LONG).show();
        }
        else {
            alertDialog.setMessage(result);
            alertDialog.show();
            delegate.proccessFinish(result);
        }
    }

    @Override
    protected String doInBackground(String... params) {

        String reg_url = "http://10.0.2.2/safesendaccounts/register.php";
        String purpose = params[0];
        String login_url =  "http://10.0.2.2/safesendaccounts/login.php";
        //String reg_url =  "http://10.0.2.2/safesendaccounts/register.php";
        //int type = 0;
        if(purpose.equals("register")){
            Log.d(TAG, "in REGISTER ");
            String user_name = params[1];
            String user_pass = params[2];
            try {
                Log.d(TAG, "IN REGISTER TRY ");
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                Log.d(TAG, "OUTPUT STREAM");
                OutputStream OS = httpURLConnection.getOutputStream();
                Log.d(TAG, "MAKING BUFFERED WRITER");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" + URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                Log.d(TAG, "MAKING INPUT STREAM");
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                Log.d(TAG, "ABOUT TO RETURN REG SUCCESS ");
                return "Registration Success";
            }
            catch (MalformedURLException e) {
                Log.d(TAG, "IN FIRST CATCH");
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                Log.d(TAG, "IN SECOND CATCH");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.d(TAG, "THIRD CATCH");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d(TAG, "IO CATCH");
                e.printStackTrace();
            }
        }
        else if(purpose.equals("loggin")){
            Log.d(TAG, "in LOGGIN ");
            String user_name = params[1];
            String user_pass = params[2];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" + URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String line = "";
                String response = "";
                while((line = bufferedReader.readLine())!=null){
                    response+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Log.d(TAG, "ABOUT TO RETURN HELLO ");
        return null;
    }
}
