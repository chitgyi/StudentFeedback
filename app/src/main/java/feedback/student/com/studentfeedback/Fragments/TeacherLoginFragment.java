package feedback.student.com.studentfeedback.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import feedback.student.com.studentfeedback.R;
import feedback.student.com.studentfeedback.network.RetrivePost;

public class TeacherLoginFragment extends Fragment {
    TextView header;
    EditText username, password;
    CheckBox checkBox;
    Button login;
    String checked = "false";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.teacher_login, container, false);
        header = v.findViewById(R.id.teacher_header);
        username = v.findViewById(R.id.username);
        password = v.findViewById(R.id.password);
        checkBox = v.findViewById(R.id.remember_acc);
        login = v.findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline()) {
                    if (username.getText().toString().isEmpty()) {
                        username.setError("Please fill username");
                    } else if (password.getText().toString().isEmpty()) {
                        password.setError("Please fill password");
                    } else {
                        String user = username.getText().toString();
                        String psw = password.getText().toString();
                        if (checkBox.isChecked()) {
                            checked = "true";
                        }
                        getContext().getSharedPreferences("mypref",Context.MODE_PRIVATE).edit()
                                .putString("current_psw",password.getText().toString())
                                .commit();
                        RetrivePost retrivePost = (RetrivePost) new RetrivePost(getContext()).execute(user, psw, checked);
                        retrivePost.finishActivity(getActivity());
                    }
                }else{
                    Toast.makeText(getContext(),"Please check your internet connection.",Toast.LENGTH_LONG).show();
                }
            }
        });
        textAnimate();
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
