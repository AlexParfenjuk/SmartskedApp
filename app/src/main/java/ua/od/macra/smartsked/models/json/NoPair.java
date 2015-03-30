package ua.od.macra.smartsked.models.json;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ua.od.macra.smartsked.models.Strings;

public class NoPair extends ShedTask {

    public String time_from, time_to, number;

    public NoPair(String time_from, String time_to, String number) {
        this.time_from = time_from;
        this.time_to = time_to;
        this.number = number;
    }

    public NoPair(JSONObject jsonObject, Date day){
        try {
            number = jsonObject.getString(Strings.NUMBER);
            time_from = jsonObject.getString(Strings.TIME_FROM);
            time_to = jsonObject.getString(Strings.TIME_TO);
            date = day;
        } catch (JSONException e) {
            Log.d("BreakConstructor", "Can't parse JSON");
        }
    }

    public String getTimeString() {
        return time_from + " : " + time_to;
    }
}
