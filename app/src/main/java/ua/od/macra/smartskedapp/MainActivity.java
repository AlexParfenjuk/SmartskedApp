package ua.od.macra.smartskedapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ua.od.macra.smartskedapp.models.Faculty;
import ua.od.macra.smartskedapp.models.Group;
import ua.od.macra.smartskedapp.models.Strings;


public class MainActivity extends Activity {

    public static final String LOG_TAG = MainActivity.class.getName();

    Spinner instituteSpinner, facultySpinner, groupSpinner;
    Strings strings;
    int instIndex, facultIndex, groupIndex;
    List<JSONArray> days = new ArrayList<>();
    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("inst", instIndex);
        outState.putInt("facult", facultIndex);
        outState.putInt("group", groupIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        instituteSpinner.setSelection(savedInstanceState.getInt("inst"));
        facultySpinner.setSelection(savedInstanceState.getInt("facult"));
        groupSpinner.setSelection(savedInstanceState.getInt("group"));
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
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.simple_dropdown_item_1line_small, facs);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            facultySpinner.setAdapter(adapter);
            facultySpinner.setSelection(0);
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
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.simple_dropdown_item_1line_small, groups);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            groupSpinner.setAdapter(adapter);
            groupSpinner.setSelection(0);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener groupListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                String groupName = String.valueOf(((TextView) view).getText());
                for (Group group : strings.groups) {
                    if (group.getName().equals(groupName)) {
                        groupIndex = group.getGroupId();
                        break;
                    }
                }
                networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new JSONParser().start();
                } else
                    Toast.makeText(getApplicationContext(), "Не можу загрузити розклад :(", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private class JSONParser extends Thread {
        @Override
        public void run() {
            super.run();
            days.clear();
            Uri apiRequestUri = new Uri.Builder()
                    .scheme("http")
                    .authority("sskedapp.esy.es")
                    .appendPath("parser.php")
                    .appendQueryParameter("inst", "" + instIndex)
                    .appendQueryParameter("facult", "" + facultIndex)
                    .appendQueryParameter("group", "" + groupIndex)
                    .build();
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(apiRequestUri.toString()).openConnection();
                InputStream is = connection.getInputStream();
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String jsonString = sb.toString();
                Intent startShed = new Intent(MainActivity.this, ShedActivity.class);
                startShed.putExtra(Strings.EXTRA_JSON, jsonString);
                MainActivity.this.startActivity(startShed);
            } catch (IOException e) {
                Log.d(LOG_TAG, "Can't connect to the server");
            }
        }
    }
}
