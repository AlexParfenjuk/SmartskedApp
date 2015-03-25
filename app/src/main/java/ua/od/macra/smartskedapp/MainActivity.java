package ua.od.macra.smartskedapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ua.od.macra.smartskedapp.models.Faculty;
import ua.od.macra.smartskedapp.models.Strings;


public class MainActivity extends Activity {

    Spinner instituteSpinner, facultySpinner, groupSpinner, testSpinner;
    Strings strings;
    int instIndex, facultIndex, groupIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        strings = new Strings();
        testSpinner = (Spinner) findViewById(R.id.testSpinner);
        String[] test = {"ololo", "trololo"};
        ArrayAdapter<String> testAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, test);
        testAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        testSpinner.setAdapter(testAdapter);
        instituteSpinner = (Spinner) findViewById(R.id.instituteSpinner);
        facultySpinner = (Spinner) findViewById(R.id.facultySpinner);
        facultySpinner.setOnItemSelectedListener(facultListener);
        groupSpinner = (Spinner) findViewById(R.id.groupSpinner);
        instituteSpinner.setAdapter(ArrayAdapter.
                createFromResource(this, R.array.institutes, android.R.layout.simple_dropdown_item_1line));
        instituteSpinner.setSelection(0);
        instituteSpinner.setOnItemSelectedListener(instListener);
    }

    private AdapterView.OnItemSelectedListener instListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Context context = view.getContext();
            List<String> facs = new ArrayList<>();
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
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line);
            if (position != 0) {
                facultySpinner.setSelection(0);
                facultySpinner.setOnItemSelectedListener(facultListener);
            }
            facultySpinner.setAdapter(adapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
