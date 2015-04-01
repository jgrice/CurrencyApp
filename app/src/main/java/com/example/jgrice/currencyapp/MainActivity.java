package com.example.jgrice.currencyapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.NumberPicker;

import java.io.IOException;
import java.sql.SQLException;


public class MainActivity extends ActionBarActivity {
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler dbHandler = new DBHandler(this);
        try {
            dbHandler.createDatabase();
            dbHandler.openDatabase();
        } catch (IOException e) {
            Log.e(DBHandler.class.getName(),"Unable to create database");
        } catch (SQLException e) {
            Log.e(DBHandler.class.getName(),"Unable to open database");
        }

        NumberPicker startYearPicker = (NumberPicker) findViewById(R.id.StartYearNumberPicker);
        startYearPicker.setMinValue(1950);
        startYearPicker.setMaxValue(2015);

        NumberPicker startMonthPicker = (NumberPicker) findViewById(R.id.StartMonthNumberPicker);
        startMonthPicker.setMinValue(1);
        startMonthPicker.setMaxValue(12);

        NumberPicker endYearPicker = (NumberPicker) findViewById(R.id.EndYearNumberPicker);
        endYearPicker.setMinValue(1950);
        endYearPicker.setMaxValue(2015);

        NumberPicker endMonthPicker = (NumberPicker) findViewById(R.id.EndMonthNumberPicker);
        endMonthPicker.setMinValue(1);
        endMonthPicker.setMaxValue(12);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
