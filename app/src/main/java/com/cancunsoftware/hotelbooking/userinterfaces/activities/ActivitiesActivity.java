package com.cancunsoftware.hotelbooking.userinterfaces.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.adapters.ActivityRecyclerViewAdapter;
import com.cancunsoftware.hotelbooking.model.UtilsClass;
import com.cancunsoftware.hotelbooking.task.GetActivitiesAsyncTask;

import java.util.Objects;

public class ActivitiesActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeLayout;
    private RecyclerView recyclerViewActivities;
    private ActivityRecyclerViewAdapter activityRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        View root = findViewById(android.R.id.content).getRootView();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        UtilsClass.lang = 4;

        swipeLayout = root.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(() -> {
            new GetActivitiesAsyncTask(this).execute();
        });
        new GetActivitiesAsyncTask(this).execute();

        recyclerViewActivities = findViewById(R.id.recyclerViewActivities);
        activityRecyclerViewAdapter = (ActivityRecyclerViewAdapter) recyclerViewActivities.getAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetActivitiesAsyncTask(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //recyclerViewActivities = findViewById(R.id.recyclerViewActivities);
        //activityRecyclerViewAdapter = (ActivityRecyclerViewAdapter) recyclerViewActivities.getAdapter();

        return super.onCreateOptionsMenu(menu);
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