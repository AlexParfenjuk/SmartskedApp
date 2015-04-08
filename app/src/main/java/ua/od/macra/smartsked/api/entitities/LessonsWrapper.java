package ua.od.macra.smartsked.api.entitities;

import java.util.List;

/**
 * Created by Roodie on 08.04.2015.
 */
public class LessonsWrapper {
    /// ??????????????????????????
    private String date;
    private List<Lesson> lessons;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        return "LessonsWrapper{" +
                "date='" + date + '\'' +
                ", lessons=" + lessons +
                '}';
    }
}
