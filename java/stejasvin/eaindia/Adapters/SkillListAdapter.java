


package stejasvin.eaindia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import stejasvin.eaindia.Objects.Skill;
import stejasvin.eaindia.R;
import stejasvin.eaindia.databases.SkillDatabaseHandler;

/**
 * This class is a customized array adapter used to display the List of
 * messages and their details in a list.
 *
 * @author stejasvin
 * @since v1.0
 */

public class SkillListAdapter extends ArrayAdapter {

    List<Skill> skillList;
    int textViewResourceId = R.layout.single_list_item_skill_list;

    private Context context;
    SkillDatabaseHandler skillDatabaseHandler = null;

    public SkillListAdapter(Context context, List<Skill> skillList) {
        super(context, R.layout.single_list_item_skill_list, skillList);
        this.context = context;
        this.skillList = skillList;
        skillDatabaseHandler = new SkillDatabaseHandler(context);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(textViewResourceId, parent, false); // inflate view from xml file
        }
        row.setEnabled(false);

        TextView tvName = (TextView) row.findViewById(R.id.single_list_item_skill_name);
        tvName.setText(skillList.get(position).getName());
        TextView tvSubject = (TextView) row.findViewById(R.id.single_list_item_skill_subject);
        tvSubject.setText(skillList.get(position).getSubject());
        return row;
    }

}