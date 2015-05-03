package com.roodie.model.entitities;

import java.util.List;

/**
 * Created by Roodie on 03.05.2015.
 */
public class LessonsWrapper {
    public List<LessonsInstance> lessons;

    public List<LessonsInstance> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonsInstance> lessons) {
        this.lessons = lessons;
    }
}
