package com.cancunsoftware.hotelbooking.userinterfaces.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.model.Person;
import com.cancunsoftware.hotelbooking.model.UtilsClass;
import com.cancunsoftware.hotelbooking.task.GetPersonDataAsyncTask;
import com.cancunsoftware.hotelbooking.task.GetReservationAsyncTask;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class FormPersonActivity extends AppCompatActivity {

    private AlertDialog alert = null;
    private String mImagePath;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_person);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        AutoCompleteTextView spinnerDocType = findViewById(R.id.spinnerDocType);
        AutoCompleteTextView spinnerGenre = findViewById(R.id.spinnerGenre);
        TextInputEditText tIETIdNumber = findViewById(R.id.tIETIdNumber);
        TextInputEditText tIETFirstName = findViewById(R.id.tIETFirstName);
        TextInputEditText tIETSecondName = findViewById(R.id.tIETSecondName);
        TextInputEditText tIETFirstSurname = findViewById(R.id.tIETFirstSurname);
        TextInputEditText tIETSecondSurname = findViewById(R.id.tIETSecondSurname);
        TextInputEditText tIETBirthday = findViewById(R.id.tIETBirthday);
        TextInputEditText tIETNationality = findViewById(R.id.tIETNationality);
        TextInputEditText tIETEmail = findViewById(R.id.tIETEmail);
        CountryCodePicker ccpPhone = findViewById(R.id.ccpPhone);
        TextInputEditText tIETPhone = findViewById(R.id.tIETPhone);

        String[] docTypes = getResources().getStringArray(R.array.doctypes);
        spinnerDocType.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, docTypes));

        String[] genre = getResources().getStringArray(R.array.genre);
        spinnerGenre.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, genre));

        position = getIntent().getIntExtra("POSITION", -1);
        if(position != -1){
            if (position == 0){
                if (UtilsClass.hotelReservation.getEmail() != null && !UtilsClass.hotelReservation.getEmail().isEmpty())
                    tIETEmail.setText(UtilsClass.hotelReservation.getEmail());
                if (UtilsClass.hotelReservation.getPhone() != null && !UtilsClass.hotelReservation.getPhone().isEmpty())
                    tIETPhone.setText(UtilsClass.hotelReservation.getPhone());
            }
            Person person = UtilsClass.hotelReservation.getPersons().get(position);
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
            if (person.getId() != null && !person.getId().isEmpty())
                tIETIdNumber.setText(person.getId());
            if (person.getFirstname() != null && !person.getFirstname().isEmpty())
                tIETFirstName.setText(person.getFirstname());
            if (person.getLastname() != null && !person.getLastname().isEmpty())
                tIETSecondName.setText(person.getLastname());
            if (person.getFirstSurname() != null && !person.getFirstSurname().isEmpty())
                tIETFirstSurname.setText(person.getFirstSurname());
            if (person.getSecondSurname() != null && !person.getSecondSurname().isEmpty())
                tIETSecondSurname.setText(person.getSecondSurname());
            if (person.getBirthday() != null && !person.getBirthday().isEmpty())
                tIETBirthday.setText(person.getBirthday());
            if (person.getNationality() != null && !person.getNationality().isEmpty())
                tIETNationality.setText(person.getNationality());
            if (person.getEmail() != null && !person.getEmail().isEmpty())
                tIETEmail.setText(person.getEmail());
            if (person.getPhone() != null && !person.getPhone().isEmpty())
                tIETPhone.setText(person.getPhone());

        }

        tIETBirthday.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) {
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText(R.string.select_date);
                MaterialDatePicker<Long> picker = builder.build();
                picker.show(getSupportFragmentManager(), picker.toString());

                picker.addOnNegativeButtonClickListener(v1 -> picker.dismiss());

                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

                        String dateString = sdf.format(new Date(selection));
                        tIETBirthday.setText(dateString);
                    }
                });
            }
        });

        showOtions();

        MaterialButton btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(view1 -> {

            if (spinnerDocType.getText().toString().equals("")){
                Toast.makeText(FormPersonActivity.this, R.string.doc_type_message_error, Toast.LENGTH_SHORT).show();
            }
            else if (spinnerGenre.getText().toString().equals("")){
                Toast.makeText(FormPersonActivity.this, R.string.genre_message_error, Toast.LENGTH_SHORT).show();
            }
            else if (tIETIdNumber.getText().toString().isEmpty()){
                Toast.makeText(FormPersonActivity.this, R.string.complete_field, Toast.LENGTH_SHORT).show();
            }
            else if (tIETFirstName.getText().toString().isEmpty()){
                Toast.makeText(FormPersonActivity.this, R.string.complete_field, Toast.LENGTH_SHORT).show();
            }
            else if (tIETFirstSurname.getText().toString().isEmpty()){
                Toast.makeText(FormPersonActivity.this, R.string.complete_field, Toast.LENGTH_SHORT).show();
            }
            else if (tIETSecondSurname.getText().toString().isEmpty()){
                Toast.makeText(FormPersonActivity.this, R.string.complete_field, Toast.LENGTH_SHORT).show();
            }
            else if (tIETBirthday.getText().toString().isEmpty()){
                Toast.makeText(FormPersonActivity.this, R.string.complete_field, Toast.LENGTH_SHORT).show();
            }
            else if (!tIETBirthday.getText().toString().isEmpty() && !isDateFormatValid(tIETBirthday.getText().toString())){
                Toast.makeText(FormPersonActivity.this, R.string.wrong_date_format, Toast.LENGTH_SHORT).show();
            }
            else if (tIETNationality.getText().toString().isEmpty()){
                Toast.makeText(FormPersonActivity.this, R.string.complete_field, Toast.LENGTH_SHORT).show();
            }
            else if (UtilsClass.hotelReservation.getPersons().get(position).getDocImage().length == 0){
                Toast.makeText(FormPersonActivity.this, R.string.image_upload, Toast.LENGTH_SHORT).show();
            }
            else {
                if (spinnerDocType.getText().toString().equalsIgnoreCase(getString(R.string.passport))){
                    UtilsClass.hotelReservation.getPersons().get(position).setDocType("P");
                }
                else if (spinnerDocType.getText().toString().equalsIgnoreCase(getString(R.string.idcard))){
                    UtilsClass.hotelReservation.getPersons().get(position).setDocType("I");
                }

                if (spinnerGenre.getText().toString().equalsIgnoreCase(getString(R.string.female))){
                    UtilsClass.hotelReservation.getPersons().get(position).setGenre("F");
                }
                else if (spinnerGenre.getText().toString().equalsIgnoreCase(getString(R.string.male))){
                    UtilsClass.hotelReservation.getPersons().get(position).setGenre("M");
                }

                UtilsClass.hotelReservation.getPersons().get(position).setId(tIETIdNumber.getText().toString());
                UtilsClass.hotelReservation.getPersons().get(position).setFirstname(tIETFirstName.getText().toString());
                UtilsClass.hotelReservation.getPersons().get(position).setLastname(tIETSecondName.getText().toString());
                UtilsClass.hotelReservation.getPersons().get(position).setFirstSurname(tIETFirstSurname.getText().toString());
                UtilsClass.hotelReservation.getPersons().get(position).setSecondSurname(tIETSecondSurname.getText().toString());
                UtilsClass.hotelReservation.getPersons().get(position).setBirthday(tIETBirthday.getText().toString());
                UtilsClass.hotelReservation.getPersons().get(position).setNationality(tIETNationality.getText().toString());
                UtilsClass.hotelReservation.getPersons().get(position).setEmail(tIETEmail.getText().toString());
                if (!tIETPhone.getText().toString().isEmpty()){
                    UtilsClass.hotelReservation.getPersons().get(position).setPhone(ccpPhone.getSelectedCountryCode() + tIETPhone.getText().toString());
                }
                finish();
            }
        });
    }

    public void showOtions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setTitle(getString(R.string.alert_chooser_title));
        View dialogLayout = inflater.inflate(R.layout.alert_choose_image, null);
        builder.setView(dialogLayout);
        builder.setPositiveButton(getString(R.string.cancel), (dialogInterface, i) -> builder.create().cancel());
        alert = builder.show();
        ImageView imageViewCamera = dialogLayout.findViewById(R.id.imageViewCamera);
        imageViewCamera.setOnClickListener(v -> {
            try{
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
                }
                else{
                    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    Date date = new Date();
                    @SuppressLint("SimpleDateFormat")
                    String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
                    File imageFile = File.createTempFile(fileName, ".jpg", storageDir);
                    mImagePath = imageFile.getAbsolutePath();
                    Uri mImageUri = FileProvider.getUriForFile(FormPersonActivity.this, "com.cancunsoftware.hotelbooking.fileprovider", imageFile);
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, mImageUri), 0);
                    alert.dismiss();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
        ImageView imageViewGallery = dialogLayout.findViewById(R.id.imageViewGallery);
        imageViewGallery.setOnClickListener(v -> {
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                else {
                    startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 1);
                    alert.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form_person, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        if (item.getItemId() == R.id.action_pick_image) {
            showOtions();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SimpleDateFormat")
    private boolean isDateFormatValid(String toString) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(toString);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage;
        File image;
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK) {
                        Bitmap bmp = BitmapFactory.decodeFile(mImagePath);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] array = stream.toByteArray();
                        bmp.recycle();
                        File file = new File(mImagePath);
                        boolean deleted = file.delete();
                        new GetPersonDataAsyncTask(position, array, FormPersonActivity.this).execute();
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        selectedImage = data.getData();
                        image = new File(getPath(selectedImage));
                        byte[] array = FileToByteArray(image);
                        new GetPersonDataAsyncTask(position, array, FormPersonActivity.this).execute();
                    }
                    break;
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public byte[] FileToByteArray(File file){
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults){
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    Date date = new Date();
                    @SuppressLint("SimpleDateFormat")
                    String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
                    File imageFile = null;
                    try {
                        imageFile = File.createTempFile(fileName, ".jpg", storageDir);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mImagePath = Objects.requireNonNull(imageFile).getAbsolutePath();
                    Uri mImageUri = FileProvider.getUriForFile(FormPersonActivity.this, "com.cancunsoftware.hotelbooking.fileprovider", imageFile);
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, mImageUri), 0);
                    alert.dismiss();
                }
                else {
                    Toast.makeText(FormPersonActivity.this, R.string.camera_permission_message, Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 1);
                    alert.dismiss();
                }
                else {
                    Toast.makeText(FormPersonActivity.this, R.string.gallery_permission_message, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}