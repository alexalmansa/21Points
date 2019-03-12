package com.marcllort.a21points;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private Button mRegisterButton;
    private TextView mAlreadyRegTextView;
    private EditText mUsernameText;
    private EditText mEmailText;
    private EditText mPasswordText;
    private EditText mRePasswordText;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();                    //Fora actionbar
        setContentView(R.layout.activity_signup);

        mRegisterButton = (Button) findViewById(R.id.btn_signup);
        mAlreadyRegTextView = (TextView) findViewById(R.id.text_alreadyLogin);
        mUsernameText = (EditText) findViewById(R.id.input_name);
        mEmailText = (EditText) findViewById(R.id.input_email);
        mPasswordText = (EditText) findViewById(R.id.input_password);
        mRePasswordText = (EditText) findViewById(R.id.input_reEnterPassword);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        mAlreadyRegTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void register() {
        Log.d(TAG, "SignUp function");

        if (!validate()) {
            onSignUpFailed();
            return;
        }
        mRegisterButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String user = mUsernameText.getText().toString();
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();
        String repassword = mRePasswordText.getText().toString();

        // Implemetan el register AQUI

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onSignUpSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                        finish(); //sobrara
                    }
                }, 3000);
    }


    public boolean validate() {
        boolean valid = true;

        String user = mUsernameText.getText().toString();
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();
        String repassword = mRePasswordText.getText().toString();

        if (user.isEmpty()) {
            mUsernameText.setError(getText(R.string.error_user));
            valid = false;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailText.setError(getText(R.string.error_mail));
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPasswordText.setError(getText(R.string.error_pass));
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        if (repassword.isEmpty() || !repassword.equals(password)) {
            mRePasswordText.setError(getText(R.string.error_repass));
            valid = false;
        }

        return valid;
    }


    public void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();
        mRegisterButton.setEnabled(true);
    }

    public void onSignUpSuccess() {
        mRegisterButton.setEnabled(true);
        finish();
    }
}
