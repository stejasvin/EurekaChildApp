package stejasvin.eaindia.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

import stejasvin.eaindia.Adapters.SkillChartListAdapter;
import stejasvin.eaindia.Objects.SkillChart;
import stejasvin.eaindia.databases.SkillChartDatabaseHandler;

/**
 * Created by stejasvin on 7/12/2014.
 */
public class ViewSkillChartList extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SkillChartDatabaseHandler skillChartDatabaseHandler = new SkillChartDatabaseHandler(this);
        final List<SkillChart> skillChartList = skillChartDatabaseHandler.getAllSkillCharts();
        if(skillChartList.size()>0){
            setListAdapter(new SkillChartListAdapter(ViewSkillChartList.this,skillChartList));
            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(ViewSkillChartList.this,ViewSkillChart.class);
                    i.putExtra("stejasvin.eaindia.LID", skillChartList.get(position).getLid());
                    startActivity(i);
                }
            });
        }
        else{
            finish();
            Toast.makeText(this, "No students added", Toast.LENGTH_SHORT).show();
        }

    }

}
