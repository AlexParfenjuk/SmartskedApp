package ua.od.macra.smartsked;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ua.od.macra.smartsked.adapter.ShedModelAdapter;
import ua.od.macra.smartsked.models.json.Lesson;
import ua.od.macra.smartsked.models.json.NoLesson;
import ua.od.macra.smartsked.models.json.ShedTask;
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

        ListView shedList = (ListView) findViewById(R.id.shedTable);

        LinearLayout headerView = new LinearLayout(this);
        getLayoutInflater().inflate(R.layout.ssked_list_header, headerView);
        ((TextView) headerView.findViewById(R.id.list_header_group_name)).setText(headerText);
        shedList.addHeaderView(headerView);

        ShedModelAdapter modelAdapter = new ShedModelAdapter(this);
        try {
            JSONObject daysJsonObject = new JSONObject(intent.getStringExtra(Strings.EXTRA_JSON));
            JSONArray array = daysJsonObject.toJSONArray(daysJsonObject.names());
            JSONArray datesArray = daysJsonObject.names();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                JSONArray objectNames = object.names();
                JSONArray eventArray = object.toJSONArray(objectNames);
                List<Pair<Integer, ShedTask>> eventPairs = new ArrayList<>();
                for (int j = 0; j < eventArray.length(); j++) {
                    Lesson lesson = new Lesson(new JSONObject(eventArray.getString(j)));
                    eventPairs.add(new Pair<Integer, ShedTask>(Integer.parseInt(objectNames.getString(j)), lesson));
                }
                List<Pair<Integer, ShedTask>> taskList = new ArrayList<>();
                for (Pair<Integer, ShedTask> pair: eventPairs) {
                    int index = pair.first;
                    for (int j = taskList.size(); j < index-1; j++) {
                        taskList.add(new Pair<Integer, ShedTask>(j+1, new NoLesson()));
                    }
                    taskList.add(pair);
                }
                for (int j = taskList.size(); j < 6; j++) {
                    taskList.add(new Pair<Integer, ShedTask>(j+1, new NoLesson()));
                }
                ListEntry entry = new ListEntry
                        (this, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(datesArray.getString(i)), taskList);
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
