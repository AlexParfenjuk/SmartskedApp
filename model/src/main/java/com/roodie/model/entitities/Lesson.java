package com.roodie.model.entitities;


import com.google.gson.annotations.Expose;

public class Lesson {

    @Expose
    private Number id;
    @Expose
    private Number group_id;
    @Expose
    private Integer index;
    @Expose
    private String name;
    @Expose
    private String place;
    @Expose
    private String type;
    @Expose
    private String teacher_name;

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Number getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Number group_id) {
        this.group_id = group_id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", group_id=" + group_id +
                ", index=" + index +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", type='" + type + '\'' +
                ", teacher_name='" + teacher_name + '\'' +
                '}';
    }
}
