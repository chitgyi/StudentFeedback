package feedback.student.com.studentfeedback;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import feedback.student.com.studentfeedback.Fragments.AboutFragment;
import feedback.student.com.studentfeedback.Fragments.PostFragment;

public class StudentPostActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    PostFragment postFrgament;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_post);
        navigationView = findViewById(R.id.navigation);
        postFrgament = new PostFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.teacher_container,postFrgament)
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.exit, R.anim.pop_exit)
                .commit();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navi_post:
                        postFrgament = new PostFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.teacher_container,postFrgament)
                                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.exit, R.anim.pop_exit)
                                .commit();
                        return true;
                    case R.id.navi_profile:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.teacher_container,new AboutFragment())
                                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.exit, R.anim.pop_exit)
                                .commit();
                        postFrgament = null;
                        return true;
                }
              return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (postFrgament == null){
            postFrgament = new PostFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.teacher_container,postFrgament)
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.exit, R.anim.pop_exit)
                    .commit();
            navigationView.setSelectedItemId(R.id.navi_post);
        }else if (postFrgament != null){
           AlertDialog.Builder alert = new AlertDialog.Builder(this);
           alert.setMessage("Are you sure want to exit?");
           alert.setPositiveButton("Ok", new AlertDialog.OnClickListener(){
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                 finish();
               }
           });
           alert.setNegativeButton("Cancel", new AlertDialog.OnClickListener(){
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {

               }
           });
           alert.show();
        }
    }
}
