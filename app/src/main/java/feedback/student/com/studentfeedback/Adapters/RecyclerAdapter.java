package feedback.student.com.studentfeedback.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import feedback.student.com.studentfeedback.Models.Teacher;
import feedback.student.com.studentfeedback.FeedbackActivity;
import feedback.student.com.studentfeedback.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    List<Teacher> teachers;
    Context context;
    public RecyclerAdapter(Context context, List<Teacher> teachers){
        this.teachers = teachers;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_item_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(teachers.get(position).getName());
        holder.subject.setText(teachers.get(position).getSubject());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,teachers.get(position).getId()+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, FeedbackActivity.class);
                intent.putExtra("teacher",teachers.get(position).getName());
                intent.putExtra("subject",teachers.get(position).getSubject());
                intent.putExtra("id",teachers.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,subject;
        View view;
        public MyViewHolder(View view){
            super(view);
            this.view = view;
            name = view.findViewById(R.id.teacher_name);
            subject = view.findViewById(R.id.subject);
        }
    }
}
