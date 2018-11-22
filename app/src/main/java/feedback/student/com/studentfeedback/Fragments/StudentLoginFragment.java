package feedback.student.com.studentfeedback.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import feedback.student.com.studentfeedback.R;
import feedback.student.com.studentfeedback.network.MyTask;

public class StudentLoginFragment extends Fragment {
    TextView header;
    Button next;
    Spinner year, major;
    CheckBox remember;
    String str;
    MyTask task;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.student_login, container, false);
        header = v.findViewById(R.id.student_header);
        next = v.findViewById(R.id.stu_next);
        year = v.findViewById(R.id.year);
        major = v.findViewById(R.id.major);
        remember = v.findViewById(R.id.remember_major);
        textAnimate();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = year.getSelectedItem().toString() + "-BE-" + major.getSelectedItem().toString();
                //Toast.makeText(getContext(),str,Toast.LENGTH_SHORT).show();
                if (remember.isChecked()) {
                    SharedPreferences.Editor pref = getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE).edit();
                    pref.putString("major", str);
                    pref.commit();
                }
                if (isOnline()) {
                    getContext().getSharedPreferences("mypref",Context.MODE_PRIVATE).edit()
                            .putString("post_major",str)
                            .commit();
                    task = (MyTask) new MyTask(getContext()).execute(str);
                    task.setActivity(getActivity());
                } else {
                    Snackbar.make(v.findViewById(R.id.student_login_layout), "Please check out your internet connection.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    public void textAnimate() {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(800);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        header.setAnimation(anim);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}

