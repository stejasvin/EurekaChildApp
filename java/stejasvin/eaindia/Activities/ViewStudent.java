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

import stejasvin.eaindia.Adapters.StudentListAdapter;
import stejasvin.eaindia.Objects.Student;
import stejasvin.eaindia.R;
import stejasvin.eaindia.databases.StudentDatabaseHandler;

/**
 * Created by stejasvin on 7/12/2014.
 */
public class ViewStudent extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final StudentDatabaseHandler studentDatabaseHandler = new StudentDatabaseHandler(this);
        final List<Student> studentList = studentDatabaseHandler.getAllStudents();
        if(studentList.size()>0){
            final StudentListAdapter studentListAdapter = new StudentListAdapter(ViewStudent.this,studentList);
            setListAdapter(studentListAdapter);
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

            getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    if(position==0)
                        return false;

                    AlertDialog.Builder alert1 = new AlertDialog.Builder(ViewStudent.this);
                    alert1.setTitle("Options"); // set title for the alert
                    alert1.setMessage("Are you sure you want to remove this student?"); // this sets the message in the alert dialog
                    alert1.setIcon(R.drawable.ic_launcher); // this sets icon for the dialog
                    alert1.setPositiveButton("Remove", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            studentDatabaseHandler.deleteStudent(studentList.get(position - 1));
                            studentList.remove(position-1);
                            studentListAdapter.notifyDataSetChanged();

                        }
                    });

                    alert1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    alert1.setCancelable(true);
                    alert1.show();
                    return true;
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
