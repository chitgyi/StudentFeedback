package feedback.student.com.studentfeedback.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import feedback.student.com.studentfeedback.AboutActivity;
import feedback.student.com.studentfeedback.MainActivity;
import feedback.student.com.studentfeedback.R;

public class AboutFragment extends Fragment {
    Button change_psw, teacher_logout;
    TextView teacher_name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_about, container, false);
        change_psw = view.findViewById(R.id.change_psw);
        teacher_logout = view.findViewById(R.id.teacher_logout);
        teacher_name = view.findViewById(R.id.teacher_name);

        try {
            JSONObject jsonObject = new JSONObject(getActivity().getIntent().getExtras().getString("json"));
            String name = jsonObject.getJSONObject("action").getString("name");
            teacher_name.setText(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        teacher_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE).edit()
                        .remove("username")
                        .remove("password")
                        .remove("current_psw")
                        .commit();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        change_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.teacher_container, new ChangePasswrodFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");
    }
}
