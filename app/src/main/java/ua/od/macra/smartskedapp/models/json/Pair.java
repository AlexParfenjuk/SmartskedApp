package ua.od.macra.smartskedapp.models.json;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ua.od.macra.smartskedapp.models.Strings;

public class Pair implements ShedTask {

    public String time_from, time_to, name, type, aud, number;

    public Pair(JSONObject jsonObject) {
        try {
            number = jsonObject.getString(Strings.NUMBER);
            time_from = jsonObject.getString(Strings.TIME_FROM);
            time_to = jsonObject.getString(Strings.TIME_TO);
            name = jsonObject.getString(Strings.DISCIPL);
            type = jsonObject.getString(Strings.TYPE);
            aud = jsonObject.getString(Strings.AUD);
        } catch (JSONException e) {
            Log.d("PairConstructor", "Can't parse JSON");
        }
    }

    public String getTimeString() {
        return time_from + " : " + time_to;
    }
}
