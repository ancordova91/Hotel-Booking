package com.cancunsoftware.hotelbooking.model;

import java.util.ArrayList;

public class UtilsClass {

    public static Hotel hotel;
    public static int lang;
    public static String currentFragment;
    public static Person person;
    public static HotelReservation hotelReservation;
    public static ArrayList<Room> roomsArrayList;
    public static ArrayList<HotelActivity> hotelActivityArrayList;
    public static Room room;

    public static Room findRoom(int idRoom){
        Room room;
        if (roomsArrayList != null){
            for (int i = 0; i < roomsArrayList.size(); i++) {
                if (roomsArrayList.get(i).getIdRoom() == idRoom){
                    room = roomsArrayList.get(i);
                    return room;
                }
            }
        }
        return null;
    }

    public static HotelActivity findActivity(int idActivity){
        HotelActivity hotelActivity;
        if (hotelActivityArrayList != null){
            for (int i = 0; i < hotelActivityArrayList.size(); i++) {
                if (hotelActivityArrayList.get(i).getId() == idActivity){
                    hotelActivity = hotelActivityArrayList.get(i);
                    return hotelActivity;
                }
            }
        }
        return null;
    }
}
