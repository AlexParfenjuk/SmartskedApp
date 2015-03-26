package ua.od.macra.smartskedapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.od.macra.smartskedapp.models.Faculty;
import ua.od.macra.smartskedapp.models.Group;
import ua.od.macra.smartskedapp.models.Strings;


public class MainActivity extends Activity {

    Spinner instituteSpinner, facultySpinner, groupSpinner;
    Strings strings;
    int instIndex, facultIndex, groupIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        strings = new Strings();
        instituteSpinner = (Spinner) findViewById(R.id.instituteSpinner);
        facultySpinner = (Spinner) findViewById(R.id.facultSpinner);
        groupSpinner = (Spinner) findViewById(R.id.groupSpinner);

        instituteSpinner.setAdapter(ArrayAdapter.
                createFromResource(this, R.array.institutes, android.R.layout.simple_dropdown_item_1line));
        instituteSpinner.setSelection(0);
        instituteSpinner.setOnItemSelectedListener(instListener);

        facultySpinner.setOnItemSelectedListener(facultListener);
        groupSpinner.setOnItemSelectedListener(groupListener);
        Log.d("", "");
    }

    private AdapterView.OnItemSelectedListener instListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Context context = view.getContext();
            List<String> facs = new ArrayList<>();
            facs.add(context.getResources().getString(R.string.not_chosen));
            if (position != 0) {
                instIndex = position;
                for (Faculty faculty : strings.faculties) {
                    if (faculty.getInstId() == position) facs.add(faculty.getName());
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, facs);
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
            Context context = view.getContext();
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
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, groups);
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

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
