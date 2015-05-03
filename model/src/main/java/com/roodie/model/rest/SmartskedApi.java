package com.roodie.model.rest;

import com.roodie.model.entitities.FacultiesWrapper;
import com.roodie.model.entitities.Faculty;
import com.roodie.model.entitities.Group;
import com.roodie.model.entitities.GroupsWrapper;
import com.roodie.model.entitities.Institute;
import com.roodie.model.entitities.InstitutesWrapper;
import com.roodie.model.entitities.LessonsWrapper;
import com.roodie.model.entitities.ScheduleCurrent;
import com.roodie.model.entitities.ScheduleData;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Roodie on 08.04.2015.
 */
public interface SmartskedApi {

    public static final String FORMAT_JSON = "json";
    public static final String URL = "http://smartsked.com.ua/api/v1";
    public static String KEY = "key";


    @GET("/institute/list")
    void getInstituteList(
            @Query("key") String api_key,
            Callback<InstitutesWrapper> response
    );

    @GET("/faculty/list")
    void getFacultyList(
            @Query("key") String api_key,
            Callback<FacultiesWrapper> response
    );

    @GET("/group/list")
    void getGroupList(
            @Query("key") String api_key,
            Callback<GroupsWrapper> response
    );

    @GET("/institute/{id}")
    void getInstitute(
            @Path("id") Integer param,
            @Query("key") String api_key,
            Callback<Institute> response);

    @GET("/faculty/{id}")
    void getFaculty(
            @Path("id") Integer param,
            @Query("key") String api_key,
            Callback<Faculty> response);

    @GET("/group/{id}")
    void getGroup(
            @Path("id") Integer param,
            @Query("key") String api_key,
            Callback<Group> response
    );

    @GET("/schedule/data")
    void scheduleData(
            @Query("key") String api_key,
            Callback<ScheduleData> response
    );

    @GET("/schedule/now")
    void scheduleNow(
            @Query("key") String api_key,
            Callback<ScheduleCurrent> response
    );


    //need to fix
    @GET("/schedule/{id}")
    void schedule(
            @Path("id") String param,
            @Query("key") String api_key,
            @Query("dates[]") List<String> dates,
            Callback<LessonsWrapper> response);
    //need tofix

}
