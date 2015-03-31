package com.example.jgrice.currencyapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.InputStream;
import java.util.Scanner;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "CurrencyDB";

    public static final String TABLE_CURRENCY = "exrate";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_REFDATE = "refdate";
    public static final String COLUMN_CURRENCY = "currency";
    public static final String COLUMN_VECTOR = "vector";
    public static final String COLUMN_VALUE = "value";

    private static final String DB_CREATE = "CREATE TABLE "
            + TABLE_CURRENCY + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_REFDATE
            + " TEXT NOT NULL, " + COLUMN_CURRENCY
            + " TEXT NOT NULL," + COLUMN_VECTOR
            + " INTEGER NOT NULL,"+ COLUMN_VALUE +
            " INTEGER NOT NULL);";

    private final Context context;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
        InputStream is = context.getResources().openRawResource(R.raw.datasetcurrency);
        Scanner sc = new Scanner(is);
        String sql;
        while(sc.hasNext(";")) {
            sql = sc.next(";");
            db.execSQL(sql);
        }
        Log.i(DBHandler.class.getName(), "Created Database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(DBHandler.class.getName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCY);
        onCreate(db);

    }
}
