package com.marcllort.a21points;

import android.app.AlertDialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class BloodActivity extends AppCompatActivity implements RestAPICallBack {

    private static final String TAG = "21Blood";
    private LineChart chart;
    private FloatingActionButton addButton;
    private Button btn_Blood;
    private final Calendar myCalendar = Calendar.getInstance();
    private CheckBox ExerciceCheck, EatCheck, DrinkCheck;
    private TextView MonthBlood;
    private TextView daysLeft;
    private Boolean initializing = true;
    private ArrayList<Blood> valors;
    private Calendar date;
    int type = 0;


    //Farem servir el MainActivity com un gestor de les diferents activitats

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();                    //Fora actionbar

        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_blood);

        daysLeft = (TextView) findViewById(R.id.text_daysLeft);
        valors = new ArrayList<>();
        date = Calendar.getInstance();

        refreshGraph();
        thisMonthInitialize();
        addBlood();
        graphSetup();
        MultiStateToggle();
        ButtonBlod();

    }


    private void refreshGraph() {
        initializing = true;
        valors = new ArrayList<>();
        Calendar date = Calendar.getInstance();
        String myFormat = "yyyy-MM"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        //for (int i = 0; i < 5; i++) {
        RestAPIManager.getInstance().getBloodbyMonth(sdf.format(date.getTime()), this);
        //  date.add(Calendar.DAY_OF_MONTH, -7);
        //}

    }

    private void thisMonthInitialize() {
        Calendar cal = Calendar.getInstance();
        String myFormat = "yyyy-MM"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        RestAPIManager.getInstance().getBloodbyMonth(sdf.format(cal.getTime()), this);
        MonthBlood = (TextView) findViewById(R.id.text_points);
        MonthBlood.setText("-");
    }

    private void addBlood() {
        addButton = (FloatingActionButton) findViewById(R.id.floatingButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(BloodActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.blood_preassure_dialog, null);


                Button mAdd = (Button) mView.findViewById(R.id.btnAddPreassure);


                final EditText mDiast = (EditText) mView.findViewById(R.id.blod_preassure_diast);
                final EditText mSist = (EditText) mView.findViewById(R.id.blod_preassure_syst);


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();


                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Blood blod = new Blood();
                        int x = Integer.parseInt(mDiast.getText().toString());
                        int y = Integer.parseInt(mSist.getText().toString());

                        blod.setDiastolic(x);
                        blod.setSystolic(y);
                        ZonedDateTime z = ZonedDateTime.now(ZoneId.systemDefault());
                        blod.setTimestamp(toZoneDateTime(ZonedDateTime.now()));

                        RestAPIManager.getInstance().postBlood(blod, BloodActivity.this);
                        dialog.dismiss();
                    }
                });


            }
        });

    }

    private void MultiStateToggle() {
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

        msb_button.setValue(1);
        msb_button.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                Log.d(TAG, "Position: " + position);
                switch (position) {
                    case 0:
                        Intent main = new Intent(getApplicationContext(), MainActivity.class);
                        Log.d(TAG, "startActivity(intent) created"); //foresult caldra fer en algun moment
                        startActivity(main);

                        overridePendingTransition(R.transition.slide_in_left, R.transition.slide_in_left);
                        finish();
                        break;
                    case 2:
                        Intent weight = new Intent(getApplicationContext(), WeightActivity.class);
                        Log.d(TAG, "startActivity(intent) created"); //foresult caldra fer en algun moment
                        startActivity(weight);
                        finish();
                        overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);


                }
            }
        });
    }

    private void ButtonBlod() {
        btn_Blood = findViewById(R.id.btn_Blood);
        btn_Blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 0) {
                    type = 1;
                    btn_Blood.setText("Last systolic Measurement");
                } else {
                    type = 0;
                    btn_Blood.setText("Last diastolic Measurement");

                }
                refreshGraph();

            }
        });

    }

    private void checkReceived(ArrayBlood punt, int i) {

        String myFormat = "yyyy-MM"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);


        System.out.println("FUNCIONA, DEMANEM SEGUENT, REBUT" + punt.getReadings().get(i).getBlood() + "  " + punt.getReadings().get(i).getTimestamp());
        valors.add(punt.getReadings().get(i));
        date.add(Calendar.DAY_OF_MONTH, -7);

    }

    public static String toZoneDateTime(ZonedDateTime dateTime) {

        ZonedDateTime a = ZonedDateTime.now();
        System.out.println(a.toString());
        return a.toString();

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

    private void setData() {

        ArrayList<Entry> values = new ArrayList<>();
        System.out.println("LLARGADA" + valors.size());
        ArrayList<Blood> valors2 = new ArrayList<>(valors);
        Collections.reverse(valors2);
        int i = 0;
        if (type == 0) {
            for (Blood val : valors2) {
                values.add(new Entry(i, val.getBlood().intValue(), getResources().getDrawable(R.drawable.logo)));
                i++;
            }
        } else {


            for (Blood val : valors2) {
                values.add(new Entry(i, val.getSystolic().intValue(), getResources().getDrawable(R.drawable.logo)));
                i++;
            }
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
                .setMessage("Blood data added successfully.")
                .show();

        refreshGraph();
        date = Calendar.getInstance();
    }

    @Override
    public synchronized void onGetBlood(ArrayBlood blod) {

        int size = blod.getReadings().size();
        valors = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (initializing) {

                initializing = false;

                checkReceived(blod, i);
            } else {
                checkReceived(blod, i);

            }
            if (type == 0) {

                MonthBlood.setText(blod.getReadings().get(0).getBlood().toString());
                System.out.println("BLOD =" + blod.getReadings().get(i).getBlood());

            } else {

                MonthBlood.setText(blod.getReadings().get(0).getSystolic().toString());
            }


        }
        setData();
        chart.invalidate(); // Refresquem el graph

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
        String myFormat = "yyyy-MM";  //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        RestAPIManager.getInstance().getBloodbyMonth(sdf.format(date.getTime()), this);
    }

    @Override
    public void onPostPreferences(Preferences preferences) {

    }

    @Override
    public void onGetPreferences(Preferences preferences) {

    }


}