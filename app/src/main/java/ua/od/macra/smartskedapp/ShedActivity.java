package ua.od.macra.smartskedapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ua.od.macra.smartskedapp.models.Strings;
import ua.od.macra.smartskedapp.models.json.Break;
import ua.od.macra.smartskedapp.models.json.Day;
import ua.od.macra.smartskedapp.models.json.NoPair;
import ua.od.macra.smartskedapp.models.json.Pair;
import ua.od.macra.smartskedapp.models.list.ListEntry;


public class ShedActivity extends ActionBarActivity {

    public static final String LOG_TAG = ShedActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shed);

        ListView shedLayout = (ListView) findViewById(R.id.shedTable);
        String[] weekDays = getResources().getStringArray(R.array.weekdays);
        Intent intent = getIntent();
        ShedModelAdapter modelAdapter = new ShedModelAdapter(this);
        try {
            JSONArray daysJsonArray = new JSONArray(intent.getStringExtra(Strings.EXTRA_JSON));
            for (int i = 0; i < daysJsonArray.length(); i++) {
                ShedListAdapter listAdapter = new ShedListAdapter(this);
                Calendar c = Calendar.getInstance();
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                if (dayOfWeek < i + 1) {
                    c.roll(Calendar.DAY_OF_MONTH, 1);
                    while (!(i + 1 == c.get(Calendar.DAY_OF_WEEK) - 1)) {
                        c.roll(Calendar.DAY_OF_MONTH, 1);
                    }
                } else if (dayOfWeek > i + 1) {
                    c.roll(Calendar.DAY_OF_MONTH, -1);
                    while (!(i + 1 == c.get(Calendar.DAY_OF_WEEK) - 1)) {
                        c.roll(Calendar.DAY_OF_MONTH, -1);
                    }
                }

                JSONArray dayPairsArray = daysJsonArray.getJSONArray(i);
                for (int j = 0; j < dayPairsArray.length(); j++) {
                    JSONObject object = dayPairsArray.getJSONObject(j);
                    switch (object.length()) {
                        case 3: {
                            Log.d(LOG_TAG, "Creating" + object.toString());
                            listAdapter.add(new Break(object));
                            Log.d(LOG_TAG, "Created");
                            break;
                        }
                        case 4: {
                            Log.d(LOG_TAG, "Creating" + object.toString());
                            listAdapter.add(new NoPair(object));
                            Log.d(LOG_TAG, "Created");
                            break;
                        }
                        case 6: {
                            Log.d(LOG_TAG, "Creating" + object.toString());
                            listAdapter.add(new Pair(object));
                            Log.d(LOG_TAG, "Created");
                            break;
                        }
                    }
                }

                ListEntry entry = new ListEntry(
                        new Day(weekDays[i],
                                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(c.getTime())),
                        listAdapter);
                modelAdapter.add(entry);
            }
            shedLayout.setAdapter(modelAdapter);
        } catch (JSONException e) {
            Log.d(LOG_TAG, "Can't parse JSON");
        }

    }
}
