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
import com.cancunsoftware.hotelbooking.adapters.ArticlesRecyclerViewAdapter;
import com.cancunsoftware.hotelbooking.adapters.RoomRecyclerViewAdapter;
import com.cancunsoftware.hotelbooking.task.GetRoomsAsyncTask;

public class RoomFragment extends Fragment {

    private SwipeRefreshLayout swipeLayout;
    private RecyclerView recyclerViewRoom;
    private RoomRecyclerViewAdapter roomRecyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_room, container, false);
        setHasOptionsMenu(true);
        swipeLayout = root.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(() -> {
            new GetRoomsAsyncTask(getContext()).execute();
        });
        new GetRoomsAsyncTask(getContext()).execute();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetRoomsAsyncTask(getContext()).execute();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menu_room, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        recyclerViewRoom = getActivity().findViewById(R.id.recyclerViewRoom);
        roomRecyclerViewAdapter = (RoomRecyclerViewAdapter) recyclerViewRoom.getAdapter();
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (recyclerViewRoom == null)
                    recyclerViewRoom = getActivity().findViewById(R.id.recyclerViewRoom);
                else if (roomRecyclerViewAdapter == null)
                    roomRecyclerViewAdapter = (RoomRecyclerViewAdapter) recyclerViewRoom.getAdapter();
                else {
                    roomRecyclerViewAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}