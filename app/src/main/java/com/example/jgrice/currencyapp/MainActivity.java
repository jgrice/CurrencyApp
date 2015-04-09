package com.example.jgrice.currencyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


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
        //Currency Spinner
        List<String> currencies = dbHandler.getAllCurrency();
        final Spinner currencySpinner = (Spinner) findViewById(R.id.CurrencySpinner);
        ArrayAdapter<String> currencySpinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, currencies);
        currencySpinner.setAdapter(currencySpinnerAdapter);

        //Number Pickers
        final NumberPicker startYearPicker = (NumberPicker) findViewById(R.id.StartYearNumberPicker);
        startYearPicker.setMinValue(1950);
        startYearPicker.setMaxValue(2015);

        final NumberPicker startMonthPicker = (NumberPicker) findViewById(R.id.StartMonthNumberPicker);
        startMonthPicker.setMinValue(1);
        startMonthPicker.setMaxValue(12);

        final NumberPicker endYearPicker = (NumberPicker) findViewById(R.id.EndYearNumberPicker);
        endYearPicker.setMinValue(1950);
        endYearPicker.setMaxValue(2015);

        final NumberPicker endMonthPicker = (NumberPicker) findViewById(R.id.EndMonthNumberPicker);
        endMonthPicker.setMinValue(1);
        endMonthPicker.setMaxValue(12);

        //Submit Button
        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TableView.class);
                intent.putExtra("currency", currencySpinner.getSelectedItem().toString());
;
                Date startDate = new Date(startYearPicker.getValue()-1900, startMonthPicker.getValue(), 0);
                intent.putExtra("startDate", startDate.getTime());

                Date endDate = new Date(endYearPicker.getValue()-1900, endMonthPicker.getValue(), 0);
                intent.putExtra("endDate", endDate.getTime());
                startActivity(intent);
            }
        });

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
