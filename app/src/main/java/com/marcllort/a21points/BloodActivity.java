package com.marcllort.a21points;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.graphics.Color;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.marcllort.a21points.Blood;
import com.marcllort.a21points.Points;
import com.marcllort.a21points.R;
import com.marcllort.a21points.RestAPICallBack;
import com.marcllort.a21points.RestAPIManager;
import com.marcllort.a21points.UserToken;
import com.marcllort.a21points.Weight;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;


public class BloodActivity extends AppCompatActivity implements RestAPICallBack {

    private static final String TAG = "21Blood";
    private LineChart chart;
    private FloatingActionButton addButton;
    private EditText dateText;
    private final Calendar myCalendar = Calendar.getInstance();
    private CheckBox ExerciceCheck, EatCheck, DrinkCheck;
    private TextView MonthBlood;
    private TextView daysLeft;
    private Boolean initializing = true;
    private ArrayList<Blood> valors;
    private Calendar date;

    //Farem servir el MainActivity com un gestor de les diferents activitats

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();                    //Fora actionbar

        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        daysLeft = (TextView) findViewById(R.id.text_daysLeft);
        valors = new ArrayList<>();
        date = Calendar.getInstance();

        refreshGraph();
        thisMonthInitialize();
        addBlood();
        graphSetup();

    }


    private void refreshGraph() {
        initializing = true;
        valors = new ArrayList<>();
        Calendar date = Calendar.getInstance();
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        //for (int i = 0; i < 5; i++) {
        RestAPIManager.getInstance().getBloodbyMonth(sdf.format(date.getTime()), this);
        //  date.add(Calendar.DAY_OF_MONTH, -7);
        //}

    }

    private void checkReceived(Blood punt) {


        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);


        if (!punt.getTimestamp().equals(sdf.format(date.getTime()))) {
            RestAPIManager.getInstance().getBloodbyMonth(sdf.format(date.getTime()), this);
            System.out.println("FALLA, TORNEM A DEMANAR, rebut: " + punt.getBlood() + "  " + punt.getTimestamp());
        } else {
            System.out.println("FUNCIONA, DEMANEM SEGUENT, REBUT" + punt.getBlood() + "  " + punt.getTimestamp());
            valors.add(punt);
            date.add(Calendar.DAY_OF_MONTH, -7);
            RestAPIManager.getInstance().getBloodbyMonth(sdf.format(date.getTime()), this);

        }


    }

    private void addBlood() {
        addButton = (FloatingActionButton) findViewById(R.id.floatingButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(BloodActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);


                Button mAdd = (Button) mView.findViewById(R.id.btnAdd2);


                final EditText mNotes = (EditText) mView.findViewById(R.id.etnotes);


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                ExerciceCheck = (CheckBox) mView.findViewById(R.id.checkbox_exercice);
                EatCheck = (CheckBox) mView.findViewById(R.id.checkbox_eat);
                DrinkCheck = (CheckBox) mView.findViewById(R.id.checkbox_drink);


                dateText = (EditText) mView.findViewById(R.id.etdate);
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "yyyy-MM-dd"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

                        dateText.setText(sdf.format(myCalendar.getTime()));
                    }

                };

                dateText.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new DatePickerDialog(BloodActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });


                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        int exercici = ExerciceCheck.isChecked() ? 1 : 0;
                        int eat = EatCheck.isChecked() ? 1 : 0;
                        int drink = DrinkCheck.isChecked() ? 1 : 0;
                        RestAPIManager.getInstance().postBlood(new Blood(), BloodActivity.this);
                        dialog.dismiss();

                    }
                });
            }
        });

    }

    private void graphSetup() {
        chart = findViewById(R.id.chart1);
        chart.setViewPortOffsets(0, 0, 0, 0);

        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(false);

        // enable scaling and dragging
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        //chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(300);
        XAxis x = chart.getXAxis();
        x.setEnabled(false);

        YAxis y = chart.getAxisLeft();
        //y.setTypeface(tfLight);
        y.setLabelCount(5, false);
        y.setTextColor(R.color.verd);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);

        chart.getAxisRight().setEnabled(false);


        chart.getLegend().setEnabled(false);

        chart.animateXY(2000, 2000);

        // don't forget to refresh the drawing
        chart.invalidate();
    }

    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();

        ArrayList<Blood> valors2 = new ArrayList<>(valors);
        Collections.reverse(valors2);
        int i = 0;
        for (Blood val : valors2) {
            values.add(new Entry(i, val.getBlood().intValue(), getResources().getDrawable(R.drawable.logo)));
            i++;
        }


        LineDataSet set1;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHighlightIndicators(false);
            set1.setDrawHorizontalHighlightIndicator(false);


            // create a data object with the data sets
            LineData data = new LineData(set1);
            //data.setValueTypeface(tfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            chart.setData(data);

        }

    }

    private void thisMonthInitialize() {
        Calendar cal = Calendar.getInstance();
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        //RestAPIManager.getInstance().getBloodByMonth(sdf.format(cal.getTime()), this);
        MonthBlood = (TextView) findViewById(R.id.text_points);
        MonthBlood.setText("-");
    }

    private int daysLeftThisMonth(Blood Blood) {
        Calendar Month = Calendar.getInstance();
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        try {
            Month.setTime(sdf.parse(Blood.getTimestamp()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance();

        long difference = now.getTimeInMillis() - Month.getTimeInMillis();
        int days = (int) (difference / (1000 * 60 * 60 * 24));


        return 7 - days;
    }

    @Override
    public void onPostPoints(Points points) {

    }

    @Override
    public void onGetPoints(Points points) {

    }

    @Override
    public void onPostBlood(Blood Blood) {
        new AlertDialog.Builder(this)
                .setTitle("Blood added")
                .setMessage(Blood.toString())
                .show();


        refreshGraph();
        date = Calendar.getInstance();
    }

    @Override
    public synchronized void onGetBlood(Blood Blood) {

        Blood punt = Blood;


        if (initializing) {
            String strI = "" + punt.getBlood().toString();
            MonthBlood.setText(strI);


            int days = daysLeftThisMonth(Blood);
            if (days == 1) {
                daysLeft.setText(days + " day left");
            } else {
                daysLeft.setText(days + " days left");
            }
            initializing = false;

            checkReceived(punt);
        } else {
            if (valors.size() < 6) {
                checkReceived(punt);
                setData(10, 6);
                chart.invalidate();
            }

        }

    }

    @Override
    public void onPostWeight(Weight weight) {

    }

    @Override
    public void onGetWeight(Weight weight) {

    }

    @Override
    public void onLoginSuccess(UserToken userToken) {

    }


    @Override
    public void onFailure(Throwable t) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        RestAPIManager.getInstance().getBloodbyMonth(sdf.format(date.getTime()), this);

    }
}