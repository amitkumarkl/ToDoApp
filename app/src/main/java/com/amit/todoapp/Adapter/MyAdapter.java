package com.amit.todoapp.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyAdapter {

    //DB name

    public static final String DATABASE_NAME="todo";

    //Table name
    public static final String TABLE_NAME="Application";

    //Version name
    public static final int DB_VERSION = 1;

    //Table columns name

   public static final String COL_ROW = "serialNO";
   public static final String COL_TITLE = "title";
   public static final String COL_DESCRIPTION = "description";
   public static final String COL_DATEPICKER = "datepicker";

//    "CAREATE TABLE trainee(col1 dataType, col1 dataType, col1 dataType, col1 dataType, col1 dataType)"

     String createDB = "CREATE TABLE "+TABLE_NAME+" ("+COL_ROW+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_TITLE+" text, "+COL_DESCRIPTION+" text, "+COL_DATEPICKER+" text"+")";


        private SQLiteDatabase sqLiteDatabase;
        private MyDatabase myDatabase;
        public MyAdapter(Context context){
            myDatabase = new MyDatabase(context);
        }

        public  MyAdapter openDatabase() {
            sqLiteDatabase = myDatabase.getWritableDatabase();
            return this;
        }

    // TODO : method for insertion records into DB

    public  void insertRecord(Context context, String title,String description,String datepicker){

        ContentValues values = new ContentValues();
        values.put(COL_TITLE, title);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_DATEPICKER, datepicker);

        long insertionValue = sqLiteDatabase.insert(TABLE_NAME,null,values);

        if (insertionValue == -1)
        {
            Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Insertion successfully", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO : method for retrive records from DB

    public Cursor getAllRecords(String title){
        String[] COLUMNS = {COL_ROW, COL_TITLE, COL_DESCRIPTION, COL_DATEPICKER};
        return sqLiteDatabase.query(TABLE_NAME, COLUMNS, COL_TITLE+" = "+"'"+title+"'", null, null, null, null);
    }

    public Cursor getAllRecords(){
        String[] COLUMNS = {COL_ROW, COL_TITLE, COL_DESCRIPTION, COL_DATEPICKER};
        return sqLiteDatabase.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
    }

    // TODO : delete single record

    public void deleteRecord(String colRow, Context context){
        int id = sqLiteDatabase.delete(TABLE_NAME, COL_ROW+" = "+colRow, null);
        if (id > 0){
            Toast.makeText(context, "record deleted.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }

    // TODO : Delete all records
    public void deleteAllRecords(Context context){
        int id = sqLiteDatabase.delete(TABLE_NAME, null, null);
        if (id > 0){
            Toast.makeText(context, "record deleted.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO : UPDATE records
    public void updateRecord(Context context, String rowId, String title, String description, String datepicker){

        ContentValues values = new ContentValues();
        values.put(COL_TITLE, title);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_DATEPICKER, datepicker);

        int updateRecord = sqLiteDatabase.update(TABLE_NAME, values, COL_ROW+" = "+rowId, null);
        if (updateRecord == -1){
            Toast.makeText(context, "Updation failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "updated Successfully.", Toast.LENGTH_SHORT).show();
        }
    }


public class MyDatabase extends SQLiteOpenHelper {
    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createDB);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


}
