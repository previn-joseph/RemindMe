package cmpsci211.remindme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<DataEntry> events = new ArrayList<>();

    ArrayAdapter<DataEntry> myArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.list);

        myArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, events);

        listView.setAdapter(myArrayAdapter);
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MainActivity.this, Creation.class);

                DataEntry d = events.get(position);

                int mYear = d.getDateToRemind().get(Calendar.YEAR);
                int mMonth = d.getDateToRemind().get(Calendar.MONTH);
                int mDay = d.getDateToRemind().get(Calendar.DAY_OF_MONTH);
                int mHour = d.getDateToRemind().get(Calendar.HOUR_OF_DAY);
                int mMinute = d.getDateToRemind().get(Calendar.MINUTE);

                String name = d.getName();
                String desc = d.getDescription();

                if(name == "No Event Name")
                    name = "";
                if(desc == "No Event Description")
                    desc = "";


                intent.putExtra("isEditing", true);
                intent.putExtra("name", name);
                intent.putExtra("desc", desc);
                intent.putExtra("year", mYear);
                intent.putExtra("month", mMonth);
                intent.putExtra("day", mDay);
                intent.putExtra("hour", mHour);
                intent.putExtra("minute", mMinute);
                intent.putExtra("am", d.isAM());
                intent.putExtra("index", position);

                int requestCode = 2; // Or some number you choose
                startActivityForResult(intent, requestCode);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*events.add(new DataEntry("Event " + (events.size() + 1), "A test event", new Date(), new Date()));
                myArrayAdapter.notifyDataSetChanged();*/

                Intent intent = new Intent(MainActivity.this, Creation.class);
                intent.putExtra("isEditing", false);
                int requestCode = 1; // Or some number you choose
                startActivityForResult(intent, requestCode);

            }
        });

    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        // Collect data from the intent and use it

        if(resultCode == RESULT_OK && requestCode == 1) {

            String name = data.getStringExtra("name");
            String desc = data.getStringExtra("desc");
            int mYear = data.getIntExtra("year", 1);
            int mMonth = data.getIntExtra("month", 1);
            int mDay = data.getIntExtra("day", 1);
            int mHour = data.getIntExtra("hour", 1);
            int mMinute = data.getIntExtra("minute", 1);
            boolean isAM = data.getBooleanExtra("am", true);

            if(name.trim().equals(""))
                name = "No Event Name";
            if(desc.trim().equals(""))
                desc = "No Event Description";

            Calendar dateCreated = Calendar.getInstance();
            Calendar timeToRemind = Calendar.getInstance();
            timeToRemind.set(Calendar.YEAR, mYear);
            timeToRemind.set(Calendar.MONTH, mMonth);
            timeToRemind.set(Calendar.DAY_OF_MONTH, mDay);
            timeToRemind.set(Calendar.HOUR_OF_DAY, mHour);
            timeToRemind.set(Calendar.MINUTE, mMinute);
            timeToRemind.set(Calendar.SECOND, 0);

            DataEntry e = new DataEntry(name, desc, dateCreated, timeToRemind, isAM);

            events.add(e);
            myArrayAdapter.notifyDataSetChanged();

        }else if(resultCode == RESULT_OK && requestCode == 2){

            String name = data.getStringExtra("name");
            String desc = data.getStringExtra("desc");
            int mYear = data.getIntExtra("year", 1);
            int mMonth = data.getIntExtra("month", 1);
            int mDay = data.getIntExtra("day", 1);
            int mHour = data.getIntExtra("hour", 1);
            int mMinute = data.getIntExtra("minute", 1);
            boolean isAM = data.getBooleanExtra("am", true);
            int index = data.getIntExtra("index", -1);

            if(name.trim().equals(""))
                name = "No Event Name";
            if(desc.trim().equals(""))
                desc = "No Event Description";

            Calendar dateCreated = Calendar.getInstance();
            Calendar timeToRemind = Calendar.getInstance();
            timeToRemind.set(Calendar.YEAR, mYear);
            timeToRemind.set(Calendar.MONTH, mMonth);
            timeToRemind.set(Calendar.DAY_OF_MONTH, mDay);
            timeToRemind.set(Calendar.HOUR_OF_DAY, mHour);
            timeToRemind.set(Calendar.MINUTE, mMinute);
            timeToRemind.set(Calendar.SECOND, 0);

            DataEntry e = new DataEntry(name, desc, dateCreated, timeToRemind, isAM);

            events.remove(index);
            events.add(index, e);
            myArrayAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
