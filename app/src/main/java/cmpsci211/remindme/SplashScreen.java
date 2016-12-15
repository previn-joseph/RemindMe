package cmpsci211.remindme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class SplashScreen extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_main);

        readPrefs(this);
        savePrefs(this);

        startActivity(new Intent(SplashScreen.this, MainActivity.class));
    }

    public static void readPrefs(Context c){

        MainActivity.events = new ArrayList<>();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);

        int size = preferences.getInt("size", -1);

        for(int i = 0; i < size; i++){

            String name = preferences.getString("name" + i, "");
            String desc = preferences.getString("desc" + i, "");
            int mYear = preferences.getInt("year" + i, 1);
            int mMonth = preferences.getInt("month" + i, 1);
            int mDay = preferences.getInt("day" + i, 1);
            int mHour = preferences.getInt("hour" + i, 1);
            int mMinute = preferences.getInt("minute" + i, 1);
            boolean isAM = preferences.getBoolean("am" + i, true);

            Calendar dateCreated = Calendar.getInstance();
            Calendar timeToRemind = Calendar.getInstance();
            timeToRemind.set(Calendar.YEAR, mYear);
            timeToRemind.set(Calendar.MONTH, mMonth);
            timeToRemind.set(Calendar.DAY_OF_MONTH, mDay);
            timeToRemind.set(Calendar.HOUR_OF_DAY, mHour);
            timeToRemind.set(Calendar.MINUTE, mMinute);
            timeToRemind.set(Calendar.SECOND, 0);

            DataEntry e = new DataEntry(name, desc, dateCreated, timeToRemind, isAM);

            MainActivity.events.add(e);

        }

    }

    public static void savePrefs(Context c){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = preferences.edit();

        int id = 0;

        for(DataEntry d : MainActivity.events){

            int mYear = d.getDateToRemind().get(Calendar.YEAR);
            int mMonth = d.getDateToRemind().get(Calendar.MONTH);
            int mDay = d.getDateToRemind().get(Calendar.DAY_OF_MONTH);
            int mHour = d.getDateToRemind().get(Calendar.HOUR_OF_DAY);
            int mMinute = d.getDateToRemind().get(Calendar.MINUTE);
            boolean isAm = d.isAM();

            String name = d.getName();
            String desc = d.getDescription();

            editor.putString("name" + id, name);
            editor.putString("desc" + id, desc);
            editor.putInt("year" + id, mYear);
            editor.putInt("month" + id, mMonth);
            editor.putInt("day" + id, mDay);
            editor.putInt("hour" + id, mHour);
            editor.putInt("minute" + id, mMinute);
            editor.putBoolean("am" + id, isAm);

            id++;

        }

        editor.putInt("size", id);

        editor.apply();

    }

}
