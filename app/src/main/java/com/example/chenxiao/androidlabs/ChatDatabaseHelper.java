package com.example.chenxiao.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Chenxiao on 2018-02-20.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "Messages.db";
    private static int VERSION_NUM = 3;
    public static final String KEY_ID = "ID";
    public static final String KEY_MESSAGE = "Message";
    public static final String TABLE_NAME = "myTable";
    private SQLiteDatabase database;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + KEY_ID
            + " integer primary key autoincrement, " + KEY_MESSAGE
            + " text not null);";


    public ChatDatabaseHelper(Context ctx) {
        super(ctx,DATABASE_NAME,null,VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper","Calling onUpgrade, oldVersion= " + oldVersion + "newVersion=" +newVersion);
    }


}
