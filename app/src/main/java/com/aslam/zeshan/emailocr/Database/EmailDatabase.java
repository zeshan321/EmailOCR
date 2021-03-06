package com.aslam.zeshan.emailocr.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.aslam.zeshan.emailocr.Adapter.EmailObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EmailDatabase extends SQLiteOpenHelper {

    public Context con;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Emails";
    private static final String TABLE_CONTACTS = "EmailList";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";

    public EmailDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + "ID INTEGER PRIMARY KEY   AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_EMAIL + " TEXT)";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addEmail(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public void deleteEmail(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, "ID = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void update(int id, String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);

        db.update(TABLE_CONTACTS, values, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<EmailObject> getEmails() {
       List<EmailObject> map = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_CONTACTS + " ORDER BY " + KEY_NAME;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    int ID = cursor.getInt(cursor.getColumnIndex("ID"));
                    String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                    String email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL));

                    map.add(new EmailObject(ID, name, email, false));
                    cursor.moveToNext();
                }
            }

            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
            return map;
        }
        return map;

    }

    public int lastID() {
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();

        int ID = cursor.getInt(cursor.getColumnIndex("ID"));

        cursor.close();
        db.close();
        return ID;
    }

    public boolean contains(String email) {
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS + " WHERE  email = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }

        cursor.close();
        return false;
    }
}
