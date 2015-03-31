package ua.od.macra.smartsked;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import java.io.FileNotFoundException;
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


public class MainActivity extends Activity {

    private static final String LOG_TAG = MainActivity.class.getName();

    private Spinner instituteSpinner, facultySpinner, groupSpinner;

    private int instIndex, facultIndex, groupIndex;
    private String groupName;

    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;

    private SharedPreferences shPrefs;

    private List<Institute> instList = new ArrayList<>();
    private List<Faculty> facultList = new ArrayList<>();
    private List<Group> groupList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        shPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        initData();

        instituteSpinner = (Spinner) findViewById(R.id.instituteSpinner);
        instituteSpinner.setSelection(0);
        instituteSpinner.setOnItemSelectedListener(instListener);

        facultySpinner = (Spinner) findViewById(R.id.facultSpinner);
        facultySpinner.setOnItemSelectedListener(facultListener);

        groupSpinner = (Spinner) findViewById(R.id.groupSpinner);
        groupSpinner.setOnItemSelectedListener(groupListener);
    }

    private void initData() {
        String instJsonString = null, facultJsonString = null, groupJsonString = null;
        JSONArray instArray, facultArray, groupArray;
        networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            instJsonString = JSONGetter.getInstList();
            facultJsonString = JSONGetter.getFacultList();
            groupJsonString = JSONGetter.getGroupList();
            new FileSaver(instJsonString, Strings.FILE_NAME_INST).start();
            new FileSaver(facultJsonString, Strings.FILE_NAME_FACULT).start();
            new FileSaver(groupJsonString, Strings.FILE_NAME_GROUPS).start();
        } else {
            try {
                instJsonString = new FileLoader().execute(Strings.FILE_NAME_INST).get();
                facultJsonString = new FileLoader().execute(Strings.FILE_NAME_FACULT).get();
                groupJsonString = new FileLoader().execute(Strings.FILE_NAME_GROUPS).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.d(LOG_TAG, "Error while getting Asynctask result");
            }
        }
        if ((instJsonString != null) && (facultJsonString != null) && (groupJsonString != null)) {
            try {
                instArray = new JSONArray(instJsonString);
                facultArray = new JSONArray(facultJsonString);
                groupArray = new JSONArray(groupJsonString);

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
                Log.d(LOG_TAG, "Can't parse JSON");
            }
        }
        else
            Toast.makeText(MainActivity.this, "Не можу завантажити списки :(", Toast.LENGTH_LONG).show();
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

                shPrefs.edit().putInt(Strings.PREF_INST_INDEX, position).apply();
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
                shPrefs.edit().putInt(Strings.PREF_FACULT_INDEX, position).apply();
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
                shPrefs.edit().putInt(Strings.PREF_GROUP_INDEX, position).apply();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void onShowButtonClick(View view) {
        String jsonString = null;
        final String fileName = instIndex + "_" + facultIndex + "_" + groupIndex + ".json";
        networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
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
            new FileSaver(jsonString, fileName).start();
        } else {
            try {
                jsonString = new FileLoader().execute(fileName).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.d(LOG_TAG, "Error while getting Asynctask result");
            }
        }

        if (jsonString != null) {
            Intent startShed = new Intent(MainActivity.this, ShedActivity.class);
            startShed.putExtra(Strings.EXTRA_GROUP_NAME, groupName);
            startShed.putExtra(Strings.EXTRA_JSON, jsonString);
            startActivity(startShed);
        } else
            Toast.makeText(MainActivity.this, "Не можу завантажити розклад :(", Toast.LENGTH_LONG).show();

    }

    class FileLoader extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String fileName = strings[0];
            String resultString = "";
            try {
                FileInputStream fis = openFileInput(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                int content;
                StringBuilder sb = new StringBuilder();
                while ((content = isr.read()) != -1) {
                    sb.append((char) content);
                }
                resultString = sb.toString();
                isr.close();
                fis.close();
            } catch (IOException e) {
                Log.d(LOG_TAG, "File not exist");
            }
            return resultString;
        }
    }

    class FileSaver extends Thread {

        String targetString, fileName;

        FileSaver(String targetString, String fileName) {
            this.targetString = targetString;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            try {
                FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
                fos.write(targetString.getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(LOG_TAG, "File not found");
            } catch (IOException e) {
                Log.d(LOG_TAG, "Error while opening output stream");
            }
        }
    }
}
