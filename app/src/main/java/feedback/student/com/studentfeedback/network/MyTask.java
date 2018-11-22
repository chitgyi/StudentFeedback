package feedback.student.com.studentfeedback.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import feedback.student.com.studentfeedback.TeacherListActivity;

public class MyTask extends AsyncTask<String, Void, String> {
    ProgressDialog progressDialog;
    String url;
    String response = "";
    Context context;
    String major;
    Activity activity;
    public MyTask(Context context){
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//insert your url request ,We don't give you my request for security
        url = "your url";
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... major) {
        this.major = major[0];
        try {
            URL url = new URL(this.url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("major", major[0]);
            String query = builder.build().getEncodedQuery();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    this.response += line;
                }
            } else {
                this.response = "fail";
            }
            return this.response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        if (!s.equals("fail") && !s.equals("")){
            Intent intent = new Intent(this.context, TeacherListActivity.class);
            intent.putExtra("response", s);
            intent.putExtra("major", this.major);
            this.context.startActivity(intent);
            this.activity.finish();
        }else{
            Toast.makeText(this.context,s,Toast.LENGTH_LONG).show();
        }

    }
    public void setActivity(Activity activity){
       this.activity = activity;
    }
}
