package ua.od.macra.smartsked.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Faculty {
    private int id;
    private int institute_id;
    private String name;
    private String abbreviation;
    private String created_at;
    private String updated_at;
    private String status;

    public Faculty(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.institute_id = object.getInt("institute_id");
            this.name = object.getString("name");
            this.abbreviation = object.getString("abbreviation");
            this.created_at = object.getString("created_at");
            this.updated_at = object.getString("updated_at");
            this.status = object.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getInstituteId() {
        return institute_id;
    }
}

