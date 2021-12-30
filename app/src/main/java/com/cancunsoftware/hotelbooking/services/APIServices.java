package com.cancunsoftware.hotelbooking.services;

import com.cancunsoftware.hotelbooking.R;
import com.cancunsoftware.hotelbooking.model.Article;
import com.cancunsoftware.hotelbooking.model.Facility;
import com.cancunsoftware.hotelbooking.model.Hotel;
import com.cancunsoftware.hotelbooking.model.HotelActivity;
import com.cancunsoftware.hotelbooking.model.HotelReservation;
import com.cancunsoftware.hotelbooking.model.Person;
import com.cancunsoftware.hotelbooking.model.Room;
import com.cancunsoftware.hotelbooking.model.SlideObject;
import com.cancunsoftware.hotelbooking.model.UtilsClass;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.cancunsoftware.hotelbooking.connection.ConnectionConfig.PmActivity;
import static com.cancunsoftware.hotelbooking.connection.ConnectionConfig.PmBookings;
import static com.cancunsoftware.hotelbooking.connection.ConnectionConfig.PmHotel;
import static com.cancunsoftware.hotelbooking.connection.ConnectionConfig.PmRoomById;
import static com.cancunsoftware.hotelbooking.connection.ConnectionConfig.PmRooms;
import static com.cancunsoftware.hotelbooking.connection.ConnectionConfig.apiHost;
import static com.cancunsoftware.hotelbooking.connection.ConnectionConfig.getClient;
import static com.cancunsoftware.hotelbooking.model.UtilsClass.hotel;
import static com.cancunsoftware.hotelbooking.model.UtilsClass.lang;
import static com.cancunsoftware.hotelbooking.utils.Tools.cleanText;

import android.graphics.drawable.Drawable;

public class APIServices {

    public static void getHotelInfo(){
        try {
            OkHttpClient client = getClient();
            Request request = new Request.Builder()
                    .url(apiHost + PmHotel + "?lang=" + lang)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            String responseXML = response.body().string();
            JSONObject jsonObject = new JSONObject(responseXML);

            if (jsonObject.length() > 0){
                hotel = new Hotel();
                hotel.setAddress(jsonObject.getString("address"));
                hotel.setPhone(jsonObject.getString("phone"));
                hotel.setHotelDescripcion(cleanText(jsonObject.getString("hotelDescripcion")));
                JSONArray jsonArraySlide = new JSONArray(jsonObject.getString("listslide"));
                for (int i = 0; i < jsonArraySlide.length(); i++) {
                    SlideObject slideObject = new SlideObject();
                    slideObject.setId(jsonArraySlide.getJSONObject(i).getInt("id"));
                    slideObject.setLegend(jsonArraySlide.getJSONObject(i).getString("legend"));
                    slideObject.setUrl(jsonArraySlide.getJSONObject(i).getString("url"));
                    hotel.getListslide().add(slideObject);
                }

                JSONArray jsonArrayArticles = new JSONArray(jsonObject.getString("listArticle"));
                for (int i = 0; i < jsonArrayArticles.length(); i++) {
                    Article articleObject = new Article();
                    articleObject.setId(jsonArrayArticles.getJSONObject(i).getInt("id"));
                    articleObject.setTitle(jsonArrayArticles.getJSONObject(i).getString("title"));
                    articleObject.setText(cleanText(jsonArrayArticles.getJSONObject(i).getString("text")));
                    articleObject.setUrl(jsonArrayArticles.getJSONObject(i).getString("url"));
                    hotel.getArticles().add(articleObject);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Room getRoomsById(int idRoom){
        Room room = null;
        try {
            OkHttpClient client = getClient();
            Request request = new Request.Builder()
                    .url(apiHost + PmRoomById + "/" + idRoom +"/"+ lang)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            String responseXML = response.body().string();

            JSONObject jsonObject = new JSONObject(responseXML);
            room = new Room();
            room.setIdRoom(jsonObject.getInt("id"));
            room.setTitle(jsonObject.getString("title"));
            room.setSubtitle(jsonObject.getString("subtitle"));
            room.setPrice(Float.parseFloat(jsonObject.getString("price")));
            room.setMaxPeople(jsonObject.getInt("maxPeople"));
            room.setDescription(jsonObject.getString("descripcion"));
            String[] urls = jsonObject.getString("img").split(",");
            room.getImagesUrl().addAll(Arrays.asList(urls));
            JSONArray jsonArray = jsonObject.getJSONArray("listFacility");
            if(jsonArray.length() > 0){
                for (int i = 0; i < jsonArray.length(); i++) {
                    Facility facility = new Facility();
                    facility.setId(jsonArray.getJSONObject(i).getInt("id_facility"));
                    facility.setDescription(jsonArray.getJSONObject(i).getString("descripcion"));
                    facility.setUrlImg(jsonArray.getJSONObject(i).getString("urlImg"));
                    room.getFacilities().add(facility);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return room;
    }

    public static void getRoomsList(){
        try {
            OkHttpClient client = getClient();
            Request request = new Request.Builder()
                    .url(apiHost + PmRooms + "?lang=" + lang)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            String responseXML = response.body().string();

            JSONArray jsonArrayRooms = new JSONArray(responseXML);
            if (jsonArrayRooms.length() > 0){
                UtilsClass.roomsArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArrayRooms.length(); i++) {
                    Room room = new Room();
                    room.setIdRoom(jsonArrayRooms.getJSONObject(i).getInt("id"));
                    room.setTitle(jsonArrayRooms.getJSONObject(i).getString("title"));
                    room.setSubtitle(jsonArrayRooms.getJSONObject(i).getString("subtitle"));
                    room.setPrice(Float.parseFloat(jsonArrayRooms.getJSONObject(i).getString("price")));
                    room.setMaxPeople(jsonArrayRooms.getJSONObject(i).getInt("maxPeople"));
                    room.setImageUrl(jsonArrayRooms.getJSONObject(i).getString("img"));
                    UtilsClass.roomsArrayList.add(room);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getActivitiesList(){
        try {
            OkHttpClient client = getClient();
            Request request = new Request.Builder()
                    .url(apiHost + PmActivity + "?lang=" + lang)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            String responseXML = response.body().string();

            JSONArray jsonArrayActivities = new JSONArray((responseXML));
            if (jsonArrayActivities.length() > 0) {
                UtilsClass.hotelActivityArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArrayActivities.length(); i++) {
                    HotelActivity hotelActivity = new HotelActivity();
                    hotelActivity.setId(jsonArrayActivities.getJSONObject(i).getInt("id"));
                    hotelActivity.setMaxChildren(jsonArrayActivities.getJSONObject(i).getInt("max_children"));
                    hotelActivity.setMaxAdults(jsonArrayActivities.getJSONObject(i).getInt("max_adult"));
                    hotelActivity.setMaxPeople(jsonArrayActivities.getJSONObject(i).getInt("max_people"));
                    hotelActivity.setTitle(jsonArrayActivities.getJSONObject(i).getString("title"));
                    hotelActivity.setDescription(jsonArrayActivities.getJSONObject(i).getString("descripcion"));
                    if (jsonArrayActivities.getJSONObject(i).getString("duration").contains("null"))
                        hotelActivity.setDuration("");
                    else hotelActivity.setDuration(jsonArrayActivities.getJSONObject(i).getString("duration"));
                    hotelActivity.setPrice((float) jsonArrayActivities.getJSONObject(i).getDouble("precio"));
                    //JSONArray jsonArrayImages = jsonArrayActivities.getJSONObject(i).getJSONArray("urlImage");
                    //if (jsonArrayImages.length() > 0) {
                        //for (int j = 0; j < jsonArrayImages.length(); j++) {
                            //hotelActivity.getPmActivityFile().add(jsonArrayImages.getJSONObject(j).getString("url"));
                        //}
                    hotelActivity.getPmActivityFile().add(String.valueOf(Picasso.get().load(R.drawable.xcaret)));
                    UtilsClass.hotelActivityArrayList.add(hotelActivity);
                }
            }
            /*OkHttpClient client = getClient();
            Request request = new Request.Builder()
                    .url(apiHost + PmActivity+ "?lang=" + lang)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            String responseXML = response.body().string();

            JSONArray jsonArrayActivities = new JSONArray(responseXML);
            if (jsonArrayActivities.length() > 0){
                UtilsClass.hotelActivityArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArrayActivities.length(); i++) {

                    HotelActivity hotelActivity = new HotelActivity();
                    hotelActivity.setId(jsonArrayActivities.getJSONObject(i).getInt("id"));
                    hotelActivity.setMaxChildren(jsonArrayActivities.getJSONObject(i).getInt("max_children"));
                    hotelActivity.setMaxAdults(jsonArrayActivities.getJSONObject(i).getInt("max_adult"));
                    hotelActivity.setMaxPeople(jsonArrayActivities.getJSONObject(i).getInt("max_people"));
                    hotelActivity.setTitle(jsonArrayActivities.getJSONObject(i).getString("title"));
                    hotelActivity.setDescription(jsonArrayActivities.getJSONObject(i).getString("descripcion"));
                    if (jsonArrayActivities.getJSONObject(i).getString("duration").contains("null"))
                        hotelActivity.setDuration(1);
                    else hotelActivity.setDuration((float) jsonArrayActivities.getJSONObject(i).getDouble("duration"));
                    hotelActivity.setPrice(Float.parseFloat(jsonArrayActivities.getJSONObject(i).getString("precio")));
                    JSONArray jsonArrayImages = jsonArrayActivities.getJSONObject(i).getJSONArray("urlImg");
                    if (jsonArrayImages.length() > 0){
                        for (int j = 0; j < jsonArrayImages.length(); j++) {
                            hotelActivity.getPmActivityFile().add(jsonArrayImages.getJSONObject(j).getString("url"));
                        }
                    }
                    UtilsClass.hotelActivityArrayList.add(hotelActivity);
                }
            }*/
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getReservation(int idConfirm, String data){
        try {
            OkHttpClient client = getClient();

            Request request = new Request.Builder()
                    .url(apiHost + PmBookings +"/"+ idConfirm +"/"+ data)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            String responseXML = response.body().string();

            Gson gson = new Gson();
            UtilsClass.hotelReservation = gson.fromJson(responseXML, HotelReservation.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Person getPersonData(byte[] data){
        Person person = null;
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file","image.jpeg", RequestBody.create(MediaType.parse("application/octet-stream"),  data))
                    .build();
            Request request = new Request.Builder()
                    .url("https://app.nanonets.com/api/v2/OCR/Model/627ff527-8689-486b-baad-707134a41f47/LabelFile/")
                    .method("POST", body)
                    .addHeader("Authorization", "Basic aWJJNEJIZkNpR0draE5rOHpNNDFRVDFwS3ZqYUxKMFA6")
                    .build();
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();

            JSONObject jsonObject = new JSONObject(responseString);
            if (jsonObject.getString("message").equalsIgnoreCase("Success")){
                JSONArray jsonArrayResult = jsonObject.getJSONArray("result");
                JSONObject object = jsonArrayResult.getJSONObject(0);
                JSONArray jsonArrayPrediction = object.getJSONArray("prediction");
                person = new Person();
                for (int i = 0; i < jsonArrayPrediction.length(); i++) {
                    String label = jsonArrayPrediction.getJSONObject(i).getString("label");
                    switch (label){
                        case "Passport_Number":
                            person.setDocType("P");
                            person.setId(jsonArrayPrediction.getJSONObject(i).getString("ocr_text"));
                            break;
                        case "First_Name":
                            String name = jsonArrayPrediction.getJSONObject(i).getString("ocr_text");
                            String[] arrayName = name.split(" ");
                            person.setFirstname(arrayName[0]);
                            for (int j = 1; j < arrayName.length; j++) {
                                if(j == 1)
                                    person.setLastname(arrayName[j]);
                                else
                                    person.setLastname(person.getLastname() +" "+ arrayName[j]);
                            }
                            break;
                        case "Surname":
                            String surname = jsonArrayPrediction.getJSONObject(i).getString("ocr_text");
                            String[] arraySurName = surname.split(" ");
                            person.setFirstSurname(arraySurName[0]);
                            for (int j = 1; j < arraySurName.length; j++) {
                                if(j == 1)
                                    person.setSecondSurname(arraySurName[j]);
                                else
                                    person.setSecondSurname(person.getSecondSurname() +" "+ arraySurName[j]);
                            }
                            break;
                        case "Nationality":
                            person.setNationality(jsonArrayPrediction.getJSONObject(i).getString("ocr_text"));
                            break;
                        case "Date_of_Birth":
                            String[] nacArray = jsonArrayPrediction.getJSONObject(i).getString("ocr_text").split(" ");
                            person.setBirthday(nacArray[2]+"-"+nacArray[1]+"-"+nacArray[0]);
                            break;
                        case "Sex":
                            if (jsonArrayPrediction.getJSONObject(i).getString("ocr_text").equalsIgnoreCase("M"))
                                person.setGenre("M");
                            else if(jsonArrayPrediction.getJSONObject(i).getString("ocr_text").equalsIgnoreCase("F"))
                                person.setGenre("F");
                            break;

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return person;
    }
}
