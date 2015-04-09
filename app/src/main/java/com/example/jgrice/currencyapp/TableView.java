package com.example.jgrice.currencyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;


public class TableView extends ActionBarActivity {
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_view);
        Intent intent = getIntent();
        String currency = intent.getStringExtra("currency");
        int startYear = intent.getIntExtra("startYear", 0);
        int startMonth = intent.getIntExtra("startMonth", 0);
        int endYear = intent.getIntExtra("endYear", 0);
        int endMonth = intent.getIntExtra("endMonth", 0);
        List<Rate> rates = null;
        DBHandler dbHandler = new DBHandler(this);
        try {
            dbHandler.openDatabase();
            rates = dbHandler.getAllRatesByCountryInRange(currency, startYear, startMonth, endYear, endMonth);
            dbHandler.close();
        } catch (SQLException e) {
            Log.e(DBHandler.class.getName(), "Unable to open database");
        }
        //curencyTextView
        TextView currencyTextView = (TextView) findViewById(R.id.currencyTextView);
        currencyTextView.setText("Canadian to " + currency);

        TableLayout currencyTable = (TableLayout) findViewById(R.id.currencyTableLayout);
        if (rates != null && !rates.isEmpty()) {
            TableRow noDataTableRow = (TableRow) findViewById(R.id.noDataTableRow);
            noDataTableRow.setVisibility(View.INVISIBLE);
            for (Rate rate : rates) {
                TableRow tableRow = new TableRow(this);

                TextView labelDate = new TextView(this);
                labelDate.setText(rate.getRefDate());
                tableRow.addView(labelDate);

                TextView labelVector = new TextView(this);
                labelVector.setText(rate.getVector());
                tableRow.addView(labelVector);

                TextView labelRate = new TextView(this);
                labelRate.setText(String.format("%.6f", rate.getValue()));
                tableRow.addView(labelRate);

                currencyTable.addView(tableRow);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_table_view, menu);
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
