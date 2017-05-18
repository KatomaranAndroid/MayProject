package com.example.bottombar.sample.shop;

/**
 * Created by yarolegovich on 07.03.2017.
 */

public class Item {

    private final int id;
    private final String name;
    private final String price;
    private final int image;
    private final String lat;
    private final String lon;


    public Item(int id, String name, String price, int image, String lat, String lon) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.lat = lat;
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
