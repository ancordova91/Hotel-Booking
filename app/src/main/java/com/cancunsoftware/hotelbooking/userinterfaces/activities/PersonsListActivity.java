package com.cancunsoftware.hotelbooking.userinterfaces.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.adapters.PersonsRecyclerViewAdapter;
import com.cancunsoftware.hotelbooking.main.MainActivity;
import com.cancunsoftware.hotelbooking.model.UtilsClass;
import com.cancunsoftware.hotelbooking.task.GetActivitiesAsyncTask;
import com.cancunsoftware.hotelbooking.userinterfaces.fragments.ActivitiesFragment;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class PersonsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MaterialButton btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(view1 -> {
            Intent intent = new Intent(PersonsListActivity.this, ActivitiesActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UtilsClass.hotelReservation.getPersons() != null && UtilsClass.hotelReservation.getPersons().size() > 0){
            RecyclerView recyclerViewPersons = findViewById(R.id.recyclerViewPersons);
            recyclerViewPersons.setHasFixedSize(true);
            recyclerViewPersons.setLayoutManager(new LinearLayoutManager(PersonsListActivity.this));
            PersonsRecyclerViewAdapter adapter = new PersonsRecyclerViewAdapter(PersonsListActivity.this, UtilsClass.hotelReservation.getPersons());
            recyclerViewPersons.setAdapter(adapter);
        }
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