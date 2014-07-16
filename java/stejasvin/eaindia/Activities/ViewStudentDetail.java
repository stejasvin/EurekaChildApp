package stejasvin.eaindia.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import stejasvin.eaindia.Objects.Skill;
import stejasvin.eaindia.Objects.Student;
import stejasvin.eaindia.R;
import stejasvin.eaindia.databases.SkillDatabaseHandler;
import stejasvin.eaindia.databases.StudentDatabaseHandler;

public class ViewStudentDetail extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_detail);

        StudentDatabaseHandler studentDatabaseHandler = new StudentDatabaseHandler(this);
        SkillDatabaseHandler skillDatabaseHandler = new SkillDatabaseHandler(this);
        Student student = studentDatabaseHandler.getStudent(getIntent().getIntExtra("stejasvin.eaindia.LID",0));
        ((TextView)findViewById(R.id.add_st_name)).setText(student.getName());
        ((TextView)findViewById(R.id.add_st_roll)).setText(student.getRoll());
        ((TextView)findViewById(R.id.add_st_std)).setText(student.getStd());
        ((TextView)findViewById(R.id.add_st_gender)).setText(student.getGender());
        TableLayout tl = (TableLayout)findViewById(R.id.add_st_table);
        TableRow.LayoutParams nameLp = new TableRow.LayoutParams();
        nameLp.gravity = Gravity.LEFT;
        TableRow.LayoutParams dateLp = new TableRow.LayoutParams();
        nameLp.gravity = Gravity.RIGHT;

        String[] skillIds = student.getSkills().split(":");
        if(skillIds.length > 0){
            int[] ids = new int[skillIds.length];
            for(int i=0;i<skillIds.length;i++){
                try {
                    ids[i] = Integer.decode(skillIds[i]);
                    Skill skill = skillDatabaseHandler.getSkill(ids[i]);
                    TextView tvName = new TextView(this);
                    tvName.setText(skill.getName());
                    tvName.setLayoutParams(nameLp);
                    TextView tvDate = new TextView(this);
                    tvDate.setText(skill.getDateOfCreation());
                    tvDate.setLayoutParams(dateLp);

                    TableRow tr = new TableRow(this);
                    tr.setOrientation(LinearLayout.HORIZONTAL);
                    tr.addView(tvName);
                    tr.addView(tvDate);

                    tl.addView(tr);

                }catch (NumberFormatException e){
                    e.printStackTrace();
                }

            }
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_student_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
