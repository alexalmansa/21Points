package com.marcllort.a21points;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.marcllort.a21points.API.RestAPICallBack;
import com.marcllort.a21points.API.RestAPIManager;
import com.marcllort.a21points.Model.ArrayBlood;
import com.marcllort.a21points.Model.Blood;
import com.marcllort.a21points.Model.Points;
import com.marcllort.a21points.Model.Preferences;
import com.marcllort.a21points.Model.UserToken;
import com.marcllort.a21points.Model.Weight;
import com.marcllort.a21points.Model.WeightPeriod;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import android.view.LayoutInflater;

public class MainActivity extends AppCompatActivity implements RestAPICallBack {

    private static final String TAG = "21Points";
    private LineChart chart;
    private FloatingActionButton addButton;
    private EditText dateText;
    private final Calendar myCalendar = Calendar.getInstance(Locale.US);
    private CheckBox ExerciceCheck, EatCheck, DrinkCheck;
    private TextView weekPoints;
    private TextView daysLeft;
    private Boolean initializing = true;
    private ArrayList<Points> valors;
    private Calendar date;
    private EditText points_goal;
    private EditText weightUnits;
    private Button addButtonPreferences;
    private ImageButton settings;
    private MultiStateToggleButton multiButton;
    private AlertDialog dialog;
    private TextView pointsTo;
    private int objective=0;

    //main activity
    private TextView main_pointsGoal;
    private TextView main_weightUnitsGoal;


    //Farem servir el MainActivity com un gestor de les diferents activitats

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();                    //Fora actionbar

        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        daysLeft = (TextView) findViewById(R.id.text_daysLeft);
        pointsTo=(TextView) findViewById(R.id.text_pointsLeft);

        valors = new ArrayList<>();
        date = Calendar.getInstance(Locale.US);


        multiStateToggle();
        firstDayWeek();
        dialogBuilder();
        refreshGraph();
        thisWeekInitialize();
        addPoints();
        preferencesDialog();
        graphSetup();

    }


    private void multiStateToggle() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        MultiStateToggleButton msb_button = (MultiStateToggleButton) this.findViewById(R.id.mstb_multi_id);
        ImageButton button1 = (ImageButton) inflater.inflate(R.layout.btn_image, msb_button, false);
        button1.setImageResource(R.drawable.points);
        ImageButton button2 = (ImageButton) inflater.inflate(R.layout.btn_image, msb_button, false);
        button2.setImageResource(R.drawable.blood);
        ImageButton button3 = (ImageButton) inflater.inflate(R.layout.btn_image, msb_button, false);
        button3.setImageResource(R.drawable.weight);

        View[] buttons = new View[]{button1, button2, button3};
        msb_button.setButtons(buttons, new boolean[buttons.length]);

        msb_button.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                Log.d(TAG, "Position: " + position);
                switch (position) {
                    case 1:
                        Intent blood = new Intent(getApplicationContext(), BloodActivity.class);
                        Log.d(TAG, "startActivity(intent) created"); //foresult caldra fer en algun moment
                        startActivity(blood);
                        overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);
                        finish();
                        break;
                    case 2:
                        Intent weight = new Intent(getApplicationContext(), WeightActivity.class);
                        Log.d(TAG, "startActivity(intent) created"); //foresult caldra fer en algun moment
                        startActivity(weight);
                        overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);

                        finish();
                }
            }
        });
        msb_button.setValue(0);
    }

    private void firstDayWeek() {
        date = Calendar.getInstance(Locale.US);

        while (date.get(Calendar.DAY_OF_WEEK) > date.getFirstDayOfWeek()) {
            date.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
        }
        date.add(Calendar.DATE, +1);
    }

    private void dialogBuilder() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        //cogemos el dialog y lo ponemos como view
        View mView = getLayoutInflater().inflate(R.layout.preferences_dialog, null);

        points_goal = (EditText) mView.findViewById(R.id.weekly_points_goal2);


        addButtonPreferences = (Button) mView.findViewById(R.id.btnAddPreferences);
        addButtonPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objective=Integer.parseInt(points_goal.getText().toString());
                int points_left = Integer.parseInt(points_goal.getText().toString()) - Integer.parseInt(weekPoints.getText().toString());
                pointsTo.setText("Points to goal: "+String.valueOf(points_left));
                points_goal.setText("");
                dialog.dismiss();
            }
        });

        mBuilder.setView(mView);
        dialog = mBuilder.create();

    }

    private void refreshGraph() {
        initializing = true;
        valors = new ArrayList<>();
        Calendar date = (Calendar) Calendar.getInstance(Locale.US);
        firstDayWeek();
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        RestAPIManager.getInstance().getPointsByWeek(sdf.format(date.getTime()), this);
    }

    private void thisWeekInitialize() {
        Calendar cal = Calendar.getInstance(Locale.US);
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        weekPoints = (TextView) findViewById(R.id.text_points);
        weekPoints.setText("-");
    }

    private void addPoints() {
        addButton = (FloatingActionButton) findViewById(R.id.floatingButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.points_dialog, null);

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
                        new DatePickerDialog(MainActivity.this, date, myCalendar
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
                        RestAPIManager.getInstance().postPoints(new Points(dateText.getText().toString(), exercici, eat, drink, mNotes.getText().toString()), MainActivity.this);
                        dialog.dismiss();

                    }
                });
            }
        });

    }

    private void preferencesDialog() {

        RestAPIManager.getInstance().getMyPreferences(this);
        settings = (ImageButton) findViewById(R.id.action_settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();

                /*Intent intent = new Intent(getBaseContext(), PreferenesActivity.class);
                startActivityForResult(intent, 0);
                Bundle returnedInfo = intent.getExtras();
                main_weightUnitsGoal.setText(returnedInfo.getString(getString(R.string.key_weight), "none"));
                main_pointsGoal.setText(returnedInfo.getInt(getString(R.string.key_points), 15));
                */


                /*
                Preferences preferences = new Preferences();
                points_goal = findViewById(R.id.weekly_points_goal2);
                int numero = Integer.parseInt(String.valueOf(points_goal));
                preferences.setWeeklyGoal(numero);
                weightUnits = findViewById(R.id.weight_units);
                preferences.setWeightUnits(weightUnits.toString());
                main_weightUnitsGoal.setText(preferences.getWeightUnits());
                main_pointsGoal.setText(preferences.getWeeklyGoal());
                 */
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

        ArrayList<Points> valors2 = new ArrayList<>(valors);
        Collections.reverse(valors2);
        int i = 0;
        for (Points val : valors2) {
            values.add(new Entry(i, val.getPoints().intValue(), getResources().getDrawable(R.drawable.logo)));
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

    private void checkReceived(Points punt) {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);


        if (!punt.getWeek().equals(sdf.format(date.getTime()))) {
            RestAPIManager.getInstance().getPointsByWeek(sdf.format(date.getTime()), this);
            System.out.println("FALLA, TORNEM A DEMANAR, rebut: " + punt.getPoints() + "  " + punt.getWeek() + "demamant:" + sdf.format(date.getTime()));
        } else {
            System.out.println("FUNCIONA, DEMANEM SEGUENT, REBUT" + punt.getPoints() + "  " + punt.getWeek());
            valors.add(punt);
            date.add(Calendar.DAY_OF_MONTH, -7);
            RestAPIManager.getInstance().getPointsByWeek(sdf.format(date.getTime()), this);
        }

    }

    private int daysLeftThisWeek(Points points) {
        Calendar week = Calendar.getInstance(Locale.US);
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        try {
            week.setTime(sdf.parse(points.getWeek()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance(Locale.US);

        long difference = now.getTimeInMillis() - week.getTimeInMillis();
        int days = (int) (difference / (1000 * 60 * 60 * 24));


        return 7 - days;
    }


    @Override
    public void onPostPoints(Points points) {
        new AlertDialog.Builder(this)
                .setTitle("Points added")
                .setMessage("Points added succesfully.")
                .show();

        refreshGraph();
        firstDayWeek();
    }

    @Override
    public synchronized void onGetPoints(Points points) {

        Points punt = points;

        if (initializing) {
            String strI = "" + punt.getPoints().toString();
            weekPoints.setText(strI);


            int days = daysLeftThisWeek(points);
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
                if (objective!=0) {
                    System.out.println("ENTRA");
                    int points_left = objective - Integer.parseInt(weekPoints.getText().toString());
                    pointsTo.setText("Points to goal: " + String.valueOf(points_left));
                }
            }

        }

    }

    @Override
    public void onPostBlood(Blood blood) {

    }

    @Override
    public void onGetBlood(ArrayBlood blood) {

    }

    @Override
    public void onPostWeight(Weight weight) {

    }

    @Override
    public void onGetWeight(WeightPeriod weight) {

    }

    @Override
    public void onLoginSuccess(UserToken userToken) {

    }

    @Override
    public void onFailure(Throwable t) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        RestAPIManager.getInstance().getPointsByWeek(sdf.format(date.getTime()), this);
    }

    @Override
    public void onPostPreferences(Preferences preferences) {

    }

    @Override
    public void onGetPreferences(Preferences preferences) {
        new AlertDialog.Builder(this).setMessage(preferences.toString());
    }


}