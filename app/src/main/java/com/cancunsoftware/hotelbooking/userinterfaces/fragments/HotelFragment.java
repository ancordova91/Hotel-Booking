package com.cancunsoftware.hotelbooking.userinterfaces.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.adapters.ArticlesRecyclerViewAdapter;
import com.cancunsoftware.hotelbooking.task.GetHotelInfoAsyncTask;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class HotelFragment extends Fragment {

    private RecyclerView recyclerViewArticles;
    private ArticlesRecyclerViewAdapter articlesRecyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_hotel, container, false);
        new GetHotelInfoAsyncTask(getContext()).execute();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetHotelInfoAsyncTask(getContext()).execute();
    }
}