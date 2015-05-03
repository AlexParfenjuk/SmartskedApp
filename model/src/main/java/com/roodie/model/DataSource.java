package com.roodie.model;

/**
 * Created by Roodie on 03.05.2015.
 */
public interface DataSource {

    public void getInstitutes();

    public void getGroups();

    public void getFaculties();

    public void getInstitute(int id);

    public void getGroup(int id);

    public void getFaculty(int id);

    public void getScheduleCurrent();

    public void getScheduleData();

}
