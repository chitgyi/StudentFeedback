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

public class InsertPost extends AsyncTask<String, Void, String> {
    ProgressDialog progressDialog;
    String url;
    String response = "";
    Context context;
    Activity activity;
    public InsertPost(Context context){
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//insert your url request ,We don't give you my request for security
        url = "your url";
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Posting....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... data) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("id", data[0])
                    .appendQueryParameter("year",data[1])
                    .appendQueryParameter("post", data[2])
                    .appendQueryParameter("other_post", data[3]);
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

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        if (s.equals("Ok")){
            this.activity.finish();
            Toast.makeText(context,"Successfully posted..",Toast.LENGTH_LONG).show();
        }else if (s.equals("Fail")){
            Toast.makeText(context,"Fail to Post",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context,s,Toast.LENGTH_LONG).show();
        }

    }
    public void setActivity(Activity activity){
        this.activity = activity;
    }
}
