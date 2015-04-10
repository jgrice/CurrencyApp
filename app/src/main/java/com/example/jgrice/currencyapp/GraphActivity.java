package com.example.jgrice.currencyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class GraphActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_activity);

        Intent intent = getIntent();
        final String currency = intent.getStringExtra("currency");
        final Date startDate = new Date(intent.getLongExtra("startDate", 0));
        final Date endDate = new Date(intent.getLongExtra("endDate", 0));

        List<Rate> rates = null;
        DBHandler dbHandler = new DBHandler(this);
        try {
            dbHandler.openDatabase();
            rates = dbHandler.getAllRatesByCountry(currency);
        } catch (SQLException e) {
            Log.e(DBHandler.class.getName(), "Unable to open database");
        }

        TextView currencyTextView = (TextView) findViewById(R.id.currencyTextView2);
        currencyTextView.setText("Canadian to " + currency);

        TableLayout currencyTable = (TableLayout) findViewById(R.id.currencyTableLayout);
        Collections.sort(rates, new Comparator<Rate>() {
            @Override
            public int compare(Rate lhs, Rate rhs) {
                return (rhs.getRefDate().before(lhs.getRefDate())) ? 1 : -1;
            }
        });

        GraphView currencyGraph = (GraphView) findViewById(R.id.currencyGraph);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(3);

        currencyGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));
        currencyGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        currencyGraph.getGridLabelRenderer().setNumHorizontalLabels(3);

        List<String> vectors = dbHandler.getAllVectorsByCurrency(currency);

        for (String vector : vectors) {
            ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();
            for (Rate rate : rates) {

                if (rate.getRefDate().after(startDate) && rate.getRefDate().before(endDate)
                        && rate.getVector().equals(vector)) {
                    dataPoints.add(new DataPoint(rate.getRefDate(), rate.getValue()));
                }
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(
                    dataPoints.toArray(new DataPoint[dataPoints.size()]));
            currencyGraph.addSeries(series);
        }

        Button viewGraph = (Button) findViewById(R.id.viewDataButton);
        viewGraph.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GraphActivity.this, TableActivity.class);
                intent.putExtra("currency", currency);
                intent.putExtra("startDate", startDate.getTime());

                intent.putExtra("endDate", endDate.getTime());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            Intent intent = new Intent(GraphActivity.this, HelpActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_home) {
            Intent intent = new Intent(GraphActivity.this, MainActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }
}
