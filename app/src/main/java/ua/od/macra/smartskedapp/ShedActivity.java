package ua.od.macra.smartskedapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;


public class ShedActivity extends ActionBarActivity {

    private float tinyHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shed);
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.listPreferredItemHeightSmall, typedValue, true);
        tinyHeight = typedValue.getDimension(getResources().getDisplayMetrics()) / 2;
    }
}
