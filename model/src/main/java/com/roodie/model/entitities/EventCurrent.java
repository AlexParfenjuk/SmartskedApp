package com.roodie.model.entitities;

import com.google.gson.annotations.Expose;

import java.util.Arrays;

/**
 * Created by Roodie on 08.04.2015.
 */
public class EventCurrent {
    @Expose
    private String type;
    @Expose
    private Integer index;
    @Expose
    private String[] data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EventCurrent{" +
                "type='" + type + '\'' +
                ", index=" + index +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
