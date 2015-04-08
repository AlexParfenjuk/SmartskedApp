package ua.od.macra.smartsked.api.rest;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import ua.od.macra.smartsked.api.entitities.Faculty;
import ua.od.macra.smartsked.api.entitities.Group;
import ua.od.macra.smartsked.api.entitities.Institute;
import ua.od.macra.smartsked.api.entitities.Lesson;
import ua.od.macra.smartsked.api.entitities.LessonsWrapper;
import ua.od.macra.smartsked.api.entitities.ScheduleCurrent;
import ua.od.macra.smartsked.api.entitities.ScheduleData;

/**
 * Created by Roodie on 08.04.2015.
 */
public interface SmartskedService {

    public static final String FORMAT_JSON = "json";
    public static final String URL = "http://smartsked.com.ua/api/v1";
    public static String KEY = "key";



    @GET("/institute/list")
    List<Institute> instituteList(
            @Query("key") String api_key
    );

    @GET("/faculty/list")
    List<Faculty> facultyList(
            @Query("key") String api_key
    );

    @GET("/group/list")
    List<Group> groupList(
            @Query("key") String api_key
    );

    @GET("/institute/{id}")
    Institute institute(@Path("id") Integer param,
                        @Query("key") String api_key);

    @GET("/faculty/{id}")
    Faculty faculty(@Path("id") Integer param,
                    @Query("key") String api_key);

    @GET("/group/{id}")
    Group group(@Path("id") Integer param,
                  @Query("key") String api_key
    );

    @GET("/schedule/data")
    ScheduleData scheduleData(
            @Query("key") String api_key
    );

    @GET("/schedule/now")
    ScheduleCurrent scheduleNow(
            @Query("key") String api_key
    );


    //need to fix
    @GET("/schedule/{id}")
    List<LessonsWrapper> schedule(
            @Path("id") Integer param,
            @Query("key") String api_key,
            @Query("dates[]") List<String> dates);
    //need t ofix

}
