package feedback.student.com.studentfeedback.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import feedback.student.com.studentfeedback.R;
import feedback.student.com.studentfeedback.network.Changepassword;

public class ChangePasswrodFragment extends Fragment {
    Button change;
    EditText current_psw, new_pasw, confirm_psw;
    String confirm_psw_str = "";
    String new_psw_str = "";
    String str = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(container.getContext()).inflate(R.layout.change_password, container, false);
        change = view.findViewById(R.id.change_btn);
        current_psw = view.findViewById(R.id.current_psw);
        new_pasw = view.findViewById(R.id.new_psw);
        confirm_psw = view.findViewById(R.id.confirm_psw);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String current_psw_str = getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE).getString("current_psw", null);
                //Toast.makeText(getContext(), current_psw_str, Toast.LENGTH_LONG).show();
                if (isOnline()) {
                    if (!current_psw.getText().toString().isEmpty()) {
                        str = current_psw.getText().toString();
                        if (current_psw_str.equals(str)) {
                            if (!new_pasw.getText().toString().isEmpty() || !confirm_psw.getText().toString().isEmpty()) {
                                //do
                                new_psw_str = new_pasw.getText().toString();
                                confirm_psw_str = confirm_psw.getText().toString();
                                if (new_psw_str.equals(confirm_psw_str)) {
                                    //do
                                    try {
                                        JSONObject jsonObject = new JSONObject(getActivity().getIntent().getExtras().getString("json"));
                                        String id = jsonObject.getJSONObject("action").getString("id");
                                        if (!id.isEmpty()) {
                                            new Changepassword(getActivity()).execute(id, new_psw_str);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    new_pasw.setError("New password didn't match");
                                }
                            } else {
                                new_pasw.setError("Please fill");
                            }
                        } else {
                            current_psw.setError("Current password is incorrect");
                        }
                    } else {
                        current_psw.setError("Please fill current password");
                    }
                }else{
                    Toast.makeText(getContext(),"Please check internet connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Change Password");
    }
}
