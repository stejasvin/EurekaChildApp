package stejasvin.eaindia.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import stejasvin.eaindia.Objects.SkillChart;

public class SkillChartDatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "SkillChartManager";

	private static final String TABLE_SKILL_CHART = "SkillChart";

	public static final String KEY_LOCAL_ID = "l_id";
	public static final String KEY_CENTER_NAME = "centre";
    public static final String KEY_TUTOR = "tutor";
    public static final String KEY_STUDENTS = "students";
    public static final String KEY_CREATION_DATE = "l_creation_date";

    private static final String TAG = "SkillDatabaseHandler";

    public SkillChartDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_SKILL_TABLE = "CREATE TABLE " + TABLE_SKILL_CHART + "("
				+ KEY_LOCAL_ID + " INTEGER PRIMARY KEY,"
                + KEY_CENTER_NAME + " TEXT,"
				+ KEY_TUTOR + " TEXT,"
				+ KEY_STUDENTS + " TEXT,"
                + KEY_CREATION_DATE + " TEXT" +")";
		db.execSQL(CREATE_SKILL_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKILL_CHART);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new annotation
	public void addSkillChart(SkillChart skillChart) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		 // Annotation primary key

        values.put(KEY_CENTER_NAME, skillChart.getCentreName());
        values.put(KEY_TUTOR, skillChart.getTutorName());
        values.put(KEY_STUDENTS, skillChart.getStudents());
        values.put(KEY_CREATION_DATE, skillChart.getDateOfCreation());

		// Inserting Row
		if(db.insert(TABLE_SKILL_CHART, null, values)==-1)
            Log.e(TAG, "error in inserting");
		db.close(); // Closing database connection
	}

	public SkillChart getSkillChart(int l_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_SKILL_CHART, new String[] {
                KEY_LOCAL_ID,
				KEY_CENTER_NAME,
                KEY_TUTOR,
                KEY_STUDENTS,
                KEY_CREATION_DATE}, KEY_LOCAL_ID + "=?",
				new String[] { String.valueOf(l_id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		SkillChart skillChart = new SkillChart();
        skillChart.setLid(cursor.getInt(0));
        skillChart.setCentreName(cursor.getString(1));
        skillChart.setTutorName(cursor.getString(2));
        skillChart.setStudents(cursor.getString(3));
        skillChart.setDateOfCreation(cursor.getString(4));

        db.close();
		return skillChart;
	}

    public ArrayList<SkillChart> getAllSkillCharts() {
        ArrayList<SkillChart> skillChartList = new ArrayList<SkillChart>();
        String selectQuery = "SELECT  * FROM " + TABLE_SKILL_CHART;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SkillChart skillChart = new SkillChart();
                skillChart.setLid(cursor.getInt(0));
                skillChart.setCentreName(cursor.getString(1));
                skillChart.setTutorName(cursor.getString(2));
                skillChart.setStudents(cursor.getString(3));
                skillChart.setDateOfCreation(cursor.getString(4));

                skillChartList.add(skillChart);
            } while (cursor.moveToNext());
        }
        db.close();
        // return student list
        return skillChartList;
    }

//	// Getting All Annotations
//	public List<Annotation> getAllAnnotations() {
//		List<Annotation> annotationList = new ArrayList<Annotation>();
//		// Select All Query
//		String selectQuery = "SELECT  * FROM " + TABLE_ANNOTATIONS;
//
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//                Annotation annotation = new Annotation();
//                annotation.setLocalId(cursor.getString(0));
//                annotation.setServerId(cursor.getString(1));
//                annotation.setLocalEcgId(cursor.getString(2));
//                annotation.setNote(cursor.getString(3));
//                annotation.setLocalCreationDate(cursor.getString(4));
//
//                // Adding annotation to list
//			    annotationList.add(annotation);
//			} while (cursor.moveToNext());
//		}
//        db.close();
//
//        // return annotation list
//		return annotationList;
//	}
//
//    // Getting All Annotations
//    public ArrayList<HashMap<String,String>> getAllAnnotationsArrayList(String ecgId) {
//        ArrayList<HashMap<String,String>> annotationList = new ArrayList<HashMap<String,String>>();
//        // Select All Query
////        String selectQuery = "SELECT  * FROM " + TABLE_ANNOTATIONS + "WHERE "+ KEY_ECG_ID +"= "+ s_id;
//
//        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor cursor = db.rawQuery(selectQuery, null);
//        Cursor cursor = db.query(TABLE_ANNOTATIONS, new String[] {
//                KEY_LOCAL_ID,
//                KEY_SERVER_ID,
//                KEY_LOCAL_ECG_ID,
//                KEY_NOTE,
//                KEY_CREATION_DATE}, KEY_LOCAL_ECG_ID + "=?",
//                new String[] { ecgId }, null, null, null, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                HashMap<String,String> hashMap = new HashMap<String, String>();
//                hashMap.put(KEY_LOCAL_ID,cursor.getString(0));
//                hashMap.put(KEY_SERVER_ID, cursor.getString(1));
//                hashMap.put(KEY_LOCAL_ECG_ID,cursor.getString(2));
//                hashMap.put(KEY_NOTE, cursor.getString(3));
//                hashMap.put(KEY_CREATION_DATE, cursor.getString(4));
//                // Adding annotation to list
//                annotationList.add(hashMap);
//            } while (cursor.moveToNext());
//        }
//        db.close();
//        // return annotation list
//        return annotationList;
//    }

//	//Updating single annotation
//	public int updateAnnotation(Annotation annotation) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(KEY_NAME, annotation.getName());
//		values.put(KEY_PH_NO, annotation.getPhoneNumber());
//
//		// updating row
//		return db.update(TABLE_ANNOTATIONS, values, KEY_ID + " = ?",
//				new String[] { String.valueOf(annotation.getID()) });
//	}
//
//	// Deleting single annotation
//	public void deleteAnnotation(Annotation annotation) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(TABLE_ANNOTATIONS, KEY_ID + " = ?",
//				new String[] { String.valueOf(annotation.getID()) });
//		db.close();
//	}


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
