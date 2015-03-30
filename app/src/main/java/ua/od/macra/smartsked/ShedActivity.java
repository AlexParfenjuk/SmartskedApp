package ua.od.macra.smartsked;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ua.od.macra.smartsked.adapter.ShedModelAdapter;
import ua.od.macra.smartsked.models.Strings;
import ua.od.macra.smartsked.models.json.Break;
import ua.od.macra.smartsked.models.json.NoPair;
import ua.od.macra.smartsked.models.json.Pair;
import ua.od.macra.smartsked.models.json.ShedTask;
import ua.od.macra.smartsked.models.list.ListEntry;


public class ShedActivity extends Activity {

    public static final String LOG_TAG = ShedActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shed);
        Intent intent = getIntent();

        ListView shedLayout = (ListView) findViewById(R.id.shedTable);
        LinearLayout headerView = new LinearLayout(this);
        getLayoutInflater().inflate(R.layout.ssked_list_header, headerView);
        String headerText = getResources().getString(
                R.string.list_header_group_name) + " " +
                intent.getStringExtra(Strings.EXTRA_GROUP_NAME);
        ((TextView) headerView.findViewById(R.id.list_header_group_name)).setText(headerText);
        shedLayout.addHeaderView(headerView);
        ShedModelAdapter modelAdapter = new ShedModelAdapter(this);
        try {
            JSONArray daysJsonArray = new JSONArray(intent.getStringExtra(Strings.EXTRA_JSON));
            for (int i = 0; i < daysJsonArray.length(); i++) {
                List<ShedTask> events = new ArrayList<>();
                Calendar c = Calendar.getInstance();
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                if (dayOfWeek < i + 1) {
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    while (!(i + 1 == c.get(Calendar.DAY_OF_WEEK) - 1)) {
                        c.add(Calendar.DAY_OF_MONTH, 1);
                    }
                } else if (dayOfWeek > i + 1) {
                    c.add(Calendar.DAY_OF_MONTH, -1);
                    while (!(i + 1 == c.get(Calendar.DAY_OF_WEEK) - 1)) {
                        c.add(Calendar.DAY_OF_MONTH, -1);
                    }
                }
                Date date = c.getTime();
                JSONArray dayPairsArray = daysJsonArray.getJSONArray(i);
                for (int j = 0; j < dayPairsArray.length(); j++) {
                    JSONObject object = dayPairsArray.getJSONObject(j);
                    switch (object.length()) {
                        case 3: {
                            events.add(new Break(object, date));
                            break;
                        }
                        case 4: {
                            events.add(new NoPair(object, date));
                            break;
                        }
                        case 6: {
                            events.add(new Pair(object, date));
                            break;
                        }
                    }
                }

                ListEntry entry = new ListEntry(this, date, events);
                modelAdapter.add(entry);
            }
            shedLayout.setAdapter(modelAdapter);
        } catch (JSONException e) {
            Log.d(LOG_TAG, "Can't parse JSON");
        }

    }
}
