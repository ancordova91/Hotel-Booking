package com.cancunsoftware.hotelbooking.userinterfaces.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.model.HotelActivity;
import com.cancunsoftware.hotelbooking.model.UtilsClass;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class ConfirmationActivity extends AppCompatActivity {

    TextView textViewTitle, textViewAdults, textViewChildren, textViewDescription;
    ImageView imageView;
    MaterialButton btnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        HotelActivity activity = (HotelActivity)intent.getSerializableExtra("act");

        imageView = findViewById(R.id.ivDisplay);
        textViewTitle = findViewById(R.id.tvActivityTitle);
        textViewAdults = findViewById(R.id.tvSetAdults);
        textViewChildren = findViewById(R.id.tvSetChildren);
        textViewDescription = findViewById(R.id.tvActivityDescription);
        btnBuy = findViewById(R.id.btnSelectActivity);

        imageView.setImageResource(R.drawable.xcaret);
        textViewTitle.setText(activity.getTitle());
        if (!String.valueOf(activity.getMaxAdults()).contains("0")) {
            textViewAdults.setText(String.valueOf(activity.getMaxAdults()));
        } else textViewAdults.setText("10");
        if (!String.valueOf(activity.getMaxChildren()).contains("0")) {
            textViewChildren.setText(String.valueOf(activity.getMaxChildren()));
        } else textViewChildren.setText("10");
        if (UtilsClass.lang == 4) {
            textViewDescription.setText("Visit the most fun and spectacular parks of Cancun and Riviera Maya, each one with different attractions to live unique experiences.");
        } else textViewDescription.setText(Html.fromHtml(activity.getDescription()).toString());

        btnBuy.setOnClickListener(v -> {
            Intent intent2 = new Intent(ConfirmationActivity.this, FinalActivity.class);
            intent2.putExtra("activity", activity);
            startActivity(intent2);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        });
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