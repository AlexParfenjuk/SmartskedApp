package ua.od.macra.smartsked;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import ua.od.macra.smartsked.models.Faculty;
import ua.od.macra.smartsked.models.Group;
import ua.od.macra.smartsked.models.Institute;
import ua.od.macra.smartsked.models.Strings;


public class MainActivity extends Activity {

    public static final String LOG_TAG = MainActivity.class.getName();

    Spinner instituteSpinner, facultySpinner, groupSpinner;
    int instIndex, facultIndex, groupIndex;
    List<JSONArray> days = new ArrayList<>();
    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;
    private SharedPreferences.Editor shPrefsEditor;
    private SharedPreferences shPrefs;
    private String groupName;
    private List<Institute> instList = new ArrayList<>();
    private List<Faculty> facultList = new ArrayList<>();
    private List<Group> groupList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        shPrefsEditor = getSharedPreferences(LOG_TAG, MODE_PRIVATE).edit();

        instituteSpinner = (Spinner) findViewById(R.id.instituteSpinner);
        facultySpinner = (Spinner) findViewById(R.id.facultSpinner);
        groupSpinner = (Spinner) findViewById(R.id.groupSpinner);

        try {
            JSONArray instArray = new JSONArray(JSONGetter.getInstList());
            JSONArray facultArray = new JSONArray(JSONGetter.getFacultList());
            JSONArray groupArray = new JSONArray(JSONGetter.getGroupList());
            for (int i = 0; i < instArray.length(); i++) {
                instList.add(new Institute(instArray.getJSONObject(i)));
            }
            for (int i = 0; i < facultArray.length(); i++) {
                facultList.add(new Faculty(facultArray.getJSONObject(i)));
            }
            for (int i = 0; i < groupArray.length(); i++) {
                groupList.add(new Group(groupArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        instituteSpinner.setSelection(0);
        instituteSpinner.setOnItemSelectedListener(instListener);

        facultySpinner.setOnItemSelectedListener(facultListener);
        groupSpinner.setOnItemSelectedListener(groupListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shPrefs = getSharedPreferences(LOG_TAG, MODE_PRIVATE);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_dropdown_item_1line_small);
        adapter.add(getResources().getString(R.string.not_chosen));
        for (Institute inst : instList) {
            adapter.add(inst.name);
        }
        instituteSpinner.setAdapter(adapter);
        instituteSpinner.setSelection(shPrefs.getInt(Strings.PREF_INST_INDEX, 0));
    }

    private AdapterView.OnItemSelectedListener instListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Context context = MainActivity.this;
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(context, R.layout.simple_dropdown_item_1line_small);
            adapter.add(context.getResources().getString(R.string.not_chosen));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if (position != 0) {
                instIndex = position;
                for (Faculty faculty : facultList) {
                    if (faculty.getInstituteId() == instIndex) adapter.add(faculty.getName());
                }

                shPrefsEditor.putInt(Strings.PREF_INST_INDEX, position);
                shPrefsEditor.apply();
                facultySpinner.setAdapter(adapter);
                facultySpinner.setSelection(shPrefs.getInt(Strings.PREF_FACULT_INDEX, 0));
            } else {
                facultySpinner.setAdapter(adapter);
                facultySpinner.setSelection(0);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener facultListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Context context = MainActivity.this;
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(context, R.layout.simple_dropdown_item_1line_small);
            adapter.add(context.getResources().getString(R.string.not_chosen));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            String facultyName = String.valueOf(((TextView) view).getText());
            for (Faculty faculty : facultList) {
                if (faculty.getName().equals(facultyName)) {
                    facultIndex = faculty.getId();
                    break;
                }
            }
            if (position != 0) {
                for (Group group : groupList) {
                    if (group.getFacultyId() == facultIndex) adapter.add(group.getFullName());
                }
                shPrefsEditor.putInt(Strings.PREF_FACULT_INDEX, position);
                shPrefsEditor.apply();
                groupSpinner.setAdapter(adapter);
                groupSpinner.setSelection(shPrefs.getInt(Strings.PREF_GROUP_INDEX, 0));
            } else {
                groupSpinner.setAdapter(adapter);
                groupSpinner.setSelection(0);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener groupListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                groupName = ((TextView) view).getText().toString();
                for (Group group : groupList) {
                    if (group.getFullName().equals(groupName)) {
                        groupIndex = group.getId();
                        break;
                    }
                }
                shPrefsEditor.putInt(Strings.PREF_GROUP_INDEX, position);
                shPrefsEditor.apply();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void onShowButtonClick(View view) {
        String jsonString = null;
        networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                jsonString = new Parser().execute().get();
            } catch (InterruptedException | ExecutionException e) {
                Log.d(LOG_TAG, "Cannot get an AsyncTask result");
            }
        } else {
            try {
                FileInputStream fis = openFileInput(instIndex + "_" + facultIndex + "_" + groupIndex + ".json");
                InputStreamReader isr = new InputStreamReader(fis);
                int content;
                StringBuilder sb = new StringBuilder();
                while ((content = isr.read()) != -1) {
                    sb.append((char) content);
                }
                jsonString = sb.toString();
                isr.close();
                fis.close();
            } catch (IOException e) {
                Log.d(LOG_TAG, "File not exist");
            }
        }
        if (jsonString != null) {
            Intent startShed = new Intent(MainActivity.this, ShedActivity.class);
            startShed.putExtra(Strings.EXTRA_GROUP_NAME, groupName);
            startShed.putExtra(Strings.EXTRA_JSON, jsonString);
            startActivity(startShed);
        } else
            Toast.makeText(getApplicationContext(), "Не можу завантажити розклад :(", Toast.LENGTH_LONG).show();
    }

    class Parser extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... v) {
            String jsonString = "";
            try {
                days.clear();
                List<String> dates = new ArrayList<>();
                Calendar cal = Calendar.getInstance();
                while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                }
                for (int i = 0; i < 6; i++) {
                    dates.add(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.getTime()));
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
                jsonString = JSONGetter.getScheduleByGroupId(groupIndex, dates);
                FileOutputStream fos = openFileOutput(instIndex + "_" + facultIndex + "_" + groupIndex + ".json", MODE_PRIVATE);
                fos.write(jsonString.getBytes());
                fos.close();
            } catch (IOException e) {
                Log.d(LOG_TAG, "Can't connect to the server");
            }
            return jsonString;
        }
    }
}
