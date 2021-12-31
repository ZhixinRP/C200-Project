package org.tensorflow.lite.examples.classification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "workout_tracker.db";
    private static final int DATABASE_VERSION = 1;
    // first table
    private static final String TABLE_EXERCISE = "exercise";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    // second table
    private static final String TABLE_WORKOUT = "workout";
    // for id, just take same variable as first table
    private static final String COLUMN_EXID = "exercise_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_METRIC1 = "metric1";
    private static final String COLUMN_METRIC2 = "metric2";
    private static final String COLUMN_METRIC3 = "metric3";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating sql table in database
        String createExerciseTableSql = "CREATE TABLE " + TABLE_EXERCISE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT ) ";
        db.execSQL(createExerciseTableSql);
        Log.i("info", "created first table");

        String createWorkoutTableSql = "CREATE TABLE " + TABLE_WORKOUT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_METRIC1 + " INTEGER,"
                + COLUMN_METRIC2 + " INTEGER,"
                + COLUMN_METRIC3 + " INTEGER,"
                + COLUMN_EXID + " INTEGER, FOREIGN KEY(" + COLUMN_EXID + ") REFERENCES " + TABLE_EXERCISE + "(" + COLUMN_ID + ") )";
        db.execSQL(createWorkoutTableSql);
        Log.i("info", "created first table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
        onCreate(db);
    }

    // Insert
    public long insertExercise(Exercise data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // values.put(COLUMN_ID, data.getId());
        values.put(COLUMN_NAME, data.getName());
        long result = db.insert(TABLE_EXERCISE, null, values); // result returns the id of the new row
        db.close();
        Log.d("SQL Insert Module","ID: "+ result); //id returned, shouldn’t be -1
//        int resultId = (int) result;
//        data.setId(resultId);
        return result;
    }

    public long insertWorkout(Workout data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // values.put(COLUMN_ID, data.getId());
        values.put(COLUMN_EXID, data.getExerciseId());
        values.put(COLUMN_DATE, data.getDate());
        values.put(COLUMN_METRIC1, data.getMetric1());
        values.put(COLUMN_METRIC2, data.getMetric2());
        values.put(COLUMN_METRIC3, data.getMetric3());
        long result = db.insert(TABLE_WORKOUT, null, values); // result returns the id of the new row
        db.close();
        Log.d("SQL Insert Module","ID: "+ result); //id returned, shouldn’t be -1
//        int resultId = (int) result;
//        data.setId(resultId);
        return result;
    }

    public ArrayList<Exercise> getAllExercise() {
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();

        String selectQuery = "SELECT * FROM " + TABLE_EXERCISE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                Exercise exercise = new Exercise(id, name);
                exercises.add(exercise);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return exercises;
    }

    public ArrayList<Workout> getAllWorkout() {
        ArrayList<Exercise> exercises = getAllExercise();
        ArrayList<Workout> workouts = new ArrayList<Workout>();

        String selectQuery = "SELECT * FROM " + TABLE_WORKOUT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int workoutId = cursor.getInt(0);
                String date = cursor.getString(1);
                int metric1 = cursor.getInt(2);
                int metric2 = cursor.getInt(3);
                int metric3 = cursor.getInt(4);
                int exerciseId = cursor.getInt(5);
                String name = "";
                for(int i = 0; i < exercises.size(); i++) {
                    if(exerciseId == exercises.get(i).getExerciseId()) {
                        name = exercises.get(i).getName();
                        break;
                    }
                }
                Workout workout = new Workout(exerciseId, name, workoutId, date, metric1, metric2, metric3);
                workouts.add(workout);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return workouts;
    }

//    public int updateModule(Module data){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        //values.put(COLUMN_ID, data.getId());
//        values.put(COLUMN_CODE, data.getCode());
//        values.put(COLUMN_NAME, data.getName());
//        values.put(COLUMN_BREAKDOWN, data.getBreakdown());
//        String condition = COLUMN_ID + "= ?";
//        String[] args = {String.valueOf(data.getModuleId())};
//        int result = db.update(TABLE_MODULE, values, condition, args); // result returns the number of rows updated
//        if (result < 1) {
//            Log.d("DBHelper", "Update failed");
//        }
//        db.close();
//        return result;
//    }
//
//    public int updateCAG(CAG data){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        //values.put(COLUMN_ID, data.getId());
//        values.put(COLUMN_AKS, data.getAks());
//        values.put(COLUMN_SDL, data.getSdl());
//        values.put(COLUMN_COL, data.getCol());
//        values.put(COLUMN_MODID, data.getModuleId());
//        String condition = COLUMN_ID + "= ?";
//        String[] args = {String.valueOf(data.getModuleId())};
//        int result = db.update(TABLE_CAG, values, condition, args); // result returns the number of rows updated
//        if (result < 1) {
//            Log.d("DBHelper", "Update failed");
//        }
//        db.close();
//        return result;
//    }
//
//    public int[] deleteModule(int id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        // delete child
//        String childCondition = COLUMN_MODID + "= ?";
//        String[] args = {String.valueOf(id)};
//        int childResult = db.delete(TABLE_CAG, childCondition, args);
//        if (childResult < 1) {
//            Log.d("DBHelper", "Delete child failed");
//        }
//        // delete parent
//        String parentCondition = COLUMN_ID + "= ?";
//        int parentResult = db.delete(TABLE_MODULE, parentCondition, args);
//        if (parentResult < 1) {
//            Log.d("DBHelper", "Delete parent failed");
//        }
//        int[] result = {childResult, parentResult};
//        db.close();
//        return result;
//    }
//
//    public int deleteCAG(int id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String childCondition = COLUMN_ID + "= ?";
//        String[] args = {String.valueOf(id)};
//        int result = db.delete(TABLE_CAG, childCondition, args);
//        if (result < 1) {
//            Log.d("DBHelper", "Delete failed");
//        }
//        db.close();
//        return result;
//    }

}
