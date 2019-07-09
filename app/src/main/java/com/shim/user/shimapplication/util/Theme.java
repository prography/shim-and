package com.shim.user.shimapplication.util;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Calendar;

public class Theme {
    static public void apply(String mode) {
        switch (mode) {
            case "day":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "night":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "night_owl":
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                AppCompatDelegate.setDefaultNightMode(hour >= 6 && hour < 18 ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "system":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }
}
