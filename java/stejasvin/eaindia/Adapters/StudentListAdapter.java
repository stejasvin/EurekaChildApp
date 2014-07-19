


package stejasvin.eaindia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import stejasvin.eaindia.R;
import stejasvin.eaindia.Objects.Student;
import stejasvin.eaindia.databases.StudentDatabaseHandler;

/**
 * This class is a customized array adapter used to display the List of
 * messages and their details in a list.
 *
 * @author stejasvin
 * @since v1.0
 */

public class StudentListAdapter extends ArrayAdapter {

    List<Student> studentList;
    int textViewResourceId = R.layout.single_list_item_student_list;

    /**
     * Context
     */
    private Context context;
    StudentDatabaseHandler studentDatabaseHandler = null;

    public StudentListAdapter(Context context, List<Student> studentList) {
        super(context, R.layout.single_list_item_student_list, studentList);
        this.context = context;
        this.studentList = studentList;
        studentDatabaseHandler = new StudentDatabaseHandler(context);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(textViewResourceId, parent, false); // inflate view from xml file
        }

        TextView tvCourse = (TextView) row.findViewById(R.id.single_list_item_std_list_name);
        if(position==0) {
            tvCourse.setText("List of Students");
            tvCourse.setTextSize(30);
            tvCourse.setPadding(5,10,5,10);

        }else {
            tvCourse.setTextSize(20);
            tvCourse.setPadding(5,10,5,10);
            tvCourse.setText(studentList.get(position-1).getName());
        }
        return row;
    }

    @Override
    public int getCount() {
        return studentList.size()+1;
    }
}