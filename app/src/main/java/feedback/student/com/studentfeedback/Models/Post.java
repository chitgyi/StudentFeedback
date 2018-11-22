package feedback.student.com.studentfeedback.Models;

public class Post {
    private  String year, post, other_post, date;
    public Post(String year, String post, String other_post, String date) {
        this.year = year;
        this.post = post;
        this.other_post = other_post;
        this.date = date;
    }

    public String getYear() {
        return year;
    }

    public String getPost() {
        return post;
    }

    public String getOther_post() {
        return other_post;
    }

    public String getDate() {
        return date;
    }
}
