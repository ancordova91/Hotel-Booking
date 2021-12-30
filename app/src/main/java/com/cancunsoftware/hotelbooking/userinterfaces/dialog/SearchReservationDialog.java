package com.cancunsoftware.hotelbooking.userinterfaces.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.task.GetReservationAsyncTask;
import com.google.android.material.button.MaterialButton;

public class SearchReservationDialog extends Dialog {

    private Context context;

    public SearchReservationDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search_reservation);

        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

        EditText editTextConfirm = findViewById(R.id.tIETConfirm);
        EditText editTextData = findViewById(R.id.tIETData);

        MaterialButton btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(view1 -> {
            if (editTextConfirm.getText().toString().isEmpty() || editTextData.getText().toString().isEmpty())
                Toast.makeText(context, R.string.enter_data_requested, Toast.LENGTH_SHORT).show();
            else {
                new GetReservationAsyncTask(Integer.parseInt(editTextConfirm.getText().toString()), editTextData.getText().toString(), context).execute();
                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }
}