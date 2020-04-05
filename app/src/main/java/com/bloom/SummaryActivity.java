package com.bloom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity {
    private navBarListener navBar;
    private int dfname = 0;
    private String mmname;
    private String taggg;
    private int afname = 0;
    private long total_taggg_time;
    private ArrayList flowers;
    private SharedPreferences thePrefs;
    private SharedPreferences myPrefs;
    private String[] act;
    private ArrayList<Integer> barChartColors;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
        thePrefs = getSharedPreferences("tagpage", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_summary);

        //add navBar listener
        navBar = new navBarListener( (BottomNavigationView) findViewById(R.id.navBar), this);

        //Retrieving stored data
        dfname = Integer.parseInt(myPrefs.getString("dead_flower",null));
        mmname = myPrefs.getString("modify",null);
        taggg = thePrefs.getString("curr_tag",null);//get the current chosen tag
        afname = Integer.parseInt(myPrefs.getString("alive_flower",null));
        total_taggg_time = thePrefs.getLong(taggg,0);

        pieChartBuilder();
        barChartBuilder();

    }

    public void goHome(View view) {
        Intent intent = new Intent(SummaryActivity.this, MainHomepage.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    //Creates the piechart displaying dead vs alive flowers
    public void pieChartBuilder() {
        //intialize colors for pieChart
        ArrayList chartColors = new ArrayList();
        chartColors.add(ContextCompat.getColor(this, R.color.chartPink));
        chartColors.add(ContextCompat.getColor(this, R.color.chartBlue));

        //get pieChart id
        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(false);

        //Create tagTime for pieChart
        flowers = new ArrayList();
        flowers.add(new PieEntry(dfname,"Dead"));
        flowers.add(new PieEntry(afname,"Alive"));

        //Create data set and give to pieChart
        PieDataSet pieDataSet = new PieDataSet(flowers, "Flowers");
        pieDataSet.setLabel(null);

        PieData pieData = new PieData(pieDataSet);
        //Set Size and Color of Numbers in the PieChart
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTextSize(15);


        //Pass Data to PieChart
        pieChart.setData(pieData);

        //Set pieChart Color
        pieDataSet.setColors(chartColors);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(0);
        pieChart.setDrawEntryLabels(false);
        pieChart.setTouchEnabled(false);
        pieChart.setDrawCenterText(false);

        //Set animation
        pieChart.animateXY(500,500);

        //Set Description
        Description des = pieChart.getDescription();
        des.setEnabled(false);

        //Modify legend properties
        Legend leg = pieChart.getLegend();
        leg.setTextColor(ContextCompat.getColor(this, R.color.white));
        leg.setTextSize(10);

    }


    //Creates the barChart displaying time spent on each activity type
    public void barChartBuilder() {

        setBarChartColors();

        BarChart barChart = (BarChart)findViewById(R.id.barChart);
        act = new String[]{"Study", "Workout", "Fun", "Meet", "Meditate", "Social"};
        BarDataSet barDataSet = new BarDataSet(getTime(), null);
        barDataSet.setBarBorderWidth(0f);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(10);
        barDataSet.setColors(barChartColors);
        BarData barData = new BarData(barDataSet);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(act);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(formatter);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateXY(1000, 1000);
        barChart.invalidate();

        barChart.setDrawGridBackground(false);
        barChart.setTouchEnabled(false);
        barChart.setVisibleXRangeMaximum(9);
        //Modify legend properties
        Legend leg = barChart.getLegend();
        leg.setEnabled(false);

        //Set Description
        Description des = barChart.getDescription();
        des.setEnabled(false);

    }


    //retrieves time spent on each activity from storage
    private ArrayList getTime() {

        ArrayList<BarEntry> tagTime = new ArrayList<>();
        tagTime.add(new BarEntry(0f, thePrefs.getLong("STUDY",0)));
        tagTime.add(new BarEntry(1f, thePrefs.getLong("WORKOUT",0)));
        tagTime.add(new BarEntry(2f, thePrefs.getLong("FUN",0)));
        tagTime.add(new BarEntry(3f, thePrefs.getLong("MEETING",0)));
        tagTime.add(new BarEntry(4f, thePrefs.getLong("MEDITATION",0)));
        tagTime.add(new BarEntry(5f, thePrefs.getLong("SOCIAL",0)));

        return tagTime;
    }




    //Add my own colors
    private void setBarChartColors() {
        barChartColors = new ArrayList<>();
        barChartColors.add(ContextCompat.getColor(this, R.color.barGreen));
        barChartColors.add(ContextCompat.getColor(this, R.color.barBlue));
        barChartColors.add(ContextCompat.getColor(this, R.color.barPink));
        barChartColors.add(ContextCompat.getColor(this, R.color.barOrange));
        barChartColors.add(ContextCompat.getColor(this, R.color.barRed));
        barChartColors.add(ContextCompat.getColor(this, R.color.barPurple));
        barChartColors.add(ContextCompat.getColor(this, R.color.barYellow));
        barChartColors.add(ContextCompat.getColor(this, R.color.barDarkBlue));
        barChartColors.add(ContextCompat.getColor(this, R.color.barMaroon));
    }


}
