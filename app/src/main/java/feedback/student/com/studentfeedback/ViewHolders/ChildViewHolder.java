package feedback.student.com.studentfeedback.ViewHolders;

import android.view.View;
import android.widget.TextView;

import feedback.student.com.studentfeedback.R;

public class ChildViewHolder extends com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder {
    private TextView textView;
    public ChildViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.post);
    }
    public void setText(String string){
        this.textView.setText(string);
    }
}
