package com.cancunsoftware.hotelbooking.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.model.Person;
import com.cancunsoftware.hotelbooking.model.UtilsClass;
import com.cancunsoftware.hotelbooking.userinterfaces.activities.CheckInActivity;
import com.cancunsoftware.hotelbooking.userinterfaces.activities.PersonsListActivity;

import java.util.ArrayList;

import static com.cancunsoftware.hotelbooking.services.APIServices.getReservation;

public class GetReservationAsyncTask extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private int idConfirm;
    private String data;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private ProgressDialog dialog;

    public GetReservationAsyncTask(int idConfirm, String data, Context context) {
        this.idConfirm = idConfirm;
        this.data = data;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        getReservation(idConfirm, data);
        return null;
    }

    @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (UtilsClass.hotelReservation == null)
            ((Activity)context).runOnUiThread(() -> Toast.makeText(context,  context.getResources().getString(R.string.booking_not_found), Toast.LENGTH_LONG).show());
        else{
            ArrayList<Person> people = new ArrayList<>();
            int i = 0;
            while (i < UtilsClass.hotelReservation.getAdults()) {
                Person person = new Person();
                people.add(person);
                i++;
            }

            i = 0;
            while (i < UtilsClass.hotelReservation.getKids()) {
                Person person = new Person();
                people.add(person);
                i++;
            }

            UtilsClass.hotelReservation.setPersons(people);
            ((Activity)context).startActivity(new Intent(context, CheckInActivity.class));
            ((Activity)context).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
        dialog.dismiss();
    }
}
