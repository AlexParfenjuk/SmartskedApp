package ua.od.macra.smartsked;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ua.od.macra.smartsked.models.Faculty;
import ua.od.macra.smartsked.models.Group;
import ua.od.macra.smartsked.models.Strings;


public class MainActivity extends Activity {

    public static final String LOG_TAG = MainActivity.class.getName();

    Spinner instituteSpinner, facultySpinner, groupSpinner;
    Strings strings;
    int instIndex, facultIndex, groupIndex;
    List<JSONArray> days = new ArrayList<>();
    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;
    private SharedPreferences.Editor shPrefsEditor;
    private SharedPreferences shPrefs;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        shPrefsEditor = getSharedPreferences(LOG_TAG, MODE_PRIVATE).edit();

        strings = new Strings();
        instituteSpinner = (Spinner) findViewById(R.id.instituteSpinner);
        facultySpinner = (Spinner) findViewById(R.id.facultSpinner);
        groupSpinner = (Spinner) findViewById(R.id.groupSpinner);

        instituteSpinner.setAdapter(ArrayAdapter.
                createFromResource(this, R.array.institutes, R.layout.simple_dropdown_item_1line_small));
        instituteSpinner.setSelection(0);
        instituteSpinner.setOnItemSelectedListener(instListener);

        facultySpinner.setOnItemSelectedListener(facultListener);
        groupSpinner.setOnItemSelectedListener(groupListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        instituteSpinner.setAdapter(ArrayAdapter.
                createFromResource(this, R.array.institutes, R.layout.simple_dropdown_item_1line_small));
        shPrefs = getSharedPreferences(LOG_TAG, MODE_PRIVATE);
        instituteSpinner.setSelection(shPrefs.getInt(Strings.PREF_INST_INDEX, 0));
    }

    private AdapterView.OnItemSelectedListener instListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Context context = getApplicationContext();
            List<String> facs = new ArrayList<>();
            facs.add(context.getResources().getString(R.string.not_chosen));
            if (position != 0) {
                instIndex = position;
                for (Faculty faculty : strings.faculties) {
                    if (faculty.getInstId() == position) facs.add(faculty.getName());
                }
                shPrefsEditor.putInt(Strings.PREF_INST_INDEX, position);
                shPrefsEditor.apply();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.simple_dropdown_item_1line_small, facs);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            facultySpinner.setAdapter(adapter);
            facultySpinner.setSelection(shPrefs.getInt(Strings.PREF_FACULT_INDEX, 0));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener facultListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Context context = getApplicationContext();
            String facultyName = String.valueOf(((TextView) view).getText());
            for (Faculty faculty : strings.faculties) {
                if (faculty.getName().equals(facultyName)) {
                    facultIndex = faculty.getFacuId();
                    break;
                }
            }
            List<String> groups = new ArrayList<>();
            groups.add(context.getResources().getString(R.string.not_chosen));
            if (position != 0) {
                for (Group group : strings.groups) {
                    if (group.getFacuId() == facultIndex) groups.add(group.getName());
                }
                shPrefsEditor.putInt(Strings.PREF_FACULT_INDEX, position);
                shPrefsEditor.apply();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.simple_dropdown_item_1line_small, groups);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            groupSpinner.setAdapter(adapter);
            groupSpinner.setSelection(shPrefs.getInt(Strings.PREF_GROUP_INDEX, 0));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener groupListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                groupName = String.valueOf(((TextView) view).getText());
                for (Group group : strings.groups) {
                    if (group.getName().equals(groupName)) {
                        groupIndex = group.getGroupId();
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
                jsonString = parser.execute().get();
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

    private AsyncTask<Void, Integer, String> parser = new AsyncTask<Void, Integer, String>() {

        @Override
        protected String doInBackground(Void... v) {
            String jsonString = "";
            try {
                days.clear();
                Uri apiRequestUri = new Uri.Builder()
                        .scheme("http")
                        .authority("sskedapp.esy.es")
                        .appendPath("parser.php")
                        .appendQueryParameter("inst", "" + instIndex)
                        .appendQueryParameter("facult", "" + facultIndex)
                        .appendQueryParameter("group", "" + groupIndex)
                        .build();
                HttpURLConnection connection = (HttpURLConnection) new URL(apiRequestUri.toString()).openConnection();
                InputStream is = connection.getInputStream();
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                jsonString = sb.toString();
                FileOutputStream fos = openFileOutput(instIndex + "_" + facultIndex + "_" + groupIndex + ".json", MODE_PRIVATE);
                fos.write(jsonString.getBytes());
                fos.close();
            } catch (IOException e) {
                Log.d(LOG_TAG, "Can't connect to the server");
            }
            return jsonString;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    };
}
