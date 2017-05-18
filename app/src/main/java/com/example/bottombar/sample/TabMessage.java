package com.example.bottombar.sample;

/**
 * Created by iiro on 7.6.2016.
 */
public class TabMessage {
    public static String get(int menuItemId, boolean isReselection) {
        String message = "Content for ";

        switch (menuItemId) {
            case R.id.tab_favorites:
                message += "Home";
                break;
            case R.id.tab_nearby:
                message += "Mine";
                break;
            case R.id.tab_friends:
                message += "Account";
                break;
        }

        if (isReselection) {
            message += " WAS RESELECTED! YAY!";
        }

        return message;
    }
}
