package stejasvin.eaindia.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import stejasvin.eaindia.Objects.Student;
import stejasvin.eaindia.R;
import stejasvin.eaindia.Utils.Constants;
import stejasvin.eaindia.Utils.Utilities;
import stejasvin.eaindia.databases.StudentDatabaseHandler;

public class ExportImport extends ActionBarActivity {

    private static final int REQUEST_IMP = 1;
    private static final int REQUEST_EX = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_import);

        Button bExStudent = (Button) findViewById(R.id.ex_students);
        Button bImStudent = (Button) findViewById(R.id.im_students);

        final StudentDatabaseHandler studentDatabaseHandler = new StudentDatabaseHandler(ExportImport.this);
        bExStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExportImport.this, ActivityImport.class);
                intent.putExtra("stejasvin.CHOICE", 1);
                startActivityForResult(intent, REQUEST_EX);
            }
        });

        bImStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExportImport.this, ActivityImport.class);
                intent.putExtra("stejasvin.CHOICE", 2);
                startActivityForResult(intent, REQUEST_IMP);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMP) {
                String filePath = data.getStringExtra(ActivityImport.returnFileParameter);
                boolean suc = false;
                if (filePath != null && !filePath.equals("")) {
                    suc = importFromFile(ExportImport.this, filePath);
                }
                if (!suc) {
                    Toast.makeText(ExportImport.this, "Error in importing ,invalid file", Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == REQUEST_EX) {
                String filePath = data.getStringExtra(ActivityImport.returnDirectoryParameter);
                boolean suc = false;
                if (filePath != null && !filePath.equals("")) {
                    StudentDatabaseHandler studentDatabaseHandler = new StudentDatabaseHandler(ExportImport.this);
                    suc = true;
                    saveExcelFile(ExportImport.this, filePath, "studentList" + Utilities.getDate(Utilities.getCurrentTime()).replace("/", "_") + ".xls", studentDatabaseHandler.getAllStudents());
                }
                if (!suc) {
                    Toast.makeText(ExportImport.this, "Error in exporting ,invalid dir", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private static boolean saveExcelFile(Context context, String dir, String fileName, ArrayList<Student> list) {

//        // check if available and not read only
//        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
//            Log.e(TAG, "Storage not available or read only");
//            return false;
//        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("StudentsList " + Utilities.getDate(Utilities.getCurrentTime()).replace("/", "_"));

        // Generate column headings
        Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue(Constants.NAME);
        c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue(Constants.ROLL);
        c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue(Constants.GENDER);
        c.setCellStyle(cs);

        c = row.createCell(3);
        c.setCellValue(Constants.CLASS);
        c.setCellStyle(cs);

        int rowCounter = 1, cellCounter = 0;

        for (Student s : list) {
            cellCounter = 0;
            row = sheet1.createRow(rowCounter);
            c = row.createCell(cellCounter++);
            c.setCellValue(s.getName());
            c = row.createCell(cellCounter++);
            c.setCellValue(s.getRoll());
            c = row.createCell(cellCounter++);
            c.setCellValue(s.getGender());
            c = row.createCell(cellCounter++);
            c.setCellValue(s.getStd());
            rowCounter++;
        }

//        sheet1.setColumnWidth(0, (15 * 500));
//        sheet1.setColumnWidth(1, (15 * 500));
//        sheet1.setColumnWidth(2, (15 * 500));

        // Create a path where we will place our List of objects on external storage
        File file = new File(dir, fileName);
        file.delete();
        //File file1 = new File("/storage/sdcard1", "test.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
            Toast.makeText(context, "Data successfully exported to " + file, Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            Log.e("TestPOI", Environment.getExternalStorageState());
            Log.w("FileUtils", "Error writing " + file, e);
            Toast.makeText(context, "Error1", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
            Toast.makeText(context, "Error2", Toast.LENGTH_LONG).show();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return success;
    }

    private static boolean importFromFile(Context context, String filePath) {
        try {
            final StudentDatabaseHandler studentDatabaseHandler = new StudentDatabaseHandler(context);
            // Creating Input Stream
            //File file = new File("/storage/sdcard1", "importstudent.xls");
            File file = new File(filePath);
            if (!file.exists()) {
                Toast.makeText(context, "Import file not found, please follow the above instructions", Toast.LENGTH_LONG).show();
                return false;

            }
            Log.e("TestPOI", Environment.getExternalStorageState());
            FileInputStream myInput = new FileInputStream(file);

            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            /** We now need something to iterate through the cells.**/
            Iterator rowIter = mySheet.rowIterator();

            ArrayList<String> list = new ArrayList<String>();
            //ArrayList<HashMap<String,String>> mapList = new ArrayList<HashMap<String,String>>();
            boolean firstrow = true;
            int counter = 0;
            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                HashMap<String, String> map = new HashMap<String, String>();
                Iterator cellIter = myRow.cellIterator();
                counter = 0;
                if (myRow.getCell(0).toString().equals(""))
                    continue;
                while (cellIter.hasNext()) {

                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    if (firstrow) {
                        list.add(myCell.toString().toLowerCase());
                    } else {
                        map.put(list.get(counter), myCell.toString());
                    }
                    Log.e("TestPOI", "Cell Value: " + myCell.toString());
//                            Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
                    counter++;
                }

                Student student = new Student();
                student.setName(map.get(Constants.NAME));
                student.setRoll(map.get(Constants.ROLL));
                student.setGender(map.get(Constants.GENDER));
                student.setStd(map.get(Constants.CLASS));
                student.setDateOfCreation(Utilities.getDate(Utilities.getCurrentTime()));

                if (student.getName() != null && !student.getName().equals("")) {
                    studentDatabaseHandler.addStudent(student);
                }

                firstrow = false;
            }
            myInput.close();
            Toast.makeText(context, "Data successfully imported", Toast.LENGTH_LONG).show();
            return true;

//                    for(HashMap<String,String> m:mapList){
//
//                    }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("POI", e.getMessage());
            return false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.export_import, menu);
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
