package com.example.lab4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MuseumsDBManager {
    private Context context;
    private MuseumsDBHelper DBHelper;
    private SQLiteDatabase db;

    public MuseumsDBManager(Context context){
        this.context=context;
        DBHelper=new MuseumsDBHelper(context);
    }

    public void StartDB(){
        db=DBHelper.getReadableDatabase();
    }

    public void insert(String museum,String city,int year,String phone,int exhibits,int priv){
        ContentValues values = new ContentValues();
        values.put(DBConstants.FeedEntry.COLUMN_MUSEUM, museum);
        values.put(DBConstants.FeedEntry.COLUMN_CITY, city);
        values.put(DBConstants.FeedEntry.COLUMN_YEAR, year);
        values.put(DBConstants.FeedEntry.COLUMN_PHONE, phone);
        values.put(DBConstants.FeedEntry.COLUMN_EXHIBITS, exhibits);
        values.put(DBConstants.FeedEntry.COLUMN_PRIVATE, priv);
        db.insert(DBConstants.FeedEntry.TABLE_NAME, null, values);
    }

    public void delete(String id){
        db.execSQL(DBConstants.SQL_DELETE_RECORD+id);
    }

    public void update(String[] array){
        String query= "UPDATE "+DBConstants.FeedEntry.TABLE_NAME+" SET "+
                DBConstants.FeedEntry.COLUMN_MUSEUM+ " = '"+ array[0]+ "'," +
                DBConstants.FeedEntry.COLUMN_CITY+ " = '"+ array[1]+ "'," +
                DBConstants.FeedEntry.COLUMN_YEAR+ " = "+ array[2]+ "," +
                DBConstants.FeedEntry.COLUMN_PHONE+ " = '"+ array[3]+ "',"+
                DBConstants.FeedEntry.COLUMN_EXHIBITS+ " = "+ array[4]+ ","+
                DBConstants.FeedEntry.COLUMN_PRIVATE+ " = "+ array[5]+ " WHERE _id="+ array[6];
        db.execSQL(query.toString());
    }

    public ArrayList<String> selectAll(){
        ArrayList<String> tempList = new ArrayList<>();
        Cursor cursor = db.query(DBConstants.FeedEntry.TABLE_NAME,null, null,
                null, null,null,null);
        while (cursor.moveToNext()){
            String id=cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.FeedEntry._ID));
            tempList.add(id);
            String museum= cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.FeedEntry.COLUMN_MUSEUM));
            tempList.add(museum);
            String city= cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.FeedEntry.COLUMN_CITY));
            tempList.add(city);
            String year= cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.FeedEntry.COLUMN_YEAR));
            tempList.add(year);
            String phone= cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.FeedEntry.COLUMN_PHONE));
            tempList.add(phone);
            String exhibits= cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.FeedEntry.COLUMN_EXHIBITS));
            tempList.add(exhibits);
            String priv= cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.FeedEntry.COLUMN_PRIVATE));
            tempList.add(priv);
        }
        cursor.close();
        return tempList;
    }

    public void closeDB(){
        DBHelper.close();
    }
}
