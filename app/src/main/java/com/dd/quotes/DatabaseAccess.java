package com.dd.quotes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess extends AppCompatActivity {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    int getCursorCount() {
        String sqlQueryText = "SELECT quotes FROM quote";
        Cursor cursor = database.rawQuery(sqlQueryText, null);
        return cursor.getCount();
    }

    public List<String> getList(int randomNumber) {

        List<String> list = new ArrayList<>();
        String sqlQueryText = "SELECT quotes, authors FROM quote";
        Cursor cursor = database.rawQuery(sqlQueryText, null);

//        int randomNumber = new Random().nextInt(cursor.getCount())+1;


//        cursor.moveToPosition(randomNumber);
        cursor.move(randomNumber);
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

}
