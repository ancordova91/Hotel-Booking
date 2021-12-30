package com.cancunsoftware.hotelbooking.userinterfaces.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.model.HotelActivity;
import com.cancunsoftware.hotelbooking.model.UtilsClass;

import java.util.Objects;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        HotelActivity activity = (HotelActivity)intent.getSerializableExtra("activity");

        TextView textViewLastname = findViewById(R.id.textViewLastName);
        TextView textViewConfirm = findViewById(R.id.textViewConfirm);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewRoom = findViewById(R.id.textViewRoom);
        TextView textViewAdult = findViewById(R.id.textViewAdult);
        TextView textViewKid = findViewById(R.id.textViewKid);
        TextView textViewDateFrom = findViewById(R.id.textViewDateFrom);
        TextView textViewDateTo = findViewById(R.id.textViewDateTo);
        TextView textViewPackage = findViewById(R.id.tvPackage);
        TextView textViewSetPackage = findViewById(R.id.tvTour);

        textViewLastname.setText(UtilsClass.hotelReservation.getLastname());
        textViewConfirm.setText(String.valueOf(UtilsClass.hotelReservation.getId()));
        textViewEmail.setText(UtilsClass.hotelReservation.getEmail());
        textViewRoom.setText(UtilsClass.hotelReservation.getRoomName());
        textViewAdult.setText(String.valueOf(UtilsClass.hotelReservation.getAdults()));
        textViewKid.setText(String.valueOf(UtilsClass.hotelReservation.getKids()));
        textViewDateFrom.setText(UtilsClass.hotelReservation.getFrom_date().substring(0, 10));
        textViewDateTo.setText(UtilsClass.hotelReservation.getTo_date().substring(0, 10));
        textViewSetPackage.setText(activity.getTitle());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}