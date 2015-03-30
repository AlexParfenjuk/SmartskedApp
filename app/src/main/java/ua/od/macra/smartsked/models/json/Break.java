package ua.od.macra.smartsked.models.json;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ua.od.macra.smartsked.models.Strings;

public class Break extends ShedTask {

    public String type, time_from, time_to;

    public Break(JSONObject jsonObject, Date day) {
        try {
            date = day;
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
