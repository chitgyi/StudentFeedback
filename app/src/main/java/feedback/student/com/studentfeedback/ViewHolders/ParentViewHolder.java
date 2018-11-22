package feedback.student.com.studentfeedback.ViewHolders;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import feedback.student.com.studentfeedback.R;

public class ParentViewHolder extends GroupViewHolder implements View.OnClickListener {
    boolean event = false;
    private TextView textView, date;
    private ImageView image;

    public ParentViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.post_header);
        date = itemView.findViewById(R.id.post_date);
        image = itemView.findViewById(R.id.arrow);
        image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        event = !event;
        if (event)
            image.setRotation(270);
        else
            image.setRotation(90);

        super.onClick(v);
    }

    public void setHeaderNDate(String string) {
        String[] splitstr = string.split("\n");
        this.textView.setText(splitstr[0]);
        this.date.setText(setDate(splitstr[1]));
    }

    private String setDate(String mydatestr) {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        simpleDateFormat1.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date mydate = simpleDateFormat1.parse(mydatestr);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy hh:mm a");
            Calendar calendar = Calendar.getInstance();
            int mymonth = mydate.getMonth();
            int month = calendar.get(Calendar.MONTH);
            int myyear = mydate.getYear() + 1900;
            int year = calendar.get(Calendar.YEAR);
            if (mydate.getDate() == calendar.get(Calendar.DATE) && mymonth == month && myyear == year) {
                return "Today";
            } else if (calendar.get(Calendar.DATE) - mydate.getDate() == 1 && mymonth == month && myyear == year) {
                return "Yesterday";
            } else {
                return simpleDateFormat.format(mydate);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
