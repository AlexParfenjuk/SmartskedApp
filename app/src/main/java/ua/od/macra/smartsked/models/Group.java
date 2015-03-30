package ua.od.macra.smartsked.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Group {
    private int id;
    private int faculty_id;
    private String name;
    private String letter;
    private String created_at;
    private String updated_at;

    public Group(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.faculty_id = object.getInt("faculty_id");
            this.name = object.getString("name");
            this.letter = object.getString("letter");
            this.created_at = object.getString("created_at");
            this.updated_at = object.getString("updated_at");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public int getFacultyId() {
        return faculty_id;
    }

    public String getName() {
        return name;
    }

    public String getFullName(){
        return name+letter;
    }
}
