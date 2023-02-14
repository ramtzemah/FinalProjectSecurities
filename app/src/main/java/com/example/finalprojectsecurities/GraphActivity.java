package com.example.finalprojectsecurities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class GraphActivity extends AppCompatActivity {
    private TextView graphLbl;
    public static final String LIST = "LIST";
    private HashMap<Integer,Double> list;
    private GraphView graphView;

    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        findViews();


        Intent intent = getIntent();
        list = (HashMap<Integer, Double>) intent.getSerializableExtra("list");

        DataPoint[] dataPoints = new DataPoint[list.size()];
        for (int i = 0; i <list.size(); i++){
            dataPoints[i] = new DataPoint(i, list.get(i));
        }

        calendar = GregorianCalendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);     // gets the current month
        int minute = calendar.get(Calendar.MINUTE);
        int thePoint = ((hours*60)+ minute);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
        graph.getViewport().setMinX(thePoint - 150);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(200000);
        graph.getViewport().setMaxX(thePoint +150); //(1440);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.addSeries(series);
    }

    private void findViews() {
        graphLbl = findViewById(R.id.graphLbl);
        graphView = findViewById(R.id.graph);
    }
}