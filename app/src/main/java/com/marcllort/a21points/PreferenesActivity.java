package com.marcllort.a21points;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

class PreferenesActivity extends AppCompatActivity {

    private Bundle bundle;

    //preferences custom dialog
    private TextView textPreferences;
    private EditText points_goal;
    private Spinner weightUnits;
    private Button addButtonPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences_custom_dialog);

        bundle = savedInstanceState;

        points_goal = findViewById(R.id.weekly_points_goal2);
        weightUnits = findViewById(R.id.spinner);

        addButtonPreferences = (Button) findViewById(R.id.btnAddPreferences);


        //main_weightUnitsGoal.setText(preferences.getWeightUnits());
        //main_pointsGoal.setText(preferences.getWeeklyGoal());
    }

    public void onSavePreferences(View v){
        Preferences preferences = new Preferences();

        int numero = Integer.parseInt(String.valueOf(points_goal));
        preferences.setWeeklyGoal(numero);

        preferences.setWeightUnits(weightUnits.toString());

        //bundle.putString(String.valueOf(R.string.key_weight), preferences.getWeightUnits());
        //bundle.putInt(String.valueOf(R.string.key_points), preferences.getWeeklyGoal());

        finish();
    }
}
