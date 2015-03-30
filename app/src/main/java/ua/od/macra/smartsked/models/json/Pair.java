package ua.od.macra.smartsked.models.json;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ua.od.macra.smartsked.models.Strings;

public class Pair extends ShedTask {

    public String time_from, time_to, name, type, aud, number;

    public Pair(JSONObject jsonObject, Date day) {
        try {
            number = jsonObject.getString(Strings.NUMBER);
            time_from = jsonObject.getString(Strings.TIME_FROM);
            time_to = jsonObject.getString(Strings.TIME_TO);
            name = jsonObject.getString(Strings.DISCIPL);
            type = jsonObject.getString(Strings.TYPE);
            aud = jsonObject.getString(Strings.AUD);
            date = day;
        } catch (JSONException e) {
            Log.d("PairConstructor", "Can't parse JSON");
        }
    }

    public String getTimeString() {
        return time_from + " : " + time_to;
    }
}
