package stejasvin.eaindia.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import stejasvin.eaindia.Objects.Skill;

public class SkillDatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "SkillManager";

	private static final String TABLE_SKILL = "Skill";

	public static final String KEY_LOCAL_ID = "l_id";
	public static final String KEY_NAME = "name";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_CREATION_DATE = "l_creation_date";

    private static final String TAG = "SkillDatabaseHandler";

    public SkillDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_SKILL_TABLE = "CREATE TABLE " + TABLE_SKILL + "("
				+ KEY_LOCAL_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
				+ KEY_SUBJECT + " TEXT,"
                + KEY_CREATION_DATE + " TEXT" +")";
		db.execSQL(CREATE_SKILL_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKILL);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new annotation
	public void addSkill(Skill skill) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		 // Annotation primary key

        values.put(KEY_NAME, skill.getName());
        values.put(KEY_SUBJECT, skill.getSubject());
        values.put(KEY_CREATION_DATE, skill.getDateOfCreation());

		// Inserting Row
		if(db.insert(TABLE_SKILL, null, values)==-1)
            Log.e(TAG, "error in inserting");
		db.close(); // Closing database connection
	}

	public Skill getSkill(int l_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_SKILL, new String[] {
                KEY_LOCAL_ID,
				KEY_NAME,
                KEY_SUBJECT,
                KEY_CREATION_DATE}, KEY_LOCAL_ID + "=?",
				new String[] { String.valueOf(l_id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Skill skill = new Skill();
        skill.setLid(cursor.getInt(0));
        skill.setName(cursor.getString(1));
        skill.setSubject(cursor.getString(2));
        skill.setDateOfCreation(cursor.getString(3));

        db.close();
		return skill;
	}
	
    public ArrayList<Skill> getAllSkill() {
        ArrayList<Skill> skillList = new ArrayList<Skill>();
        String selectQuery = "SELECT  * FROM " + TABLE_SKILL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Skill skill = new Skill();
                skill.setLid(cursor.getInt(0));
                skill.setName(cursor.getString(1));
                skill.setSubject(cursor.getString(2));
                skill.setDateOfCreation(cursor.getString(3));
                skillList.add(skill);
            } while (cursor.moveToNext());
        }
        db.close();
        // return student list
        return skillList;
    }

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
