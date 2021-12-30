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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.interfaces.ItemClickListener;
import com.cancunsoftware.hotelbooking.model.HotelActivity;
import com.cancunsoftware.hotelbooking.model.Room;
import com.cancunsoftware.hotelbooking.model.UtilsClass;
import com.cancunsoftware.hotelbooking.userinterfaces.activities.ActivitiesActivity;
import com.cancunsoftware.hotelbooking.userinterfaces.activities.ConfirmationActivity;
import com.cancunsoftware.hotelbooking.userinterfaces.activities.RoomDetailsScrollingActivity;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ActivityRecyclerViewAdapter extends RecyclerView.Adapter<ActivityRecyclerViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<HotelActivity> list;
    private ArrayList<HotelActivity> arrayList;
    private Context mContext;

    public ActivityRecyclerViewAdapter(Activity mContext, ArrayList<HotelActivity> list) {
        this.list = list;
        this.arrayList = new ArrayList<>(list);
        this.mContext = mContext;
    }

    public HotelActivity getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activity, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        HotelActivity hotelActivity = list.get(position);

        viewHolder.textViewTitle.setText(hotelActivity.getTitle());
        if (String.valueOf(hotelActivity.getPrice()).endsWith(".0"))
            viewHolder.textViewPrice.setText(String.valueOf(hotelActivity.getPrice()).replace(".0",""));
        else
            viewHolder.textViewPrice.setText(String.valueOf(hotelActivity.getPrice()));

        viewHolder.setItemClickListener((view, position1, isLongClick) -> {
            if (!isLongClick) {
                Intent intent = new Intent(mContext, ConfirmationActivity.class);
                HotelActivity activity = arrayList.get(position1);
                intent.putExtra("act", activity);
                mContext.startActivity(intent);
                ((Activity)mContext).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }

            /*viewHolder.buttonSeeMore.setOnClickListener(v -> {
                Toast.makeText(mContext, "ver mas", Toast.LENGTH_SHORT).show();
            });
            viewHolder.buttonBuy.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, ConfirmationActivity.class);
                int clickPosition = getAdapter
            });*/
        });
    }

    @Override
    public Filter getFilter() {
        return hotelactivityFilter;
    }

    private Filter hotelactivityFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<HotelActivity> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(arrayList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (HotelActivity hotelActivity : arrayList) {
                    if (hotelActivity.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(hotelActivity);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

        private TextView textViewTitle, textViewPrice;
        private ImageView imageView;
        private ItemClickListener itemClickListener;
        private MaterialButton buttonBuy, buttonSeeMore;

        public ViewHolder(View v) {
            super(v);
            textViewTitle = v.findViewById(R.id.textViewTitle);
            textViewPrice = v.findViewById(R.id.textViewPrice);
            imageView = v.findViewById(R.id.imageView);
            buttonBuy = v.findViewById(R.id.btnBuy);
            buttonSeeMore = v.findViewById(R.id.btnSeeMore);

            buttonBuy.setOnClickListener(this);
            buttonSeeMore.setOnClickListener(this);

            itemView.setTag(v);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
            return true;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }
}