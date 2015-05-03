package com.roodie.model.entitities;

import com.google.gson.annotations.Expose;

public class Faculty {
    @Expose
    private Integer id;
    @Expose
    private Integer institute_id;
    @Expose
    private String name;
    @Expose
    private String abbreviation;
    @Expose
    private String created_at;
    @Expose
    private String updated_at;
    @Expose
    private Integer status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(int institute_id) {
        this.institute_id = institute_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

