package com.cancunsoftware.hotelbooking.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.model.Room;
import com.cancunsoftware.hotelbooking.model.UtilsClass;
import com.cancunsoftware.hotelbooking.userinterfaces.activities.ImageFullViewActivity;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import static com.cancunsoftware.hotelbooking.model.UtilsClass.hotel;
import static com.cancunsoftware.hotelbooking.services.APIServices.getRoomsById;
import static com.cancunsoftware.hotelbooking.services.APIServices.getRoomsList;

public class GetRoomDetailsAsyncTask extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private int idRoom;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private CarouselView carouselView;
    private ProgressDialog dialog;
    private Room room;

    public GetRoomDetailsAsyncTask(int idRoom, Context context) {
        this.idRoom = idRoom;
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
        room = getRoomsById(idRoom);
        return null;
    }

    @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (room == null)
            ((Activity)context).runOnUiThread(() -> Toast.makeText(context,  context.getResources().getString(R.string.connection_failed), Toast.LENGTH_LONG).show());
        else{
            UtilsClass.room = room;
            carouselView = ((Activity) context).findViewById(R.id.carouselView);
            carouselView.setImageListener(imageListener);
            carouselView.setPageCount(room.getImagesUrl().size());
            carouselView.setImageClickListener((ImageClickListener) position -> {
                ((Activity)context).startActivity(new Intent(context, ImageFullViewActivity.class).putExtra("POSITION", position));
                ((Activity)context).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            });

            TextView textViewTitle = ((Activity)context).findViewById(R.id.textViewTitle);
            TextView textViewSubtitle = ((Activity)context).findViewById(R.id.textViewSubtitle);
            TextView textViewPrice = ((Activity)context).findViewById(R.id.textViewPrice);
            TextView textViewCapacity = ((Activity)context).findViewById(R.id.textViewCapacity);
            TextView textViewDescription = ((Activity)context).findViewById(R.id.textViewDescription);
            LinearLayout linearLayoutFacilities = ((Activity)context).findViewById(R.id.linearLayoutFacilities);

            textViewTitle.setText(room.getTitle());
            textViewSubtitle.setText(room.getSubtitle());
            textViewCapacity.setText("x" + room.getMaxPeople());
            if (String.valueOf(room.getPrice()).endsWith(".0"))
                textViewPrice.setText(String.valueOf(room.getPrice()).replace(".0",""));
            else
                textViewPrice.setText(String.valueOf(room.getPrice()));

            textViewDescription.setText(room.getDescription().replaceAll("\\<.*?>",""));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            params.setMargins(5, 5, 5, 5);
            for (int i = 0; i < room.getFacilities().size(); i++) {

                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(params);
                imageView.getLayoutParams().height = 64;
                imageView.getLayoutParams().width = 64;
                Picasso.get().load(room.getFacilities().get(i).getUrlImg()).into(imageView);
                linearLayoutFacilities.addView(imageView);
            }
        }
        dialog.dismiss();
    }

    ImageListener imageListener = (position, imageView) -> Picasso.get().load(room.getImagesUrl().get(position)).into(imageView);
}
