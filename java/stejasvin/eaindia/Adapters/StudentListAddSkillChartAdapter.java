


package stejasvin.eaindia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import stejasvin.eaindia.Objects.Student;
import stejasvin.eaindia.R;
import stejasvin.eaindia.databases.StudentDatabaseHandler;

/**
 * This class is a customized array adapter used to display the List of
 * messages and their details in a list.
 *
 * @author stejasvin
 * @since v1.0
 */

public class StudentListAddSkillChartAdapter extends ArrayAdapter {

    List<Student> studentList;
    int textViewResourceId = R.layout.single_list_item_student_list_add_skill_chart;

    /**
     * Context
     */
    private Context context;
    StudentDatabaseHandler studentDatabaseHandler = null;
    ArrayList<String> studNameList;
    ArrayAdapter<String> namesAdapter;

    public StudentListAddSkillChartAdapter(Context context, List<Student> studentList, ArrayList<String> studNameList, ArrayAdapter<String> namesAdapter) {
        super(context, R.layout.single_list_item_student_list_add_skill_chart, studentList);
        this.context = context;
        this.studentList = studentList;
        studentDatabaseHandler = new StudentDatabaseHandler(context);
        this.studNameList = studNameList;
        this.namesAdapter = namesAdapter;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(textViewResourceId, parent, false); // inflate view from xml file
        }
        row.setEnabled(false);

        TextView tvStd = (TextView) row.findViewById(R.id.single_list_item_std_name);
        tvStd.setText(studentList.get(position).getName());

        Button bDel = (Button) row.findViewById(R.id.single_list_item_std_del);
        final View finalRow = row;
        bDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                studNameList.add(studentList.get(position).getName());
                studentList.remove(position);
                namesAdapter.notifyDataSetChanged();
                notifyDataSetChanged();
                //finalRow.setVisibility(View.GONE);
            }
        });
        return row;
    }

}