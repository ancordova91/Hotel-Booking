package com.cancunsoftware.hotelbooking.model;

import java.io.Serializable;
import java.util.ArrayList;

public class HotelActivity implements Serializable {
    private int id;
    private int lang;
    private int maxChildren;
    private int maxAdults;
    private int maxPeople;
    private String title;
    private String subtitle;
    private String alias;
    private String description;
    private String duration;
    private String durationUnit;
    private float price;
    private double latitude;
    private double longitude;
    private int home;
    private int checked;
    private int rank;
    private String langNavigation;
    private ArrayList<String> pmActivityFile = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public int getMaxChildren() {
        return maxChildren;
    }

    public void setMaxChildren(int maxChildren) {
        this.maxChildren = maxChildren;
    }

    public int getMaxAdults() {
        return maxAdults;
    }

    public void setMaxAdults(int maxAdults) {
        this.maxAdults = maxAdults;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getLangNavigation() {
        return langNavigation;
    }

    public void setLangNavigation(String langNavigation) {
        this.langNavigation = langNavigation;
    }

    public ArrayList<String> getPmActivityFile() {
        return pmActivityFile;
    }

    public void setPmActivityFile(ArrayList<String> pmActivityFile) {
        this.pmActivityFile = pmActivityFile;
    }

    public HotelActivity() {
    }
}
