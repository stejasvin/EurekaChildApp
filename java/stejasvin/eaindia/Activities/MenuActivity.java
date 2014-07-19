package stejasvin.eaindia.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import stejasvin.eaindia.R;

public class MenuActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Button bAddStudent = (Button)findViewById(R.id.add_student);
        Button bAddSkill = (Button)findViewById(R.id.add_skill);
        Button bAddSc = (Button)findViewById(R.id.add_sc);
        Button bLoadSc = (Button)findViewById(R.id.load_sc);
        Button bViewStudent = (Button)findViewById(R.id.view_student);
        Button bExIm = (Button)findViewById(R.id.ex_import);

        bExIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this,ExportImport.class);
                startActivity(i);
            }
        });

        bAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this,AddStudent.class);
                startActivity(i);
            }
        });

        bAddSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this,AddSkill.class);
                startActivity(i);
            }
        });

        bAddSc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this,AddSkillChart.class);
                startActivity(i);
            }
        });


        bLoadSc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this,ViewSkillChartList.class);
                startActivity(i);
            }
        });

        bViewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this,ViewStudent.class);
                startActivity(i);
            }
        });

    }
}
