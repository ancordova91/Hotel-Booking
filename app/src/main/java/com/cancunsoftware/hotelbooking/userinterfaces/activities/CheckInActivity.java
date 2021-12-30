package com.cancunsoftware.hotelbooking.userinterfaces.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.main.MainActivity;
import com.cancunsoftware.hotelbooking.model.UtilsClass;
import com.cancunsoftware.hotelbooking.userinterfaces.dialog.NoticeOfPrivacyDialog;
import com.cancunsoftware.hotelbooking.userinterfaces.dialog.SearchReservationDialog;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class CheckInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView textViewLastname = findViewById(R.id.textViewLastName);
        TextView textViewConfirm = findViewById(R.id.textViewConfirm);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewRoom = findViewById(R.id.textViewRoom);
        TextView textViewAdult = findViewById(R.id.textViewAdult);
        TextView textViewKid = findViewById(R.id.textViewKid);
        TextView textViewDateFrom = findViewById(R.id.textViewDateFrom);
        TextView textViewDateTo = findViewById(R.id.textViewDateTo);
        textViewLastname.setText(UtilsClass.hotelReservation.getLastname());
        textViewConfirm.setText(String.valueOf(UtilsClass.hotelReservation.getId()));
        textViewEmail.setText(UtilsClass.hotelReservation.getEmail());
        textViewRoom.setText(UtilsClass.hotelReservation.getRoomName());
        textViewAdult.setText(String.valueOf(UtilsClass.hotelReservation.getAdults()));
        textViewKid.setText(String.valueOf(UtilsClass.hotelReservation.getKids()));
        textViewDateFrom.setText(UtilsClass.hotelReservation.getFrom_date().substring(0, 10));
        textViewDateTo.setText(UtilsClass.hotelReservation.getTo_date().substring(0, 10));

        MaterialButton materialButtonPreCheckIn = findViewById(R.id.btnPreCheckin);
        materialButtonPreCheckIn.setOnClickListener(v -> {
            NoticeOfPrivacyDialog noticeOfPrivacyDialog = new NoticeOfPrivacyDialog(CheckInActivity.this);
            noticeOfPrivacyDialog.show();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}