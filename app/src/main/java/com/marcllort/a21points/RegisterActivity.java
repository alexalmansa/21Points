package com.marcllort.a21points;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marcllort.a21points.API.RegisterCallback;
import com.marcllort.a21points.API.UserTokenManager;

public class RegisterActivity extends AppCompatActivity implements RegisterCallback {

    private static final String TAG = "SignUpActivity";

    private Button mRegisterButton;
    private TextView mAlreadyRegTextView;
    private EditText mUsernameText;
    private EditText mEmailText;
    private EditText mPasswordText;
    private EditText mRePasswordText;
    private ProgressDialog nDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();                    //Fora actionbar
        setContentView(R.layout.activity_register);

        mRegisterButton = findViewById(R.id.btn_signup);
        mAlreadyRegTextView = findViewById(R.id.text_alreadyLogin);
        mUsernameText = findViewById(R.id.input_name);
        mEmailText = findViewById(R.id.input_mail);
        mPasswordText = findViewById(R.id.input_password);
        mRePasswordText = findViewById(R.id.input_reEnterPassword);

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


        String username = mUsernameText.getText().toString();
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();
        String repassword = mRePasswordText.getText().toString();

        // Implemetan el register AQUI
        UserTokenManager.getInstance().register(username, email, password, this);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onSignUpSuccess();
                        // onLoginFailed();

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
        nDialog.dismiss();
        new AlertDialog.Builder(this)
                .setTitle("Register")
                .setMessage("Register successful")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mRegisterButton.setEnabled(true);
                        finish();
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.baseline_done_black_18dp)
                .show();

    }


    @Override
    public void onSuccess() {

        nDialog = new ProgressDialog(this);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Signing up");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
    }

    @Override
    public void onFailure(Throwable t) {
        new AlertDialog.Builder(this)
                .setTitle("Token Error")
                .setMessage(t.getMessage())

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
