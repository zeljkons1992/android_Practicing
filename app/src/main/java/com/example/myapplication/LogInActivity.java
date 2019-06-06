package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogInActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    @BindView(R.id.input_email) EditText inputEmail;
    @BindView(R.id.input_password) EditText inputPassword;
    @BindView(R.id.input_layout_email) TextInputLayout inputLayoutEmail;
    @BindView(R.id.input_layout_password) TextInputLayout inputLayoutPassword;
    @BindView(R.id.textView2) TextView textView;
    public static final String version = BuildConfig.POSTS_ENDPOINT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart(){
        super.onStart();

        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences("mypref",Context.MODE_PRIVATE);
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        textView.setText(version);
    }

    @OnClick(R.id.btn_signup)
    void submitButton(View view){
        submitForm();
    }
    private void submitForm() {



        if (!validateEmail()) {
            Toast.makeText(LogInActivity.this, "Wrong mail",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (!validatePassword()) {
            Toast.makeText(LogInActivity.this, "Wrong password",
                    Toast.LENGTH_LONG).show();
            return;
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("mail", inputEmail.getText().toString());
        editor.putString("password", inputPassword.getText().toString());
        editor.commit();
        Intent intent = new Intent(LogInActivity.this, RecyclerViewActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.slide_out);
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
    }
}
