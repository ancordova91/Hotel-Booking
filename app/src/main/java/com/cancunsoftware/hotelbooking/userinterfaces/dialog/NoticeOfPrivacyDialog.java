package com.cancunsoftware.hotelbooking.userinterfaces.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.task.GetReservationAsyncTask;
import com.cancunsoftware.hotelbooking.userinterfaces.activities.CheckInActivity;
import com.cancunsoftware.hotelbooking.userinterfaces.activities.PersonsListActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

public class NoticeOfPrivacyDialog extends Dialog {

    private Context context;

    public NoticeOfPrivacyDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_notice_of_privacy);

        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

        MaterialButton btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(view1 -> {
            context.startActivity(new Intent(context, PersonsListActivity.class));
            ((Activity) context).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            dismiss();
            ((Activity) context).finish();
        });

        MaterialCheckBox materialCheckBox = findViewById(R.id.checkBoxAccept);
        materialCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                materialCheckBox.setChecked(true);
                btnAccept.setEnabled(true);
            } else {
                materialCheckBox.setChecked(false);
                btnAccept.setEnabled(false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }
}