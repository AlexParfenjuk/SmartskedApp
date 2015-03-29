package ua.od.macra.smartskedapp.models.json;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ua.od.macra.smartskedapp.models.Strings;

public class NoPair implements ShedTask {

    public String time_from, time_to, number;

    public NoPair(String time_from, String time_to, String number) {
        this.time_from = time_from;
        this.time_to = time_to;
        this.number = number;
    }

    public NoPair(JSONObject jsonObject){
        try {
            number = jsonObject.getString(Strings.NUMBER);
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
