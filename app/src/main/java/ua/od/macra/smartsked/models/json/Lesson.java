package ua.od.macra.smartsked.models.json;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ua.od.macra.smartsked.Strings;

public class Lesson implements ShedTask{

    public int id, group_id, index;
    public String name, place, type, teacher_name;

    public Lesson(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt(Strings.PAIR_ID);
            group_id = jsonObject.getInt(Strings.PAIR_GROUP_ID);
            index = jsonObject.getInt(Strings.PAIR_INDEX);
            name = jsonObject.getString(Strings.PAIR_NAME);
            type = jsonObject.getString(Strings.PAIR_TYPE);
            place = jsonObject.getString(Strings.PAIR_PLACE);
            teacher_name = jsonObject.getString(Strings.PAIR_TEACHER);
        } catch (JSONException e) {
            Log.d("PairConstructor", "Can't parse JSON");
        }
    }
}
