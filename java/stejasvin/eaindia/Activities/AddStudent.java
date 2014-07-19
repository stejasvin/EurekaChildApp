package stejasvin.eaindia.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import stejasvin.eaindia.Objects.Student;
import stejasvin.eaindia.R;
import stejasvin.eaindia.Utils.Utilities;
import stejasvin.eaindia.databases.StudentDatabaseHandler;


public class AddStudent extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);
        final StudentDatabaseHandler studentDatabaseHandler = new StudentDatabaseHandler(AddStudent.this);

        Button bCreateStudent = (Button)findViewById(R.id.b_create_student);
        bCreateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etName = (EditText)findViewById(R.id.et_name);
                EditText etRoll = (EditText)findViewById(R.id.et_roll);
                EditText etStd = (EditText)findViewById(R.id.et_std);
                RadioGroup rg = (RadioGroup)findViewById(R.id.rg_add_st_gender);
                RadioButton rb1 = (RadioButton)findViewById(R.id.rb1_add_st_gender);
                RadioButton rb2 = (RadioButton)findViewById(R.id.rb2_add_st_gender);

                if(etName.getText() ==null || etName.getText().toString()==""){
                    Toast.makeText(AddStudent.this,"Enter student name",Toast.LENGTH_LONG).show();
                    return;
                }

                Student student = new Student();
                student.setName(etName.getText().toString());
                student.setRoll(etRoll.getText().toString());
                student.setStd(etStd.getText().toString());
                student.setDateOfCreation(Utilities.getDate(Utilities.getCurrentTime()));
                if(rg.getCheckedRadioButtonId() == R.id.rb1_add_st_gender)
                    student.setGender("m");
                else if(rg.getCheckedRadioButtonId() == R.id.rb2_add_st_gender)
                    student.setGender("f");
                student.setSkills("");

                studentDatabaseHandler.addStudent(student);
                finish();
            }
        });

//        Button bImport  = (Button)findViewById(R.id.b_import_std);
//        bImport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
//
//        Button bExport  = (Button)findViewById(R.id.b_export_std);
//        bExport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_student, menu);
        return true;
    }


}
