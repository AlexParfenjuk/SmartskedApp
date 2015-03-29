package ua.od.macra.smartskedapp.models.json;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ua.od.macra.smartskedapp.models.Strings;

public class Break implements ShedTask {

    public String type, time_from, time_to;

    public Break(JSONObject jsonObject) {
        try {
            type = jsonObject.getString(Strings.TYPE);
            time_from = jsonObject.getString(Strings.TIME_FROM);
            time_to = jsonObject.getString(Strings.TIME_TO);
        } catch (JSONException e) {
            Log.d("BreakConstructor", "Can't parse JSON");
        }
    }

    public String getTimeString() {
        return time_from + " : " + time_to;
    }
}
