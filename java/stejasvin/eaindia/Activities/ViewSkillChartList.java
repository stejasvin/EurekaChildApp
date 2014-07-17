package stejasvin.eaindia.Activities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

import stejasvin.eaindia.Adapters.SkillChartListAdapter;
import stejasvin.eaindia.Objects.SkillChart;
import stejasvin.eaindia.R;
import stejasvin.eaindia.databases.SkillChartDatabaseHandler;

/**
 * Created by stejasvin on 7/12/2014.
 */
public class ViewSkillChartList extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SkillChartDatabaseHandler skillChartDatabaseHandler = new SkillChartDatabaseHandler(this);
        final List<SkillChart> skillChartList = skillChartDatabaseHandler.getAllSkillCharts();
        if(skillChartList.size()>0){

            final SkillChartListAdapter skillChartListAdapter = new SkillChartListAdapter(ViewSkillChartList.this,skillChartList);
            setListAdapter(skillChartListAdapter);

            getListView().setHeaderDividersEnabled(true);
            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position==0)
                        return;
                    Intent i = new Intent(ViewSkillChartList.this,ViewSkillChart.class);
                    i.putExtra("stejasvin.eaindia.LID", skillChartList.get(position-1).getLid());
                    startActivity(i);
                }
            });

            getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    if(position==0)
                        return false;
                    final int scPos = position -1;
                    AlertDialog.Builder alert = new AlertDialog.Builder(ViewSkillChartList.this);
                    alert.setTitle("Menu"); // set title for the alert
                    alert.setMessage("Select an option or press back to cancel the menu"); // this sets the message in the alert dialog
                    alert.setIcon(R.drawable.ic_launcher); // this sets icon for the dialog
                    alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent i = new Intent(ViewSkillChartList.this,AddSkillChart.class);
                            i.putExtra("stejasvin.ACT_EDIT",true);
                            i.putExtra("stejasvin.EDIT_SC_LID", skillChartList.get(scPos).getLid());
                            startActivity(i);
                            finish();
                        }
                    });

                    alert.setNegativeButton("Remove", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            AlertDialog.Builder alert1 = new AlertDialog.Builder(ViewSkillChartList.this);
                            alert1.setTitle("Confirmation"); // set title for the alert
                            alert1.setMessage("Are you sure you want to remove this skillChart? This change is irreversible!"); // this sets the message in the alert dialog
                            alert1.setIcon(R.drawable.ic_launcher); // this sets icon for the dialog
                            alert1.setPositiveButton("Remove", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    SkillChartDatabaseHandler skillChartDatabaseHandler1 = new SkillChartDatabaseHandler((ViewSkillChartList.this));
                                    skillChartDatabaseHandler1.remove(skillChartList.get(scPos).getLid());
                                    skillChartList.remove(scPos);
                                    skillChartListAdapter.notifyDataSetChanged();

                                }
                            });

                            alert1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });
                            alert1.setCancelable(true);
                            alert1.show();

                        }
                    });

                    alert.setCancelable(true);
                    alert.show();
                    return false;
                }
            });
        }
        else{
            finish();
            Toast.makeText(this, "No students added", Toast.LENGTH_SHORT).show();
        }

    }

}
