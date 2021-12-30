package com.cancunsoftware.hotelbooking.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.adapters.RoomRecyclerViewAdapter;
import com.cancunsoftware.hotelbooking.model.UtilsClass;

import static com.cancunsoftware.hotelbooking.services.APIServices.getRoomsList;

public class GetRoomsAsyncTask extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private SwipeRefreshLayout swipeLayout;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView recyclerViewRooms;

    public GetRoomsAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        swipeLayout = ((Activity)context).findViewById(R.id.swipe_container);
        if (swipeLayout != null){
            swipeLayout.setColorSchemeResources(R.color.accent);
            swipeLayout.post(() -> swipeLayout.setRefreshing(true));
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        getRoomsList();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (UtilsClass.roomsArrayList == null)
            ((Activity)context).runOnUiThread(() -> Toast.makeText(context,  context.getResources().getString(R.string.connection_failed), Toast.LENGTH_LONG).show());
        else{
            recyclerViewRooms = ((Activity)context).findViewById(R.id.recyclerViewRoom);
            recyclerViewRooms.setHasFixedSize(true);
            recyclerViewRooms.setLayoutManager(new LinearLayoutManager(context));
            RoomRecyclerViewAdapter adapter = new RoomRecyclerViewAdapter((Activity) context, UtilsClass.roomsArrayList);
            recyclerViewRooms.setAdapter(adapter);
            recyclerViewRooms.setVisibility(View.VISIBLE);
        }
        if (swipeLayout != null) {
            Handler handler = new Handler();
            handler.postDelayed(() -> swipeLayout.setRefreshing(false), 1000);
        }
    }
}
