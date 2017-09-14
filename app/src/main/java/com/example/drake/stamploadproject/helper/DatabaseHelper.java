package com.example.drake.stamploadproject.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.drake.stamploadproject.Class.ClientRegistrationDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drake on 4/23/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // initiate database version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "clientDatabase";
    public static final String TABLE_NAME = "clients";

    //User table Columns names
    private static final String COLUMN_CLIENT_ID = "id_client";
    public static final String COLUMN_CLIENT_NAME = "client_name";
    public static final String COLUMN_CLIENT_EMAIL ="client_email";
    public static final String COLUMN_CLIENT_PASSWORD = "client_password";
    public static final String COLUMN_CLIENT_PHONE = "client_phone";
    public static final String COLUMN_CLIENT_DOB = "client_dob";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
+ COLUMN_CLIENT_NAME + " TEXT," + COLUMN_CLIENT_EMAIL + " TEXT," + COLUMN_CLIENT_PASSWORD + " TEXT," + COLUMN_CLIENT_PHONE + " TEXT," + COLUMN_CLIENT_DOB + " TEXT" + ")";


    private String DROP_USER_TABLE = "DROP TABLE IF EXIST" + TABLE_NAME;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
// create a table
    @Override
    public void onCreate(SQLiteDatabase db) {
   db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
          db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }
    public void addUser(ClientRegistrationDetails user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENT_NAME, user.getClient_username());
        values.put(COLUMN_CLIENT_EMAIL, user.getClient_email());
        values.put(COLUMN_CLIENT_PASSWORD, user.getClient_password());
        values.put(COLUMN_CLIENT_DOB, user.getClient_dob());
        values.put(COLUMN_CLIENT_PHONE, user.getClient_phone());

        db.insert(TABLE_NAME, null, values);
        db.close();

    }
    public void InsertUser(String Username, String Password, String Email, String DoB, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENT_NAME, Username);
        values.put(COLUMN_CLIENT_EMAIL, Password);
        values.put(COLUMN_CLIENT_PASSWORD, Email);
        values.put(COLUMN_CLIENT_DOB, DoB);
        values.put(COLUMN_CLIENT_PHONE, phone);
        db.insert(TABLE_NAME, null, values);
        db.close();

    }



// fetch all the user and return user record
    public List<ClientRegistrationDetails> getAllUser() {
        String[] columns = {COLUMN_CLIENT_ID, COLUMN_CLIENT_EMAIL, COLUMN_CLIENT_NAME, COLUMN_CLIENT_DOB,COLUMN_CLIENT_PHONE, COLUMN_CLIENT_PASSWORD};

        String sortOrder = COLUMN_CLIENT_NAME + "ASC";
        List<ClientRegistrationDetails> userlist = new ArrayList<ClientRegistrationDetails>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, sortOrder);


        if (cursor.moveToFirst()) {
            do {
                ClientRegistrationDetails user = new ClientRegistrationDetails();
                user.setClient_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_ID))));
                user.setClient_username(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_NAME)));
                user.setClient_email(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_EMAIL)));
                user.setClient_password(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_PASSWORD)));
                user.setClient_dob(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_DOB)));
                user.setClient_phone(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_PHONE)));
                userlist.add(user);
            }
            while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return userlist;
    }
    // update row in db
    public void UpdateUser(ClientRegistrationDetails user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENT_NAME, user.getClient_username());
        values.put(COLUMN_CLIENT_EMAIL, user.getClient_email());
        values.put(COLUMN_CLIENT_PASSWORD, user.getClient_password());
        values.put(COLUMN_CLIENT_DOB, user.getClient_dob());
        values.put(COLUMN_CLIENT_PHONE, user.getClient_dob());

        db.update(TABLE_NAME, values, COLUMN_CLIENT_ID + "=?", new String []{
        String. valueOf(user.getClient_id())});
db.close();
    }
    // delete row in db
    public void deleteUser(ClientRegistrationDetails user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_CLIENT_ID + "=?", new String[]{String.valueOf(user.getClient_id())});
    }
    //method to check if user exists

    public boolean CheckUser(String email){
        // colummm to fetch
        String [] columns = {COLUMN_CLIENT_ID};
        // makes database to be readable
        SQLiteDatabase db = this.getReadableDatabase();
        //define de selection criteria
        String selection = COLUMN_CLIENT_EMAIL + "=?";
        //selection arguments
        String[] selectionArgs = {email};
//query user table with conditions
        /**
         * Here querry function to fetch records from user table
         * Sql query equivalent to this query function is
         * Select user_id from user where user_email ='Jack@android'
         */
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount >0){
            return true;

        }
        return false;
    }
    //check if user exist or not
    public boolean CheckUser(String email, String password){
        String [] columns = {COLUMN_CLIENT_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_CLIENT_EMAIL + "=?" + "AND" + COLUMN_CLIENT_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
//below is equivalent to Select user_id From where user_email = "Steve"
        Cursor cursor = db.query(TABLE_NAME,//table to query
                columns, // columns to return
                selection,//columns for where
                selectionArgs,//values for where
                null,//group of rows
                null,//filter by row group
                null);// the sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount >0){
            return true;

        }
        return false;
    }
    public String getSingleEntry (String email){
        SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query(TABLE_NAME, null,  COLUMN_CLIENT_EMAIL + "=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1){
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String Password = cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_PASSWORD));

        cursor.close();
        return Password;
    }
    public String getSecurity(String email){
        String [] columns = {COLUMN_CLIENT_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_CLIENT_EMAIL + "=?";
        String[] selectionArgs = {email};
//below is equivalent to Select user_id From where user_email = "Steve"
        Cursor cursor = db.query(TABLE_NAME,//table to query
                columns, // columns to return
                selection,//columns for where
                selectionArgs,//values for where
                null,//group of rows
                null,//filter by row group
                null);// the sort order
       String str = null;
        if(cursor.moveToFirst()){
            do{
                str =cursor.getString(cursor.getColumnIndex("PASSWORD"));
            }while(cursor.moveToNext());
        }
        return str;
    }
   public void getUserAccount() throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_CLIENT_EMAIL, COLUMN_CLIENT_NAME, COLUMN_CLIENT_DOB,COLUMN_CLIENT_PHONE, COLUMN_CLIENT_PASSWORD};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_NAME));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_EMAIL));
                String DOB = cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_DOB));

                int phone = cursor.getInt(cursor.getColumnIndex(COLUMN_CLIENT_PHONE));

            } while (cursor.moveToFirst());
        }
        cursor.close();

        }

    public String showEmail() throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        String email = "";

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_CLIENT_EMAIL}, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                email = cursor.getString(0);


            } while (cursor.moveToNext());
        }
        cursor.close();
        return email;
    }
    public String showName() throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        String username = "";

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_CLIENT_NAME}, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                username = cursor.getString(0);


            } while (cursor.moveToNext());
        }
        cursor.close();
        return username;
    }
/*
    public String[] getData(){

            SQLiteDatabase db = this.getReadableDatabase();

String selectQuery = "SELECT * FROM" + TABLE_NAME + "WHERE " + COLUMN_CLIENT_EMAIL + "=?" + "AND" + COLUMN_CLIENT_PASSWORD + "=?"+ new String[] {COLUMN_CLIENT_EMAIL,);
        if (cursor != null) {
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            if (cursor.moveToFirst()) {
                do {
                    String email = cursor.getString(0);
                    String name = cursor.getString(1);

                }while(cursor.moveToNext());
            }
            cursor.close();
            return data;
        }
*/



}

