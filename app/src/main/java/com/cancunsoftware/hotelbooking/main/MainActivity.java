package com.cancunsoftware.hotelbooking.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.login.LoginActivity;
import com.cancunsoftware.hotelbooking.model.UtilsClass;
import com.cancunsoftware.hotelbooking.userinterfaces.activities.SettingsActivity;
import com.cancunsoftware.hotelbooking.userinterfaces.dialog.SearchReservationDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            SearchReservationDialog searchReservationDialog = new SearchReservationDialog(MainActivity.this);
            searchReservationDialog.show();
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_hotel, R.id.nav_room, R.id.nav_activities, R.id.nav_services)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        UtilsClass.lang = 4;

        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_settings).setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            drawer.closeDrawer(GravityCompat.START);
            return false;
        });

        menu.findItem(R.id.nav_logout).setOnMenuItemClickListener(item -> {
            signOut();
            return false;
        });
    }

    public void signOut() {
        SharedPreferences.Editor editor = getSharedPreferences("Preferences_HC", MODE_PRIVATE).edit();
        editor.putBoolean("isLogged", false);
        editor.apply();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            //Close Drawer menu
            drawer.closeDrawer(GravityCompat.START);
        else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            } else {
                doubleBackToExitPressedOnce = true;
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, R.string.exit_message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
            }
        }
    }
}