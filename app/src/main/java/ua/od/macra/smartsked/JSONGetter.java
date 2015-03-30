package ua.od.macra.smartsked;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ua.od.macra.smartsked.models.Strings;

public class JSONGetter {
    private JSONGetter() {
    }

    public static final String LOG_TAG = JSONGetter.class.getName();
    public static final String ERROR_URL = "Can't parse URL";
    public static final int INST_LIST = 0;
    public static final int INST_BY_ID = 1;
    public static final int FACULT_LIST = 2;
    public static final int FACULT_BY_ID = 3;
    public static final int GROUP_LIST = 4;
    public static final int GROUP_BY_ID = 5;
    public static final int SCHEDULE_ID = 6;
    public static final int SCHEDULE_DATA = 7;
    public static final int SCHEDULE_NOW = 8;

    public static String getInstList() {
        return get(INST_LIST);
    }

    public static String getInstById(int id, boolean appendFaculties) {
        List<String> argsList = new ArrayList<>();
        argsList.add(Integer.toString(id));
        argsList.add(appendFaculties ? "1" : "0");
        String[] args = argsList.toArray(new String[argsList.size()]);
        return get(INST_BY_ID, args);
    }

    public static String getFacultList() {
        return get(FACULT_LIST);
    }

    public static String getFacultById(int id, boolean appendGroups) {
        List<String> argsList = new ArrayList<>();
        argsList.add(Integer.toString(id));
        argsList.add(appendGroups ? "1" : "0");
        String[] args = argsList.toArray(new String[argsList.size()]);
        return get(FACULT_BY_ID, args);
    }

    public static String getGroupList() {
        return get(GROUP_LIST);
    }

    public static String getGroupById(int id, boolean appendLessons) {
        List<String> argsList = new ArrayList<>();
        argsList.add(Integer.toString(id));
        argsList.add(appendLessons ? "1" : "0");
        String[] args = argsList.toArray(new String[argsList.size()]);
        return get(FACULT_BY_ID, args);
    }

    public static String getScheduleById(int id, List<String> dates) {
        List<String> argsList = new ArrayList<>();
        argsList.add(Integer.toString(id));
        argsList.addAll(dates);
        String[] args = argsList.toArray(new String[argsList.size()]);
        return get(SCHEDULE_ID, args);
    }

    public static String get(int mode, String... args) {
        String jsonString = "", line;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("");
            switch (mode) {
                case INST_LIST: {
                    url = new URL(Strings.URIBUILDER
                            .appendPath("institute")
                            .appendPath("list").build().toString());
                    break;
                }
                case INST_BY_ID: {
                    String id = args[0];
                    Uri.Builder uriBuild = Strings.URIBUILDER
                            .appendPath("institute")
                            .appendPath(id)
                            .appendQueryParameter("appendFaculties", args[1].equals("1") ? "1" : "0");
                    url = new URL(uriBuild.build().toString());
                    break;
                }
                case FACULT_LIST: {
                    url = new URL(Strings.URIBUILDER
                            .appendPath("faculty")
                            .appendPath("list").build().toString());
                    break;
                }
                case FACULT_BY_ID: {
                    String id = args[0];
                    Uri.Builder uriBuild = Strings.URIBUILDER
                            .appendPath("faculty")
                            .appendPath(id)
                            .appendQueryParameter("appendGroups", args[1].equals("1") ? "1" : "0");
                    url = new URL(uriBuild.build().toString());
                    break;
                }
                case GROUP_LIST: {
                    url = new URL(Strings.URIBUILDER
                            .appendPath("group")
                            .appendPath("list").build().toString());
                    break;
                }
                case GROUP_BY_ID: {
                    String id = args[0];
                    Uri.Builder uriBuild = Strings.URIBUILDER
                            .appendPath("group")
                            .appendPath(id)
                            .appendQueryParameter("appendLessons", args[1].equals("1") ? "1" : "0");
                    url = new URL(uriBuild.build().toString());
                    break;
                }
                case SCHEDULE_ID: {
                    String id = args[0];
                    Uri.Builder uriBuild = Strings.URIBUILDER
                            .appendPath("schedule")
                            .appendPath(id);
                    for (int i = 1; i < args.length; i++) {
                        uriBuild.appendQueryParameter("dates[]=", args[i]);
                    }
                    url = new URL(uriBuild.build().toString());
                    break;
                }
                case SCHEDULE_DATA: {
                    Uri.Builder uriBuild = Strings.URIBUILDER
                            .appendPath("schedule")
                            .appendPath("data");
                    url = new URL(uriBuild.build().toString());
                    break;
                }
                case SCHEDULE_NOW: {
                    Uri.Builder uriBuild = Strings.URIBUILDER
                            .appendPath("schedule")
                            .appendPath("now");
                    url = new URL(uriBuild.build().toString());
                    break;
                }
            }
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            jsonString = sb.toString();
        } catch (IOException e) {
            Log.d(LOG_TAG, ERROR_URL);
        }
        return jsonString;
    }
}
