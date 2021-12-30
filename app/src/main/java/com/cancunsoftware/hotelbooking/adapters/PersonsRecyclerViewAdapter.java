package com.cancunsoftware.hotelbooking.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.interfaces.ItemClickListener;
import com.cancunsoftware.hotelbooking.model.Person;
import com.cancunsoftware.hotelbooking.model.UtilsClass;
import com.cancunsoftware.hotelbooking.userinterfaces.activities.FormPersonActivity;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class PersonsRecyclerViewAdapter extends RecyclerView.Adapter<PersonsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Person> list;
    private Context mContext;
    private int countAdults;
    private int countKids;

    public PersonsRecyclerViewAdapter(Activity mContext, ArrayList<Person> list) {
        this.list = list;
        this.mContext = mContext;
    }

    public Person getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_person, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        Person person = list.get(position);
        if (position == 0){
            viewHolder.textViewMain.setVisibility(View.VISIBLE);
            viewHolder.textViewName.setText(UtilsClass.hotelReservation.getFirstname() + " " + UtilsClass.hotelReservation.getLastname());
        }

        if (countAdults < UtilsClass.hotelReservation.getAdults()){
            viewHolder.textViewAge.setText(mContext.getString(R.string.adult) + " " + (countAdults + 1));
            countAdults++;
        }
        else if (countKids < UtilsClass.hotelReservation.getKids()){
            viewHolder.textViewAge.setText(mContext.getString(R.string.kid) + " " + (countKids + 1));
            countKids++;
        }

        if (person.getId() != null && !person.getId().isEmpty()){
            viewHolder.materialCheckBoxVerified.setChecked(true);
            viewHolder.materialCheckBoxVerified.setText(mContext.getString(R.string.verified));
        }

        if (person.getFirstname() != null && !person.getFirstname().isEmpty())
            viewHolder.textViewName.setText(person.getFirstname());
        if (person.getLastname() != null && !person.getLastname().isEmpty())
            viewHolder.textViewName.setText(viewHolder.textViewName.getText() + " " + person.getLastname());
        if (person.getFirstSurname() != null && !person.getFirstSurname().isEmpty())
            viewHolder.textViewName.setText(viewHolder.textViewName.getText() + " " + person.getFirstSurname());
        if (person.getSecondSurname() != null && !person.getSecondSurname().isEmpty())
            viewHolder.textViewName.setText(viewHolder.textViewName.getText() + " " + person.getSecondSurname());
        if (person.getDocType() != null && !person.getDocType().isEmpty() && person.getDocType().equalsIgnoreCase("P"))
            viewHolder.textViewIdNumber.setText(mContext.getString(R.string.passport) + ": " + person.getId());
        else if (person.getDocType() != null && !person.getDocType().isEmpty() && person.getDocType().equalsIgnoreCase("I"))
            viewHolder.textViewIdNumber.setText(mContext.getString(R.string.idcard) + ": " + person.getId());
        if (person.getGenre() != null && !person.getGenre().isEmpty())
            viewHolder.textViewGenre.setText(mContext.getString(R.string.genre) + ": " + person.getGenre());
        if (person.getId() != null && person.getId().isEmpty())
            viewHolder.imageViewEdit.setVisibility(View.GONE);
        else
            viewHolder.imageViewEdit.setVisibility(View.VISIBLE);

        viewHolder.setItemClickListener((ItemClickListener) (view, position1, isLongClick) -> {
            ((Activity)mContext).startActivity(new Intent(mContext, FormPersonActivity.class).putExtra("POSITION", position1));
            ((Activity)mContext).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textViewName, textViewIdNumber, textViewAge, textViewGenre, textViewMain;
        private ImageView imageViewEdit;
        private MaterialCheckBox materialCheckBoxVerified;
        private ItemClickListener itemClickListener;

        public ViewHolder(View v) {
            super(v);
            textViewMain = v.findViewById(R.id.textViewMain);
            textViewName = v.findViewById(R.id.textViewName);
            textViewIdNumber = v.findViewById(R.id.textViewId);
            textViewAge = v.findViewById(R.id.textViewAge);
            textViewGenre = v.findViewById(R.id.textViewGenre);
            imageViewEdit = v.findViewById(R.id.imageViewEdit);
            materialCheckBoxVerified = v.findViewById(R.id.materialCheckBoxVerified);

            itemView.setTag(v);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getPosition(), false);
        }
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }
}