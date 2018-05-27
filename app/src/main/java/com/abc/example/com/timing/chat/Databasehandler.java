package com.abc.example.com.timing.chat;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABC on 12-10-2017.
 */

public class Databasehandler extends SQLiteOpenHelper {

    private static final String TABLE_REGISTRATION = "Store65";
    private static final String KEY_ID = "id";
    private static final String KEY_sender_phone = "sender_phone";
    private static final String KEY_receiver_phone = "receiver_phone";
    private static final String KEY_mes = "mes";
    private static final String KEY_date = "date";
    private static final String KEY_tvi = "tvi";
    private static final String Key_flag = "flag";

    public Databasehandler(Context context) {
        super(context, Dbversion.DATABASE_NAME, null, Dbversion.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REGISTRATION = "CREATE TABLE IF NOT EXISTS " + TABLE_REGISTRATION + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_mes + " TEXT," + KEY_date +" DATETIME,"+KEY_sender_phone+ " TEXT," + KEY_receiver_phone + " TEXT," + KEY_tvi + " TEXT," + Key_flag + " TEXT" + ")";
        db.execSQL(CREATE_REGISTRATION);
        Log.e("sqffl", CREATE_REGISTRATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATION);
        onCreate(db);
    }

    public void addDATA(Registration reeg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(KEY_mes, reeg.getmes());
        value.put(KEY_date,reeg.getdate());
        value.put(KEY_sender_phone,reeg.getSender_phone());
        value.put(KEY_receiver_phone , reeg.getReciever_phone());
        value.put(KEY_tvi , reeg.getTvi());
        value.put(Key_flag , reeg.getFlag());
        Log.e("rnumber" , reeg.getReciever_phone());
        Log.e("snumber" , reeg.getSender_phone());
        Log.e("dateee",reeg.getdate());
        Log.e("meess",reeg.getmes());
        Log.e("tvi" , reeg.getTvi());
        db.insert(TABLE_REGISTRATION, null, value);
        db.close();
    }

    public List<Registration> getAllData (String receive) {
        try {
            List<Registration> list = new ArrayList<Registration>();
            String select = "SELECT * FROM " + TABLE_REGISTRATION+" WHERE "+KEY_receiver_phone+"='"+receive+"' OR "+KEY_sender_phone+"='"+receive+"'";
            Log.e("selectquery" , select);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(select, null);
            if (cursor.moveToFirst()) {
                do {
                    Registration obj = new Registration();
                    obj.setId(Integer.parseInt(cursor.getString(0)));
                    obj.setmes(cursor.getString(1));
                    Log.e("setmm" , cursor.getString(1));
                    obj.setdate(cursor.getString(2));
                    Log.e("setdd" , cursor.getString(2));
                    obj.setSender_phone(cursor.getString(3));
                    Log.e("se", cursor.getString(3));
                    obj.setReciever_phone(cursor.getString(4));
                    Log.e("re" , cursor.getString(4));
                    obj.setTvi(cursor.getString(5));
                    Log.e("te" , cursor.getString(5));
                    list.add(obj);
                } while (cursor.moveToNext());
            }
            return list;
        }
        catch (Exception e)
        {

        }
        return null;
    }

    public List<Registration> getAllbydate(String date , String receive) {
        try {
            List<Registration> list = new ArrayList<Registration>();

            Log.e("qqqdate",date);
            Log.e("qqq",receive);
            String select = "SELECT * FROM " + TABLE_REGISTRATION+" WHERE "+KEY_date+ "> '"+date+"'"+" AND ("+KEY_receiver_phone+"='"+receive+"' OR "+KEY_sender_phone+"='"+receive+"')";
            Log.e("selectqq" , select);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(select, null);
            if (cursor.moveToFirst()) {
                do {
                    Registration obj = new Registration();
                    obj.setId(Integer.parseInt(cursor.getString(0)));
                    obj.setmes(cursor.getString(1));
                    obj.setdate(cursor.getString(2));
                    Log.e("setdate",cursor.getString(2));
                    obj.setSender_phone(cursor.getString(3));
                    Log.e("se", cursor.getString(3));
                    obj.setReciever_phone(cursor.getString(4));
                    Log.e("re" , cursor.getString(4));
                    obj.setTvi(cursor.getString(5));
                    Log.e("tvi" , cursor.getString(5));

                    list.add(obj);

                } while (cursor.moveToNext());
            }
            return list;
        } catch (Exception e) {

        }
        return null;
    }
    public List<Registration> getdidntsenddaata() {
        try {
            List<Registration> list = new ArrayList<Registration>();

            String select = "SELECT * FROM " + TABLE_REGISTRATION+" WHERE flag=0";
            Log.e("selectqq" , select);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(select, null);
            if (cursor.moveToFirst()) {
                do {
                    Registration obj = new Registration();
                    obj.setId(Integer.parseInt(cursor.getString(0)));
                    obj.setmes(cursor.getString(1));
                    obj.setdate(cursor.getString(2));
                    Log.e("setmes",cursor.getString(1));
                    obj.setSender_phone(cursor.getString(3));
                    Log.e("se", cursor.getString(3));
                    obj.setReciever_phone(cursor.getString(4));
                    Log.e("re" , cursor.getString(4));
                    obj.setTvi(cursor.getString(5));
                    Log.e("tvi" , cursor.getString(5));

                    list.add(obj);

                } while (cursor.moveToNext());
            }
            return list;
        } catch (Exception e) {

        }
        return null;
    }
    public void updateflag1(String number)
    {
        String num = number;
        SQLiteDatabase DB = this.getWritableDatabase();
        String url = "UPDATE "+TABLE_REGISTRATION+" SET flag='1' WHERE date='"+num.trim()+"'";
        Log.e("upldateflag123" , url);
        DB.execSQL(url);
        //    Log.e("UPDATE DATE AND MSG",url);
    }

}
