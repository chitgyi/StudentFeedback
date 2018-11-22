package feedback.student.com.studentfeedback;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import feedback.student.com.studentfeedback.network.InsertPost;

public class FeedbackActivity extends AppCompatActivity {
    Button send;
    String major;
    int id;
    RadioGroup option1_gp, option2_gp, option3_gp,option4_gp;
    String post = "";
    AlertDialog.Builder dialog;
    Context context;
    EditText other_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        option1_gp = findViewById(R.id.option1_gp);
        option2_gp = findViewById(R.id.option2_gp);
        option3_gp = findViewById(R.id.option3_gp);
        option4_gp = findViewById(R.id.option4_gp);

        send = findViewById(R.id.send);
        context = getApplicationContext();
        dialog = new AlertDialog.Builder(this);
        other_feedback = findViewById(R.id.other_feedback);

        id = getIntent().getExtras().getInt("id");
        major = getSharedPreferences("mypref", MODE_PRIVATE).getString("post_major", null);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("teacher"));
        getSupportActionBar().setSubtitle(getIntent().getExtras().getString("subject"));
        // Toast.makeText(getApplicationContext(), major + " and " + id, Toast.LENGTH_LONG).show();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog();
            }
        });

    }

    public void setDialog() {
        dialog.setMessage("Are you sure want to send?");
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog.Builder ok = dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkFeedback();
                String other = other_feedback.getText().toString();
                if (!post.isEmpty() || !other.isEmpty()){
                    InsertPost insertPost = (InsertPost) new InsertPost(FeedbackActivity.this).execute(id+"", major, post, other);
                    //Toast.makeText(getApplicationContext(),major,Toast.LENGTH_SHORT).show();
                    insertPost.setActivity(FeedbackActivity.this);
                    post = "";
                    //Toast.makeText(context, post + "\n " + id, Toast.LENGTH_LONG).show();
                }else {
                    Snackbar.make(findViewById(R.id.feedback),"Please write feedback or choice",Snackbar.LENGTH_LONG).show();
                }


            }
        });
        dialog.show();
    }

    public void checkFeedback(){
        switch ((option1_gp.getCheckedRadioButtonId())) {
            case R.id.option1_good:
                post += getString(R.string.option1) + getString(R.string.good) + "။ ";
                break;
            case R.id.option1_excellent:
                post += getString(R.string.option1) + getString(R.string.excellent)+ "။ ";
                break;
            case R.id.option1_bad:
                post += getString(R.string.option1) + getString(R.string.bad)+ "။ ";
                break;
        }
        switch (option2_gp.getCheckedRadioButtonId()) {
            case R.id.option2_good:
                post += getString(R.string.option2) + getString(R.string.good)+ "။ ";
                break;
            case R.id.option2_excellent:
                post += getString(R.string.option2) + getString(R.string.excellent)+ "။ ";
                break;
            case R.id.option2_bad:
                post += getString(R.string.option2) + getString(R.string.bad)+ "။ ";
                break;
        }
        switch (option3_gp.getCheckedRadioButtonId()) {
            case R.id.option3_good:
                post +=  getString(R.string.option3) + getString(R.string.good)+ "။ ";
                break;
            case R.id.option3_excellent:
                post +=   getString(R.string.option3) + getString(R.string.excellent)+ "။ ";
                break;
            case R.id.option3_bad:
                post +=  getString(R.string.option3) + getString(R.string.bad)+ "။ ";
                break;
        }
        switch (option4_gp.getCheckedRadioButtonId()) {
            case R.id.option4_good:
                post +=  getString(R.string.option4) + getString(R.string.good)+ "။ ";
                break;
            case R.id.option4_excellent:
                post +=  getString(R.string.option4) + getString(R.string.excellent)+ "။ ";
                break;
            case R.id.option4_bad:
                post += getString(R.string.option4) + getString(R.string.bad)+ "။ ";
                break;
        }
    }
    public void cancel(View v) {
        finish();
    }

}
