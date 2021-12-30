package com.cancunsoftware.hotelbooking.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.adapters.ArticlesRecyclerViewAdapter;
import com.cancunsoftware.hotelbooking.model.Article;
import com.cancunsoftware.hotelbooking.model.Hotel;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import static com.cancunsoftware.hotelbooking.model.UtilsClass.hotel;
import static com.cancunsoftware.hotelbooking.services.APIServices.getHotelInfo;

public class GetHotelInfoAsyncTask extends AsyncTask <Void, Void, Void> {

    private int lang;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private CarouselView carouselView;
    private ProgressDialog dialog;

    public GetHotelInfoAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        getHotelInfo();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (hotel != null){
            carouselView = ((Activity) context).findViewById(R.id.carouselView);
            carouselView.setImageListener(imageListener);
            carouselView.setPageCount(hotel.getListslide().size());
        }
        dialog.dismiss();
    }
    ImageListener imageListener = (position, imageView) -> Picasso.get().load(hotel.getListslide().get(position).getUrl()).into(imageView);
}
