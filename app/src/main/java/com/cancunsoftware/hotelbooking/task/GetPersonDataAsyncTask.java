package com.cancunsoftware.hotelbooking.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.model.Person;
import com.cancunsoftware.hotelbooking.model.UtilsClass;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import static com.cancunsoftware.hotelbooking.services.APIServices.getPersonData;

public class GetPersonDataAsyncTask extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Person person;
    private int pos;
    private byte[] data;
    private Context context;
    private ProgressDialog dialog;

    public GetPersonDataAsyncTask(int pos, byte[] data, Context context) {
        this.pos = pos;
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
        person = getPersonData(data);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (person != null){
            person.setImage(data);
            UtilsClass.hotelReservation.getPersons().set(pos, person);
            TextInputEditText tIETIdNumber = ((Activity)context).findViewById(R.id.tIETIdNumber);
            tIETIdNumber.setText(person.getId());
            TextInputEditText tIETFirstName = ((Activity)context).findViewById(R.id.tIETFirstName);
            tIETFirstName.setText(person.getFirstname());
            TextInputEditText tIETSecondName = ((Activity)context).findViewById(R.id.tIETSecondName);
            tIETSecondName.setText(person.getLastname());
            TextInputEditText tIETFirstSurname = ((Activity)context).findViewById(R.id.tIETFirstSurname);
            tIETFirstSurname.setText(person.getFirstSurname());
            TextInputEditText tIETSecondSurname = ((Activity)context).findViewById(R.id.tIETSecondSurname);
            tIETSecondSurname.setText(person.getSecondSurname());
            TextInputEditText tIETBirthday = ((Activity)context).findViewById(R.id.tIETBirthday);
            tIETBirthday.setText(person.getBirthday());
            TextInputEditText tIETNationality = ((Activity)context).findViewById(R.id.tIETNationality);
            tIETNationality.setText(person.getNationality());

            AutoCompleteTextView spinnerDocType = ((Activity)context).findViewById(R.id.spinnerDocType);
            AutoCompleteTextView spinnerGenre = ((Activity)context).findViewById(R.id.spinnerGenre);

            if (person.getDocType() != null && !person.getDocType().isEmpty()) {
                if (person.getDocType().equalsIgnoreCase("I"))
                    spinnerDocType.setText(R.string.idcard);
                if (person.getDocType().equalsIgnoreCase("P"))
                    spinnerDocType.setText(R.string.passport);
            }
            if (person.getGenre() != null && !person.getGenre().isEmpty()) {
                if (person.getGenre().equalsIgnoreCase("F"))
                    spinnerGenre.setText(R.string.female);
                if (person.getGenre().equalsIgnoreCase("M"))
                    spinnerGenre.setText(R.string.male);
            }
        }
        ((Activity)context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        dialog.dismiss();
    }
}
