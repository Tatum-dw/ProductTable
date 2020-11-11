package com.example.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DatabaseName = "product.db";
    public static final String tableName = "Product Table";
    public static final String column1 = "ID";
    public static final String column2 = "Name";
    public static final String column3 = "Price";
    public static final String column4 = "Availability";

    public DatabaseHelper(@Nullable Context context) {

        super(context, DatabaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table" + tableName + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Price INTEGER, Availability TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String ID, String Name, String Price, String Availability){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column1, ID);
        contentValues.put(column2, Name);
        contentValues.put(column3, Price);
        contentValues.put(column4, Availability);
        long result = sqLiteDatabase.insert(tableName, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from " + tableName, null);
        return res;
    }

    public boolean updateData(String ID, String Name, String Price, String Availability){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column1, ID);
        contentValues.put(column2, Name);
        contentValues.put(column3, Price);
        contentValues.put(column4, Availability);
        sqLiteDatabase.update(tableName, contentValues, "ID = ?", new String[] { ID });
        return true;
    }

    public Integer deleteData(String ID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(tableName, "ID = ?", new String[] {ID});
    }
}
