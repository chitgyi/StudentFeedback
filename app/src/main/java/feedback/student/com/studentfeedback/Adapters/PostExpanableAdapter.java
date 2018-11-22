package feedback.student.com.studentfeedback.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import feedback.student.com.studentfeedback.Models.Child;
import feedback.student.com.studentfeedback.R;
import feedback.student.com.studentfeedback.ViewHolders.ChildViewHolder;
import feedback.student.com.studentfeedback.ViewHolders.ParentViewHolder;

public class PostExpanableAdapter extends ExpandableRecyclerViewAdapter<ParentViewHolder, ChildViewHolder>{

    public PostExpanableAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public ParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_post_item,parent,false);
        return new ParentViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post,parent,false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        Child post = (Child)group.getItems().get(childIndex);
        holder.setText(post.getPost());
    }

    @Override
    public void onBindGroupViewHolder(ParentViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setHeaderNDate(group.getTitle());
    }
}
