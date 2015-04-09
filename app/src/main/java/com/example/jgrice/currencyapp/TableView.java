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
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class TableView extends ActionBarActivity {
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_view);
        Intent intent = getIntent();
        String currency = intent.getStringExtra("currency");
        Date startDate = new Date(intent.getLongExtra("startDate", 0));
        Date endDate = new Date(intent.getLongExtra("endDate", 0));

        List<Rate> rates = null;
        DBHandler dbHandler = new DBHandler(this);
        try {
            dbHandler.openDatabase();
            rates = dbHandler.getAllRatesByCountry(currency);
            dbHandler.close();
        } catch (SQLException e) {
            Log.e(DBHandler.class.getName(), "Unable to open database");
        }

        TextView currencyTextView = (TextView) findViewById(R.id.currencyTextView);
        currencyTextView.setText("Canadian to " + currency);

        TableLayout currencyTable = (TableLayout) findViewById(R.id.currencyTableLayout);
        Collections.sort(rates, new Comparator<Rate>() {
            @Override
            public int compare(Rate lhs, Rate rhs) {
                return (rhs.getRefDate().before(lhs.getRefDate())) ? 1 : -1 ;
            }
        });
        for (Rate rate : rates) {
            if (rate.getRefDate().after(startDate) && rate.getRefDate().before(endDate)) {
                TableRow tableRow = new TableRow(this);

                TextView labelDate = new TextView(this);
                String date = new SimpleDateFormat("yyyy-MM").format(rate.getRefDate());
                labelDate.setText(date);
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
        if (currencyTable.getChildCount() != 1) {
            TableRow noDataTableRow = (TableRow) findViewById(R.id.noDataTableRow);
            noDataTableRow.setVisibility(View.INVISIBLE);
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
