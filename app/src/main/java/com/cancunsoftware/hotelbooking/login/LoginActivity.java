package com.cancunsoftware.hotelbooking.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.model.Hotel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextInputEditText textInputEditTextUser = findViewById(R.id.textInputEditTextUser);
        final TextInputEditText textInputEditTextPass = findViewById(R.id.textInputEditTextPass);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(view -> {

        });
    }

}
