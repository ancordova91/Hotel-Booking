package com.cancunsoftware.hotelbooking.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class PreferencesLanguages {

    public static int getLanguage(Context context){
        SharedPreferences settings = context.getSharedPreferences("Preferences_HB", MODE_PRIVATE);
        String pref = settings.getString("LANG", "");
        int lang = 0;
        switch (pref) {
            case "en":
                lang = 2;
                break;
            case "fr":
                lang = 1;
                break;
            case "es":
                lang = 4;
                break;
        }
        return lang;
    }

    public static void setLanguage(Context context, String lang){
        SharedPreferences.Editor editor = context.getSharedPreferences("Preferences_HB", MODE_PRIVATE).edit();
        editor.putString("LANG", lang);
        editor.apply();
    }

    public static void checkLanguage(int lang, Context context) {
        final Configuration config = context.getResources().getConfiguration();
        Locale locale = null;
        switch (lang) {
            case 1:
                locale = new Locale("fr");
                break;
            case 2:
                locale = new Locale("en");
                break;
            case 4:
                locale = new Locale("es");
                break;
        }
        Locale.setDefault(locale);
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }
}
