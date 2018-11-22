package feedback.student.com.studentfeedback.Fragments;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import feedback.student.com.studentfeedback.Adapters.PostExpanableAdapter;
import feedback.student.com.studentfeedback.Models.Child;
import feedback.student.com.studentfeedback.Models.Parent;
import feedback.student.com.studentfeedback.R;

public class PostFragment extends Fragment {
    TextView no_data;
    RecyclerView recyclerView;
    List<Parent> list;
    String empty = "a";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_post, container, false);
        no_data = view.findViewById(R.id.no_data);
        recyclerView = view.findViewById(R.id.student_post);
        list = new ArrayList<>();
        String json = getActivity().getIntent().getExtras().getString("json");
        try {
            JSONObject jsonObject = new JSONObject(getActivity().getIntent().getExtras().getString("json"));
            empty = jsonObject.getJSONObject("action").getString("empty");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!isOnline()) {
            no_data.setText("No internet connection.");
        }
        if (empty.equals("true")) {
            no_data.setText("No Feedback!");
        }else {
            pharseJson(json);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new PostExpanableAdapter(list));
        }
        //Toast.makeText(getContext(),json,Toast.LENGTH_LONG).show();

        return view;
    }

    private void pharseJson(String json) {
        try {
            JSONObject object = new JSONObject(json);
            JSONArray jsonArray = object.getJSONArray("posts");
            //String str = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String year = jsonObject.getString("year");
                String post = jsonObject.getString("post");
                String other_post = jsonObject.getString("other_post");
                String date = jsonObject.getString("created_at");
                //this.list.add(new Post(year, post, other_post, date));
                //str += year+" "+ post+" "+other_post+" "+date+" \n";
                List<Child> children = new ArrayList<>();
                children.add(new Child(post + "\n" + other_post));
                ;
                this.list.add(new Parent(year + "\n" + date, children));
            }
            //no_data.setText(str);
        } catch (JSONException e) {

        }

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Feedback for Teacher");
    }
}
