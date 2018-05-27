package com.abc.example.com.timing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ABC on 29-09-2017.
 */

public class DATABASEHANDLER extends SQLiteOpenHelper {
    private static final String TABLE_REGISTRATION = "Store158";
    private static final String KEY_ID = "id";
    private static final String KEY_phone = "phone";
    private static final String KEY_notification = "notification";
    private static final String KEY_flag= "flag";
    private static final String KEY_Date = "created_date";
    private static final String KEY_msg = "msg";

    public DATABASEHANDLER(Context context) {
        super(context, Dbversion.DATABASE_NAME, null, Dbversion.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REGISTRATION = "CREATE TABLE IF NOT EXISTS " + TABLE_REGISTRATION + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_phone + " TEXT not null unique," + KEY_notification + " TEXT,"+ KEY_flag + " TEXT," + KEY_Date + " DATETIME,"+ KEY_msg + " TEXT" + ")";
        db.execSQL(CREATE_REGISTRATION);
     //   Log.e("sqffl", CREATE_REGISTRATION);
    }

        @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATION);
        onCreate(db);
    }

    public void addDATA(Registration reeg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(KEY_phone, reeg.getPhone());
        value.put(KEY_notification,reeg.getNotification());
        value.put(KEY_flag,reeg.getflag());
        value.put(KEY_Date , reeg.getCreated_date());
        db.insert(TABLE_REGISTRATION, null, value);
        db.close();
    }

    public void update(String receiver , String notification)
    {
        String notific = notification;
        String receiver_number = receiver;
        SQLiteDatabase DB = this.getWritableDatabase();
        String url =  "UPDATE "+TABLE_REGISTRATION+" SET notification='"+notific+"' WHERE phone='"+receiver_number+"'";
        DB.execSQL(url);
   //     Log.e("updatequery" , url);
    }
    public void update1(String number , String mes , String date)
    {
        String num = number;
        String message = mes;
        String Date = date;
        SQLiteDatabase DB = this.getWritableDatabase();
        String url = "UPDATE "+TABLE_REGISTRATION+" SET flag='0',msg='"+message+"',created_date='"+Date+"' WHERE phone='"+num+"'";
        DB.execSQL(url);
        Log.e("upldate111" , url);
        //Log.e("UPDATE DATE AND MSG",url);
    }
    public void updateflag1(String number)
    {
        String num = number;
        SQLiteDatabase DB = this.getWritableDatabase();
        String url = "UPDATE "+TABLE_REGISTRATION+" SET flag='1' WHERE phone='"+num+"'";
        DB.execSQL(url);
      //  Log.e("UPDATE DATE AND MSG",url);
    }
    public void updateflag0(String number)
    {
        String num = number;
        SQLiteDatabase DB = this.getWritableDatabase();
        String url = "UPDATE "+TABLE_REGISTRATION+" SET flag='0' WHERE phone='"+num+"'";
        DB.execSQL(url);
    //    Log.e("UPDATE DATE AND MSG",url);
    }
  /*  public List<Registration> getAllbydate() {
        try {
            List<Registration> list = new ArrayList<Registration>();
            String select = "SELECT * FROM " + TABLE_REGISTRATION+" WHERE "+KEY_flag+"='0'";
            Log.e("select" , select);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(select, null);
            if (cursor.moveToFirst()) {
                do {
                    Registration obj = new Registration();
                    obj.setId(Integer.parseInt(cursor.getString(0)));
                    obj.setphone(cursor.getString(1));
                    obj.setNotification(cursor.getString(2));
                    list.add(obj);

                } while (cursor.moveToNext());
            }
            return list;
        } catch (Exception e) {

        }
        return null;
    }*/


    public List<Registration> getAllData() {
        try {
            List<Registration> list = new ArrayList<Registration>();
            String select = "SELECT * FROM " + TABLE_REGISTRATION+" ORDER BY created_date DESC";
        //    Log.e("select1" , select);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(select, null);
            if (cursor.moveToFirst()) {
                do {
                    Registration obj = new Registration();
                    obj.setId(Integer.parseInt(cursor.getString(0)));
                    obj.setphone(cursor.getString(1));
                    obj.setNotification(cursor.getString(2));
                    obj.setFlag(cursor.getString(3));
                    obj.setCreated_date(cursor.getString(4));
                    obj.setMsg(cursor.getString(5));
           //         Log.e("setname",cursor.getString(1));
                    list.add(obj);
                } while (cursor.moveToNext());
            }
            return list;
        } catch (Exception e) {

        }
        return null;
    }
    public List<Registration> getAllbyData() {
        try {
            List<Registration> list = new ArrayList<Registration>();
            String select = "SELECT * FROM " + TABLE_REGISTRATION + " WHERE flag='0'";
          //  Log.e("select" , select);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(select, null);
            if (cursor.moveToFirst()) {
                do {
                    Registration obj = new Registration();
                    obj.setId(Integer.parseInt(cursor.getString(0)));
                    obj.setphone(cursor.getString(1));
                    obj.setNotification(cursor.getString(2));
                    obj.setFlag(cursor.getString(3));
                    obj.setCreated_date(cursor.getString(4));
                    obj.setMsg(cursor.getString(5));
            //        Log.e("setname",cursor.getString(1));
                    list.add(obj);
                } while (cursor.moveToNext());
            }
            return list;
        } catch (Exception e) {

        }
        return null;
    }

    public void delete(String i)
    {
        String num = i;
        SQLiteDatabase DB = this.getWritableDatabase();
        String url = "DELETE FROM "+TABLE_REGISTRATION+" WHERE phone='"+num+"'";
        DB.execSQL(url);
        //Log.e("UPDATE DATE AND MSG",url);

    }

}
