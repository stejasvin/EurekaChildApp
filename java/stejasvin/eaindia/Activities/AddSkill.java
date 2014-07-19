package stejasvin.eaindia.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import stejasvin.eaindia.Adapters.SkillListAdapter;
import stejasvin.eaindia.Objects.Skill;
import stejasvin.eaindia.R;
import stejasvin.eaindia.Utils.Utilities;
import stejasvin.eaindia.databases.SkillDatabaseHandler;

public class AddSkill extends ActionBarActivity {

    ArrayList<Skill> skillList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_skill);

        final SkillDatabaseHandler skillDatabaseHandler = new SkillDatabaseHandler(AddSkill.this);
        skillList = skillDatabaseHandler.getAllSkill();

        ListView listView = (ListView)findViewById(R.id.add_skill_list);
        final SkillListAdapter skillListAdapter = new SkillListAdapter(this,skillList);
        listView.setAdapter(skillListAdapter);

        Button bCreateSkill = (Button)findViewById(R.id.add_skill_add);
        bCreateSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etName = (EditText)findViewById(R.id.add_skill_name);
                EditText etSubject = (EditText)findViewById(R.id.add_skill_subject);

                Skill skill = new Skill();
                skill.setName(etName.getText().toString());
                skill.setSubject(etSubject.getText().toString());
                skill.setDateOfCreation(Utilities.getDate(Utilities.getCurrentTime()));
                skillDatabaseHandler.addSkill(skill);

                //updating list
                skillList.add(skill);
                etName.setText("");
                etSubject.setText("");
                skillListAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_student, menu);
        return true;
    }

}
