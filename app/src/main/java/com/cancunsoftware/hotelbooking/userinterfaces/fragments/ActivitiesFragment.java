package com.cancunsoftware.hotelbooking.userinterfaces.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.adapters.ActivityRecyclerViewAdapter;
import com.cancunsoftware.hotelbooking.adapters.RoomRecyclerViewAdapter;
import com.cancunsoftware.hotelbooking.task.GetActivitiesAsyncTask;
import com.cancunsoftware.hotelbooking.task.GetRoomsAsyncTask;

public class ActivitiesFragment extends Fragment {

    private SwipeRefreshLayout swipeLayout;
    private RecyclerView recyclerViewActivities;
    private ActivityRecyclerViewAdapter activityRecyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_activities, container, false);
        setHasOptionsMenu(true);
        swipeLayout = root.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(() -> {
            new GetActivitiesAsyncTask(getContext()).execute();
        });
        new GetActivitiesAsyncTask(getContext()).execute();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetActivitiesAsyncTask(getContext()).execute();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menu_article, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        recyclerViewActivities = getActivity().findViewById(R.id.recyclerViewActivities);
        activityRecyclerViewAdapter = (ActivityRecyclerViewAdapter) recyclerViewActivities.getAdapter();
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (recyclerViewActivities == null)
                    recyclerViewActivities = getActivity().findViewById(R.id.recyclerViewActivities);
                else if (activityRecyclerViewAdapter == null)
                    activityRecyclerViewAdapter = (ActivityRecyclerViewAdapter) recyclerViewActivities.getAdapter();
                else {
                    activityRecyclerViewAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}