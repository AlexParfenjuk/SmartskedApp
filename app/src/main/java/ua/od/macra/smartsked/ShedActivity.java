package ua.od.macra.smartsked;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ua.od.macra.smartsked.adapter.ShedModelAdapter;
import ua.od.macra.smartsked.models.Strings;
import ua.od.macra.smartsked.models.json.Pair;
import ua.od.macra.smartsked.models.list.ListEntry;


public class ShedActivity extends Activity {

    public static final String LOG_TAG = ShedActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shed);

        Intent intent = getIntent();
        String groupName = intent.getStringExtra(Strings.EXTRA_GROUP_NAME);
        String headerText = getString(R.string.list_header_group_name, groupName);

        View headerView = getLayoutInflater().inflate(R.layout.ssked_list_header, null);
        ((TextView) headerView.findViewById(R.id.list_header_group_name)).setText(headerText);
        ListView shedList = (ListView) findViewById(R.id.shedTable);
        shedList.addHeaderView(headerView);

        ShedModelAdapter modelAdapter = new ShedModelAdapter(this);
        try {
            JSONObject daysJsonObject = new JSONObject(intent.getStringExtra(Strings.EXTRA_JSON));
            JSONArray array = daysJsonObject.toJSONArray(daysJsonObject.names());
            JSONArray datesArray = daysJsonObject.names();
            for (int i = 0; i < array.length(); i++) {
                List<Pair> events = new ArrayList<>();
                JSONObject object = array.getJSONObject(i);
                JSONArray objectNames = object.names();
                JSONArray eventArray = object.toJSONArray(objectNames);
                for (int j = 0; j < eventArray.length(); j++) {
                    Pair pair = new Pair(new JSONObject(eventArray.getString(j)));
                    events.add(Integer.parseInt(objectNames.getString(j)), pair);
                }
                ListEntry entry = new ListEntry
                        (this, SimpleDateFormat.getDateInstance().parse(datesArray.getString(i)), events);
                modelAdapter.add(entry);
            }
            shedList.setAdapter(modelAdapter);
        } catch (JSONException e) {
            Log.d(LOG_TAG, "Can't parse JSON");
        } catch (ParseException e) {
            Log.d(LOG_TAG, "Can't parse date string");
        }

    }
}
