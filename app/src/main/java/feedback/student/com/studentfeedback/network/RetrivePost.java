package feedback.student.com.studentfeedback.network;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import feedback.student.com.studentfeedback.StudentPostActivity;

public class RetrivePost extends AsyncTask<String,Void,String> {
    private String url;
    private  String chechk,user,pass;
    private String response = "";
    private Context context;
    private ProgressDialog dialog;
    private Activity activity;
    public  RetrivePost(Context context){
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//insert your url request ,We don't give you my request for security
        url = "your url";
        dialog = new ProgressDialog(this.context);
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... parms) {
        user = parms[0];
        pass = parms[1];
        chechk = parms[2];
        try {
            URL url = new URL(this.url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("username", parms[0])
                    .appendQueryParameter("password", parms[1]);
            String query = builder.build().getEncodedQuery();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            bufferedWriter.write(query);
            bufferedWriter.flush();
            bufferedWriter.close();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    this.response += line;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.response;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        this.dialog.dismiss();
        if (!response.equals("fail") && !response.isEmpty()){
            if (chechk == "true"){
                SharedPreferences.Editor editor = context.getSharedPreferences("mypref", Context.MODE_PRIVATE).edit();
                editor.putString("username", user);
                editor.putString("password", pass);
                editor.commit();
            }
            Intent intent = new Intent(this.context, StudentPostActivity.class);
            intent.putExtra("json", response);
            this.context.startActivity(intent);
            this.activity.finish();
        }else if (response.equals("fail")){
            Toast.makeText(this.context, "Incorrect Username or Password", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context,"Error occurring..."+response,Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(this.context, response,Toast.LENGTH_LONG).show();
    }
    public void finishActivity(Activity activity){
        this.activity = activity;
    }
}
