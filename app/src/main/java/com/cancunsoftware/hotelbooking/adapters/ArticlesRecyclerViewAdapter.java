package com.cancunsoftware.hotelbooking.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.model.Article;
import com.cancunsoftware.hotelbooking.model.Room;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticlesRecyclerViewAdapter extends RecyclerView.Adapter<ArticlesRecyclerViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<Article> list;
    private ArrayList<Article> arrayList;
    private Context mContext;

    public ArticlesRecyclerViewAdapter(Activity mContext, ArrayList<Article> list) {
        this.list = list;
        this.arrayList = new ArrayList<>(list);
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Article getItem(int position) {
        return list.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_article, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        Article article = list.get(position);
        viewHolder.textViewDescription.setText(article.getText());
        Picasso.get().load(article.getUrl()).into(viewHolder.imageViewImg);
        viewHolder.itemView.setTag(viewHolder);
    }

    @Override
    public Filter getFilter() {
        return articlesFilter;
    }

    private Filter articlesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Article> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(arrayList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Article article : arrayList) {
                    if (article.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(article);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewDescription;
        private ImageView imageViewImg;

        public ViewHolder(View v) {
            super(v);
            textViewDescription = v.findViewById(R.id.textViewDescription);
            imageViewImg = v.findViewById(R.id.imageViewImg);
            itemView.setTag(v);

        }
    }
}