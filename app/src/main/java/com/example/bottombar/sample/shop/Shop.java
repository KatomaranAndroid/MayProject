package com.example.bottombar.sample.shop;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.bottombar.sample.App;
import com.example.bottombar.sample.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yarolegovich on 07.03.2017.
 */

public class Shop {

    private static final String STORAGE = "shop";

    public static Shop get() {
        return new Shop();
    }

    private SharedPreferences storage;

    private Shop() {
        storage = App.getInstance().getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }

    public List<Item> getData() {
        return Arrays.asList(
                new Item(1, "Everyday Candle Small", "$12.00 USD", R.drawable.shop1, "10.997724", "76.950927"),
                new Item(2, "Small Porcel Board", "$50.00 USD", R.drawable.shop2, "10.999009", "76.971119"),
                new Item(3, "Favourite Board Bowl", "$265.00 USD", R.drawable.shop3, "10.996713", "76.972707"),
                new Item(4, "Earthenware Bowl Small", "$18.00 USD", R.drawable.shop4, "11.002252", "76.980904"),
                new Item(5, "Porcelain Dessert Board ", "$36.00 USD", R.drawable.shop5, "10.994038", "76.957386"));
    }

    public boolean isRated(int itemId) {
        return storage.getBoolean(String.valueOf(itemId), false);
    }

    public void setRated(int itemId, boolean isRated) {
        storage.edit().putBoolean(String.valueOf(itemId), isRated).apply();
    }
}
