package feedback.student.com.studentfeedback;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import feedback.student.com.studentfeedback.Fragments.StudentLoginFragment;
import feedback.student.com.studentfeedback.Fragments.TeacherLoginFragment;
import feedback.student.com.studentfeedback.network.MyTask;
import feedback.student.com.studentfeedback.network.RetrivePost;

public class MainActivity extends AppCompatActivity {
    RadioButton student, teacher;
    Button next;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        SharedPreferences preferences = getSharedPreferences("mypref", MODE_PRIVATE);
        String major = preferences.getString("major", null);
        String username = preferences.getString("username",null);
        String password = preferences.getString("password",null);
        setContentView(R.layout.empty_cell);
        if (major != null) {
            if (isOnline()) {
                MyTask task = (MyTask) new MyTask(this).execute(major);
                task.setActivity(this);
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("Please check out your internet connection.");
                dialog.setCancelable(false);
                dialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                dialog.show();
            }

        } else if (username != null && password != null) {
            if (isOnline()) {
                RetrivePost retrivePost = (RetrivePost) new RetrivePost(this).execute(username,password,"true");
                retrivePost.finishActivity(this);
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("Please check out your internet connection.");
                dialog.setCancelable(false);
                dialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                dialog.show();
            }

        } else {
            setContentView(R.layout.main);
            header = findViewById(R.id.header);
            student = findViewById(R.id.student);
            teacher = findViewById(R.id.teacher);
            next = findViewById(R.id.next);

            textAnimate();
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (student.isChecked()) {
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.exit, R.anim.pop_exit)
                                .replace(R.id.container, new StudentLoginFragment())
                                .addToBackStack(null)
                                .commit();
                    } else if (teacher.isChecked()) {
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.exit, R.anim.pop_exit)
                                .replace(R.id.container, new TeacherLoginFragment())
                                .addToBackStack(null)
                                .commit();
                    } else {
                        Snackbar.make(findViewById(R.id.container), "Please select one...", Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(),"Please select one...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void textAnimate() {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(800);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        header.setAnimation(anim);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
