package ua.od.macra.smartsked.api.rest;

import android.util.Log;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
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
public class Service {

    public static final String SMARTSKED_PARTNER_KEY = "androidappbunny";

    public static final String FILTER_FACULTY = "faculty";
    public static final String FILTER_INSTITUTE = "institute";
    public static final String FILTER_GROUP = "group";
    public static final String FILTE_SCHEDULE = "schedule";
    public static final String DATA = "data";
    public static final String NOW = "now";
    public static final String LIST ="list";

    public static SmartskedService getService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SmartskedService.URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Log.d("retrofit", message);
                    }
                })
                .build();

        SmartskedService service = restAdapter.create(SmartskedService.class);
        return service;
    }



    public static Institute institute(int id) throws RetrofitError {
            return getService().institute(id, SMARTSKED_PARTNER_KEY);
    }

    public static List<Institute> institutes() throws RetrofitError {
       return getService().instituteList(SMARTSKED_PARTNER_KEY);
    }


    public static Group group(int id) throws RetrofitError {
        return getService().group(id, SMARTSKED_PARTNER_KEY);
    }

    public static List<Group> groups() throws RetrofitError {
        return getService().groupList(SMARTSKED_PARTNER_KEY);
    }

    public static Faculty faculty(int id) throws RetrofitError {
        return getService().faculty(id, SMARTSKED_PARTNER_KEY);
    }

    public static List<Faculty> facultyList() throws RetrofitError {
        return getService().facultyList(SMARTSKED_PARTNER_KEY);
    }

    public static ScheduleData scheduleData() throws RetrofitError {
        return getService().scheduleData(SMARTSKED_PARTNER_KEY);
    }

    public static ScheduleCurrent scheduleCurrent() throws RetrofitError {
        return getService().scheduleNow(SMARTSKED_PARTNER_KEY);
    }


    // ?????????????
    public static List<LessonsWrapper > schedule(int id, List<String> params) throws RetrofitError {
        return getService().schedule(id, SMARTSKED_PARTNER_KEY, params);
    }









    public static class NetworkException extends Exception {}






}
