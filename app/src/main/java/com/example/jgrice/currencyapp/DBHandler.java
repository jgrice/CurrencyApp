package com.example.jgrice.currencyapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    public static final String TABLE_CURRENCY = "rates";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_REFDATE = "refdate";
    public static final String COLUMN_CURRENCY = "currency";
    public static final String COLUMN_VECTOR = "vector";
    public static final String COLUMN_VALUE = "value";
    private static final String DB_CREATE = "CREATE TABLE "
            + TABLE_CURRENCY + " ( " + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_REFDATE
            + " TEXT NOT NULL, " + COLUMN_CURRENCY
            + " TEXT NOT NULL, " + COLUMN_VECTOR
            + " INTEGER NOT NULL, "+ COLUMN_VALUE +
            " INTEGER NOT NULL )";
    private String[] allColumns = { DBHandler.COLUMN_ID, DBHandler.COLUMN_REFDATE,
            DBHandler.COLUMN_CURRENCY, DBHandler.COLUMN_VECTOR, DBHandler.COLUMN_VALUE };
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ratedb";
    private static final String DATABASE_PATH = "/data/data/com.example.jgrice.currencyapp/databases/";
    private final Context context;

    private SQLiteDatabase db;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();

        if(!dbExist) {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch(IOException e) {
                Log.e(DBHandler.class.getName(), "Error Copying Database");
            }
        }
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            String path = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e ) {
            Log.i(DBHandler.class.getName(),"Database doesn't exist yet");
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDatabase() throws IOException {
        InputStream is = context.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream os = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer))>0) {
            os.write(buffer, 0, length);
        }
        os.flush();
        os.close();
        is.close();
    }

    public void openDatabase() throws SQLException {
        String path = DATABASE_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(db != null) {
            db.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public List<Rate> getAllRates() {
        List<Rate> rates = new ArrayList<Rate>();
        Cursor cursor = db.query(DBHandler.TABLE_CURRENCY, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Rate rate = cursorToRate(cursor);
            rates.add(rate);
            Log.d(DBHandler.class.getName(), rate.toString());
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return rates;
    }

    public List<Rate> getAllRatesByCountry (String country) {
        return null;
    }

    /**
     * Query Database for unique Currencies
     * @return List of unique Currencies
     */
    public List<String> getAllCurrency() {
        List<String> currency = new ArrayList<String>();
        Cursor cursor = db.query(DBHandler.TABLE_CURRENCY, new String[] {DBHandler.COLUMN_CURRENCY},
                null, null, DBHandler.COLUMN_CURRENCY, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            currency.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return currency;
    }

    private Rate cursorToRate(Cursor cursor) {
        Rate rate = new Rate();
        rate.setId(cursor.getLong(0));
        rate.setRefDate(cursor.getString(1));
        rate.setCurrency(cursor.getString(2));
        rate.setVector(cursor.getString(3));
        rate.setValue(cursor.getLong(4));
        return rate;
    }


}
