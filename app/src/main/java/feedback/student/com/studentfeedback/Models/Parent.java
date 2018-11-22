package feedback.student.com.studentfeedback.Models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Parent extends ExpandableGroup {
    public Parent(String title, List<Child> items) {
        super(title, items);
    }
}
