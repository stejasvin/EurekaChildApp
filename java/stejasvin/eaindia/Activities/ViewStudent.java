package stejasvin.eaindia.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

import stejasvin.eaindia.Adapters.StudentListAdapter;
import stejasvin.eaindia.Objects.Student;
import stejasvin.eaindia.databases.StudentDatabaseHandler;

/**
 * Created by stejasvin on 7/12/2014.
 */
public class ViewStudent extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StudentDatabaseHandler studentDatabaseHandler = new StudentDatabaseHandler(this);
        final List<Student> studentList = studentDatabaseHandler.getAllStudents();
        if(studentList.size()>0){
            setListAdapter(new StudentListAdapter(ViewStudent.this,studentList));
            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position==0)
                        return;
                    Intent i = new Intent(ViewStudent.this,ViewStudentDetail.class);
                    i.putExtra("stejasvin.eaindia.LID",studentList.get(position-1).getLid());
                    startActivity(i);
                }
            });
            getListView().setHeaderDividersEnabled(true);
        }
        else{
            finish();
            Toast.makeText(this, "No students added", Toast.LENGTH_SHORT).show();
        }

    }

}
