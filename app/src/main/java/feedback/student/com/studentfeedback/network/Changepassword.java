package feedback.student.com.studentfeedback.network;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import feedback.student.com.studentfeedback.MainActivity;

public class Changepassword extends AsyncTask<String, Void, String> {
    String url, new_psw;
    String response = "";
    ProgressDialog progressDialog;
    Activity activity;

    public Changepassword(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//insert your url request ,We don't give you my request for security
        this.url = "your url";
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Changing...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... arg) {
        this.new_psw = arg[1];
        try {
            URL url = new URL(this.url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("id", arg[0])
                    .appendQueryParameter("new_psw", arg[1]);
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
        if (s.equals("success") && !s.isEmpty()) {
            activity.getSharedPreferences("mypref",Context.MODE_PRIVATE).edit()
                    .remove("password")
                    .remove("username")
                    .remove("current_psw")
                    .commit();
            final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setMessage("Successfully changed.Please Login again.");
            alert.setCancelable(false);
            alert.setPositiveButton("Ok", new AlertDialog.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    activity.startActivity(new Intent(activity, MainActivity.class));
                    activity.finish();

                }
            });
            alert.show();
        } else if (s.equals("unable")) {
            Toast.makeText(activity, "Unable to change", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, "error " + s, Toast.LENGTH_SHORT).show();
        }
    }
}
