


package stejasvin.eaindia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import stejasvin.eaindia.R;
import stejasvin.eaindia.databases.StudentDatabaseHandler;

/**
 * This class is a customized array adapter used to display the List of
 * messages and their details in a list.
 *
 * @author stejasvin
 * @since v1.0
 */

public class StudentGridListAdapter extends ArrayAdapter {

    List<String> studentList;
    int textViewResourceId = R.layout.single_list_item_student_list_grid;

    /**
     * Context
     */
    private Context context;
    StudentDatabaseHandler studentDatabaseHandler = null;

    public StudentGridListAdapter(Context context, List<String> studentList) {
        super(context, R.layout.single_list_item_student_list_grid, studentList);
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

//        LinearLayout linearLayout = (LinearLayout) row.findViewById(R.id.single_list_item_std_grid_name_list_ll);
//        LinearLayout.LayoutParams layoutParamsList = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 100);
//        linearLayout.setLayoutParams(layoutParamsList);

        TextView tvCourse = (TextView) row.findViewById(R.id.single_list_item_std_grid_name_list_tv);
        tvCourse.setText(studentList.get(position));
        tvCourse.setHeight(100);


        return row;
    }

    @Override
    public int getCount() {
        return studentList.size();
    }
}