package com.cancunsoftware.hotelbooking.userinterfaces.activities;

import android.os.Bundle;

import com.cancunsoftware.hotelbooking.model.Room;
import com.cancunsoftware.hotelbooking.task.GetRoomDetailsAsyncTask;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.cancunsoftware.hotelbooking.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static com.cancunsoftware.hotelbooking.model.UtilsClass.findRoom;

public class RoomDetailsScrollingActivity extends AppCompatActivity {

    private int idRoom;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details_scrolling);
        idRoom = getIntent().getIntExtra("IDROOM", 0);
        title = getIntent().getStringExtra("TITLE");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);

        toolBarLayout.setTitle(title.toUpperCase());
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        new GetRoomDetailsAsyncTask(idRoom, RoomDetailsScrollingActivity.this).execute();
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