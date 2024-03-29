package stejasvin.eaindia.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import stejasvin.eaindia.Objects.Skill;
import stejasvin.eaindia.Objects.Student;
import stejasvin.eaindia.R;
import stejasvin.eaindia.databases.SkillDatabaseHandler;
import stejasvin.eaindia.databases.StudentDatabaseHandler;

public class ViewStudentDetail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_detail);

        StudentDatabaseHandler studentDatabaseHandler = new StudentDatabaseHandler(this);
        SkillDatabaseHandler skillDatabaseHandler = new SkillDatabaseHandler(this);
        Student student = studentDatabaseHandler.getStudent(getIntent().getIntExtra("stejasvin.eaindia.LID",0));
        ((TextView)findViewById(R.id.add_st_name)).setText("Name - "+student.getName());
        ((TextView)findViewById(R.id.add_st_roll)).setText("Roll - "+student.getRoll());
        ((TextView)findViewById(R.id.add_st_std)).setText("Class - "+student.getStd());
        ((TextView)findViewById(R.id.add_st_gender)).setText("Gender - "+student.getGender());
        TableLayout tl = (TableLayout)findViewById(R.id.add_st_table);
        TableRow.LayoutParams nameLp = new TableRow.LayoutParams();
        nameLp.gravity = Gravity.LEFT;
        TableRow.LayoutParams dateLp = new TableRow.LayoutParams();
        nameLp.gravity = Gravity.RIGHT;

        try {
            JSONArray skillIds = new JSONArray(student.getSkills());
            if (skillIds.length() > 0) {
                int[] ids = new int[skillIds.length()];
                for (int i = 0; i < skillIds.length(); i++) {
                    try {
                        ids[i] = skillIds.getJSONObject(i).getInt(SkillDatabaseHandler.KEY_LOCAL_ID);
                        Skill skill = skillDatabaseHandler.getSkill(ids[i]);

                        String date = skillIds.getJSONObject(i).getString(SkillDatabaseHandler.KEY_CREATION_DATE);
                        if(date==null || date.equals(""))
                            continue;

                        TextView tvName = new TextView(this);
                        tvName.setText(skill.getName());
                        tvName.setLayoutParams(nameLp);
                        TextView tvDate = new TextView(this);
                        tvDate.setText(date+"  -  ");
                        tvDate.setLayoutParams(dateLp);



                        TableRow tr = new TableRow(this);
                        tr.setOrientation(LinearLayout.HORIZONTAL);

                        tr.addView(tvDate);
                        tr.addView(tvName);

                        tl.addView(tr);

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
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
