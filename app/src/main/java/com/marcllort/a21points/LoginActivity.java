package com.marcllort.a21points;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.marcllort.a21points.API.RestAPICallBack;
import com.marcllort.a21points.API.RestAPIManager;
import com.marcllort.a21points.Model.ArrayBlood;
import com.marcllort.a21points.Model.Blood;
import com.marcllort.a21points.Model.Points;
import com.marcllort.a21points.Model.Preferences;
import com.marcllort.a21points.Model.UserToken;
import com.marcllort.a21points.Model.Weight;
import com.marcllort.a21points.Model.WeightPeriod;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements RestAPICallBack {
    private static final String TAG = "LoginActivity";


    // UI references.
    private EditText mUserView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private TextView mSignupTextView;
    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();                    //Fora actionbar
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mSignupTextView = findViewById(R.id.text_signup);
        mUserView = findViewById(R.id.input_user);
        mPasswordView = findViewById(R.id.input_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.btn_login);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);

        mSignupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                Log.d(TAG, "startActivity(intent) created"); //foresult caldra fer en algun moment
                startActivity(intent);
            }
        });

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_pass));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUserView.setError(getString(R.string.error_username));
            focusView = mUserView;
            cancel = true;
        } else if (!isEmailValid(username)) {
            mUserView.setError(getString(R.string.error_username));
            focusView = mUserView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            RestAPIManager.getInstance().getUserToken(username, password, this);

        }
    }

    /**
     * Función para comprobar si el email introducido por el usuario tiene una apariencia correcta
     *
     * @param email dirección introducida
     * @return booleano indicando si es correcto
     */
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.length() > 2;
    }

    /**
     * Función que comprueba si la contraseña introducida cumple con la longitud mínima requerida
     *
     * @param password contraseña introducida
     * @return booleano indicando si es correcto o no
     */
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    @Override
    public void onGetPoints(Points points) {
        new AlertDialog.Builder(this)
                .setTitle("Points")
                .setMessage(points.toString())
                .show();
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
    public void onPostPoints(Points points) {
        new AlertDialog.Builder(this)
                .setTitle("POST POINTS")
                .setMessage(points.toString())
                .show();

        RestAPIManager.getInstance().getPointsById(points.getId(), this);


    }

    @Override
    public void onLoginSuccess(UserToken userToken) {

        Intent intent = new Intent(this, MainActivity.class);
        Log.d(TAG, "startActivity(intent) created");
        if (!started) {
            startActivity(intent);
            started = true;
        }
        finish();
    }

    /**
     * Función que se ejecuta si algo falla cuando el usuario intenta iniciar sesión
     *
     * @param t
     */
    @Override
    public void onFailure(Throwable t) {
        new AlertDialog.Builder(this)
                .setTitle("Login Error")
                .setMessage("Invalid user or wrong password")


                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onPostPreferences(Preferences preferences) {

    }

    @Override
    public void onGetPreferences(Preferences preferences) {

    }


}

