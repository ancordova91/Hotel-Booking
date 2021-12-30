package com.cancunsoftware.hotelbooking.model;

import java.util.ArrayList;

public class Hotel {

    private String address;
    private String phone;
    private String hotelDescripcion;
    private ArrayList<SlideObject> listslide = new ArrayList<>();
    private ArrayList<Article> articles = new ArrayList<>();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<SlideObject> getListslide() {
        return listslide;
    }

    public void setListslide(ArrayList<SlideObject> listslide) {
        this.listslide = listslide;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
    public Hotel() {
    }

    public String getHotelDescripcion() {
        return hotelDescripcion;
    }

    public void setHotelDescripcion(String hotelDescripcion) {
        this.hotelDescripcion = hotelDescripcion;
    }
}
