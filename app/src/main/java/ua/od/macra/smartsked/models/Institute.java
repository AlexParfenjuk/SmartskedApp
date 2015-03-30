package ua.od.macra.smartsked.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Institute {
    public int id;
    public String name;
    public String abbreviation;

    public Institute(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.name = object.getString("name");
            this.abbreviation = object.getString("abbreviation");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

