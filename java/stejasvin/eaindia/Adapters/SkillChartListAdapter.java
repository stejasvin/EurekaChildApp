


package stejasvin.eaindia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import stejasvin.eaindia.Objects.SkillChart;
import stejasvin.eaindia.R;
import stejasvin.eaindia.databases.SkillChartDatabaseHandler;

/**
 * This class is a customized array adapter used to display the List of
 * messages and their details in a list.
 *
 * @author stejasvin
 * @since v1.0
 */

public class SkillChartListAdapter extends ArrayAdapter {

    List<SkillChart> skillChartList;
    int textViewResourceId = R.layout.single_list_item_student_list;

    /**
     * Context
     */
    private Context context;
    SkillChartDatabaseHandler skillChartDatabaseHandler = null;

    public SkillChartListAdapter(Context context, List<SkillChart> skillChartList) {
        super(context, R.layout.single_list_item_student_list, skillChartList);
        this.context = context;
        this.skillChartList = skillChartList;
        skillChartDatabaseHandler = new SkillChartDatabaseHandler(context);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(textViewResourceId, parent, false); // inflate view from xml file
        }
        //row.setEnabled(false);
        TextView tvCourse = (TextView) row.findViewById(R.id.single_list_item_std_list_name);
        if(position==0) {
            tvCourse.setText("List of SkillCharts");
            tvCourse.setTextSize(30);
            tvCourse.setPadding(5,10,5,10);

        }else {
            tvCourse.setText(skillChartList.get(position-1).getCentreName());
        }
        return row;
    }

    @Override
    public int getCount() {
        return skillChartList.size()+1;
    }
}