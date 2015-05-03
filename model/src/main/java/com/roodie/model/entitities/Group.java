package com.roodie.model.entitities;

import com.google.gson.annotations.Expose;

/**
 * Created by Roodie on 08.04.2015.
 */
public class Group {
    @Expose
    private Integer id;
    @Expose
    private Integer faculty_id;
    @Expose
    private String name;
    @Expose
    private String letter;
    @Expose
    private String created_at;
    @Expose
    private String updated_at;

    public Number getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(Integer faculty_id) {
        this.faculty_id = faculty_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", faculty_id=" + faculty_id +
                ", name='" + name + '\'' +
                ", letter='" + letter + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
