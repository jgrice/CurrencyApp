package com.example.jgrice.currencyapp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExRateDataSource {
    private SQLiteDatabase database;
    private DBHandler dbHandler;
    private String[] allColumns = { DBHandler.COLUMN_ID, DBHandler.COLUMN_REFDATE,
            DBHandler.COLUMN_CURRENCY, DBHandler.COLUMN_VECTOR, DBHandler.COLUMN_VALUE };

    public ExRateDataSource(Context context) {
        dbHandler = new DBHandler(context);
    }

    public void open() {
        database = dbHandler.getReadableDatabase();
    }

    public void close() {
        dbHandler.close();
    }

    public List<ExRate> getAllExRates() {
        List<ExRate> rates = new ArrayList<ExRate>();
        Cursor cursor = database.query(DBHandler.TABLE_CURRENCY, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ExRate rate = cursorToExRate(cursor);
            rates.add(rate);
            Log.d(DBHandler.class.getName(), rate.toString());
            cursor.moveToNext();
        }
        cursor.close();
        return rates;
    }

    public List<ExRate> getAllExRatesByCountry (String country) {
        return null;
    }

    public List<String> getAllCurrency() {
        List<String> currency = new ArrayList<String>();
        String[] columns = {DBHandler.COLUMN_CURRENCY};
        Cursor cursor = database.query(DBHandler.TABLE_CURRENCY, columns,
               null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            currency.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return currency;
    }

    private ExRate cursorToExRate(Cursor cursor) {
        ExRate rate = new ExRate();
        rate.setId(cursor.getLong(0));
        rate.setRefDate(cursor.getString(1));
        rate.setCurrency(cursor.getString(2));
        rate.setVector(cursor.getString(3));
        rate.setValue(cursor.getLong(4));
        return rate;
    }
}
