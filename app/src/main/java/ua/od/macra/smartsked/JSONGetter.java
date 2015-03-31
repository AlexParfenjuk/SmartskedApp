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

public class JSONGetter {

    private static final String LOG_TAG = JSONGetter.class.getName();
    private static final String ERROR_URL = "Can't parse URL";
    private static final int INST_LIST = 0;
    private static final int INST_BY_ID = 1;
    private static final int FACULT_LIST = 2;
    private static final int FACULT_BY_ID = 3;
    private static final int GROUP_LIST = 4;
    private static final int GROUP_BY_ID = 5;
    private static final int SCHEDULE_ID = 6;
    private static final int SCHEDULE_DATA = 7;
    private static final int SCHEDULE_NOW = 8;

    private JSONGetter() {
    }

    public static String getInstList() {
        return get(INST_LIST);
    }

    public static String getInstById(int id, boolean appendFaculties) {
        List<String> argsList = new ArrayList<>();
        argsList.add(Integer.toString(id));
        argsList.add(appendFaculties ? "1" : "0");
        return get(INST_BY_ID, argsList.toArray(new String[argsList.size()]));
    }

    public static String getFacultList() {
        return get(FACULT_LIST);
    }

    public static String getFacultById(int id, boolean appendGroups) {
        List<String> argsList = new ArrayList<>();
        argsList.add(Integer.toString(id));
        argsList.add(appendGroups ? "1" : "0");
        return get(FACULT_BY_ID, argsList.toArray(new String[argsList.size()]));
    }

    public static String getGroupList() {
        return get(GROUP_LIST);
    }

    public static String getGroupById(int id, boolean appendLessons) {
        List<String> argsList = new ArrayList<>();
        argsList.add(Integer.toString(id));
        argsList.add(appendLessons ? "1" : "0");
        return get(FACULT_BY_ID, argsList.toArray(new String[argsList.size()]));
    }

    public static String getScheduleByGroupId(int id, List<String> dates) {
        List<String> argsList = new ArrayList<>();
        argsList.add(Integer.toString(id));
        argsList.addAll(dates);
        return get(SCHEDULE_ID, argsList.toArray(new String[argsList.size()]));
    }

    public static String getScheduleData() {
        return get(SCHEDULE_DATA);
    }

    public static String getScheduleNow() {
        return get(SCHEDULE_NOW);
    }

    private static String get(int mode, String... args) {
        return new NetworkJob(mode, args).getJsonString();
    }

    private static class NetworkJob extends Thread {

        private int mode;
        private String[] args;
        private String jsonString = "";

        public NetworkJob(int mode, String... args) {
            super();
            this.mode = mode;
            this.args = args;
        }

        public String getJsonString() {
            try {
                this.start();
                this.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return jsonString;
        }

        @Override
        public void run() {
            String line;
            final String API_KEY = "androidappbunny";
            final Uri.Builder uriBuilder = new Uri.Builder()
                    .scheme("http")
                    .authority("smartsked.com.ua")
                    .appendPath("api")
                    .appendPath("v1")
                    .appendQueryParameter("key", API_KEY);
            final StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL("http://smartsked.com.ua");
                switch (mode) {
                    case INST_LIST: {
                        url = new URL(uriBuilder
                                .appendPath("institute")
                                .appendPath("list").build().toString());
                        break;
                    }
                    case INST_BY_ID: {
                        String id = args[0];
                        Uri.Builder uriBuild = uriBuilder
                                .appendPath("institute")
                                .appendPath(id)
                                .appendQueryParameter("appendFaculties", args[1].equals("1") ? "1" : "0");
                        url = new URL(uriBuild.build().toString());
                        break;
                    }
                    case FACULT_LIST: {
                        url = new URL(uriBuilder
                                .appendPath("faculty")
                                .appendPath("list").build().toString());
                        break;
                    }
                    case FACULT_BY_ID: {
                        String id = args[0];
                        Uri.Builder uriBuild = uriBuilder
                                .appendPath("faculty")
                                .appendPath(id)
                                .appendQueryParameter("appendGroups", args[1].equals("1") ? "1" : "0");
                        url = new URL(uriBuild.build().toString());
                        break;
                    }
                    case GROUP_LIST: {
                        url = new URL(uriBuilder
                                .appendPath("group")
                                .appendPath("list").build().toString());
                        break;
                    }
                    case GROUP_BY_ID: {
                        String id = args[0];
                        Uri.Builder uriBuild = uriBuilder
                                .appendPath("group")
                                .appendPath(id)
                                .appendQueryParameter("appendLessons", args[1].equals("1") ? "1" : "0");
                        url = new URL(uriBuild.build().toString());
                        break;
                    }
                    case SCHEDULE_ID: {
                        String id = args[0];
                        Uri.Builder uriBuild = uriBuilder
                                .appendPath("schedule")
                                .appendPath(id);
                        for (int i = 1; i < args.length; i++) {
                            uriBuild.appendQueryParameter("dates[]=", args[i]);
                        }
                        url = new URL(uriBuild.build().toString());
                        break;
                    }
                    case SCHEDULE_DATA: {
                        Uri.Builder uriBuild = uriBuilder
                                .appendPath("schedule")
                                .appendPath("data");
                        url = new URL(uriBuild.build().toString());
                        break;
                    }
                    case SCHEDULE_NOW: {
                        Uri.Builder uriBuild = uriBuilder
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
                Log.d(LOG_TAG, ERROR_URL + e.getMessage());
            }
        }
    }
}
