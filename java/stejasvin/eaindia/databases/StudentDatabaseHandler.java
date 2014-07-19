package stejasvin.eaindia.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import stejasvin.eaindia.Objects.Student;

public class StudentDatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "StudentManager";

	private static final String TABLE_STUDENT = "Student";

	public static final String KEY_LOCAL_ID = "l_id";
    public static final String KEY_ROLL = "roll";
	public static final String KEY_NAME = "name";
    public static final String KEY_STD = "std";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_SKILLSET = "skillset";
    public static final String KEY_CREATION_DATE = "l_creation_date";

    private static final String TAG = "StudentDatabaseHandler";

    public StudentDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + "("
				+ KEY_LOCAL_ID + " INTEGER PRIMARY KEY,"
                + KEY_ROLL + " TEXT,"
                + KEY_NAME + " TEXT,"
				+ KEY_STD + " TEXT,"
                + KEY_GENDER + " TEXT,"
                + KEY_SKILLSET + " TEXT,"
                + KEY_CREATION_DATE + " TEXT" +")";
		db.execSQL(CREATE_STUDENT_TABLE);
	}
	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);

		// Create tables again
		onCreate(db);
	}
	public void addStudent(Student student) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		 // Annotation primary key

        values.put(KEY_ROLL, student.getRoll());
        values.put(KEY_NAME, student.getName());
        values.put(KEY_STD, student.getStd());
        values.put(KEY_GENDER, student.getGender());
        values.put(KEY_SKILLSET, student.getSkills());
        values.put(KEY_CREATION_DATE, student.getDateOfCreation());

		// Inserting Row
		if(db.insert(TABLE_STUDENT, null, values)==-1)
            Log.e(TAG, "error in inserting");
		db.close(); // Closing database connection
	}

	public Student getStudent(int l_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_STUDENT, new String[] {
                KEY_LOCAL_ID,
				KEY_ROLL,
                KEY_NAME,
                KEY_STD,
                KEY_GENDER,
                KEY_SKILLSET,
                KEY_CREATION_DATE}, KEY_LOCAL_ID + "=?",
				new String[] { String.valueOf(l_id) }, null, null, null, null);

		if (cursor != null) {
            Log.i("TestCursor",l_id+"");
            cursor.moveToFirst();
            Student student = new Student();
            student.setLid(cursor.getInt(0));
            student.setRoll(cursor.getString(1));
            student.setName(cursor.getString(2));
            student.setStd(cursor.getString(3));
            student.setGender(cursor.getString(4));
            student.setSkills(cursor.getString(5));
            student.setDateOfCreation(cursor.getString(6));
            return student;
        }

        db.close();
		return null;
	}
	
	// Getting All Annotations
	public ArrayList<Student> getAllStudents() {
		ArrayList<Student> studentList = new ArrayList<Student>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_STUDENT;


		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
                Student student = new Student();
                student.setLid(cursor.getInt(0));
                student.setRoll(cursor.getString(1));
                student.setName(cursor.getString(2));
                student.setStd(cursor.getString(3));
                student.setGender(cursor.getString(4));
                student.setSkills(cursor.getString(5));
                student.setDateOfCreation(cursor.getString(6));

                // Adding student to list
			    studentList.add(student);
			} while (cursor.moveToNext());
		}
        db.close();

        // return student list
		return studentList;
	}

    public HashMap<String,Student> getAllStudentsMap() {
        HashMap<String,Student> map = new HashMap<String,Student>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENT, new String[] {
                        KEY_LOCAL_ID,
                        KEY_ROLL,
                        KEY_NAME,
                        KEY_STD,
                        KEY_GENDER,
                        KEY_SKILLSET,
                        KEY_CREATION_DATE}, null,
                new String[] {}, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setLid(cursor.getInt(0));
                student.setRoll(cursor.getString(1));
                student.setName(cursor.getString(2));
                student.setStd(cursor.getString(3));
                student.setGender(cursor.getString(4));
                student.setSkills(cursor.getString(5));
                student.setDateOfCreation(cursor.getString(6));

                map.put(student.getName(),student);
            } while (cursor.moveToNext());
        }
        db.close();
        // return annotation list
        return map;
    }



	//Updating single annotation
	public int updateStudent(Student student) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
        values.put(KEY_ROLL, student.getRoll());
        values.put(KEY_NAME, student.getName());
        values.put(KEY_STD, student.getStd());
        values.put(KEY_GENDER, student.getGender());
        values.put(KEY_SKILLSET, student.getSkills());
        values.put(KEY_CREATION_DATE, student.getDateOfCreation());
		// updating row
		return db.update(TABLE_STUDENT, values, KEY_LOCAL_ID + " = ?",
				new String[] { String.valueOf(student.getLid()) });
	}
//
//	// Deleting single annotation
	public void deleteStudent(Student student) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_STUDENT, KEY_LOCAL_ID + " = ?",
				new String[] { student.getLid()+"" });
		db.close();
	}


//	// Getting annotations Count
//	public int getAnnotationsCount() {
//		String countQuery = "SELECT  * FROM " + TABLE_ANNOTATIONS;
//		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor cursor = db.rawQuery(countQuery, null);
//		cursor.close();
//
//		// return count
//		return cursor.getCount();
//	}

}
