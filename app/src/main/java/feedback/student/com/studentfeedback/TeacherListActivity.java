package feedback.student.com.studentfeedback;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import feedback.student.com.studentfeedback.Adapters.RecyclerAdapter;
import feedback.student.com.studentfeedback.Models.Teacher;

public class TeacherListActivity extends AppCompatActivity {
    RecyclerView teacherList;
    List<Teacher> teachers;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_list);
        teachers = new ArrayList<Teacher>();
        teacherList = findViewById(R.id.teacher_list_view);
        String json = getIntent().getExtras().getString("response");
        String title = getIntent().getExtras().getString("major");
        getSupportActionBar().setTitle(title);
        //Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("teachers");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String name = object.getString("teacher_name");
                String subject = object.getString("subject");
                int id = object.getInt("id");
                teachers.add(new Teacher(id,name,subject));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!teachers.isEmpty()){
           teacherList.setLayoutManager(new LinearLayoutManager(this));
           teacherList.setAdapter(new RecyclerAdapter(this,teachers));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                SharedPreferences.Editor preferences = getSharedPreferences("mypref",MODE_PRIVATE).edit();
                preferences.remove("major");
                preferences.commit();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case  R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Are your sure to exit from app?");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }
}
