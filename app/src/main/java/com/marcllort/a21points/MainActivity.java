package com.marcllort.a21points;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "21Points";

    //Farem servir el MainActivity com un gestor de les diferents activitats

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(this, LoginActivity.class);
        Log.d(TAG, "startActivity(intent) created");
        startActivity(intent);                                                      // Caldra fer startActibityForResult per saber si ha pogut fer login o no
    }


}
