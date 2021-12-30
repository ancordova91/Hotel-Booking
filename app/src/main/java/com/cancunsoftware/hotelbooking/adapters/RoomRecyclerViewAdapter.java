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

import androidx.recyclerview.widget.RecyclerView;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.interfaces.ItemClickListener;
import com.cancunsoftware.hotelbooking.model.Room;
import com.cancunsoftware.hotelbooking.userinterfaces.activities.RoomDetailsScrollingActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RoomRecyclerViewAdapter extends RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<Room> list;
    private ArrayList<Room> arrayList;
    private Context mContext;

    public RoomRecyclerViewAdapter(Activity mContext, ArrayList<Room> list) {
        this.list = list;
        this.arrayList = new ArrayList<>(list);
        this.mContext = mContext;
    }

    public Room getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_room, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        Room room = list.get(position);

        viewHolder.textViewTitle.setText(room.getTitle());
        viewHolder.textViewSubtitle.setText(room.getSubtitle());
        if (String.valueOf(room.getPrice()).endsWith(".0"))
            viewHolder.textViewPrice.setText(String.valueOf(room.getPrice()).replace(".0",""));
        else
            viewHolder.textViewPrice.setText(String.valueOf(room.getPrice()));

        Picasso.get().load(room.getImageUrl()).into(viewHolder.imageView);
        viewHolder.setItemClickListener((view, position1, isLongClick) -> {
            if (isLongClick) {

            }
            else {
                (mContext).startActivity(new Intent(mContext, RoomDetailsScrollingActivity.class).putExtra("IDROOM", room.getIdRoom()).putExtra("TITLE",room.getTitle()));
                ((Activity)mContext).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
    }

    @Override
    public Filter getFilter() {
        return roomFilter;
    }

    private Filter roomFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Room> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(arrayList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Room room : arrayList) {
                    if (room.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(room);
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

        private TextView textViewTitle, textViewSubtitle, textViewPrice;
        private ImageView imageView;
        private ItemClickListener itemClickListener;

        public ViewHolder(View v) {
            super(v);
            textViewTitle = v.findViewById(R.id.textViewTitle);
            textViewSubtitle = v.findViewById(R.id.textViewSubtitle);
            textViewPrice = v.findViewById(R.id.textViewPrice);
            imageView = v.findViewById(R.id.imageView);

            itemView.setTag(v);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getPosition(), true);
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