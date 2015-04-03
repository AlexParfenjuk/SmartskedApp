package ua.od.macra.smartsked;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileLoader extends AsyncTask<String, Void, String> {

    private static final String LOG_TAG = FileLoader.class.getSimpleName();

    Activity mContext;

    public FileLoader(Activity context) {
        super();
        mContext = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String fileName = strings[0];
        String resultString = "";
        try {
            FileInputStream fis = mContext.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            int content;
            StringBuilder sb = new StringBuilder();
            while ((content = isr.read()) != -1) {
                sb.append((char) content);
            }
            resultString = sb.toString();
            isr.close();
            fis.close();
        } catch (IOException e) {
            Log.d(LOG_TAG, "File not exist");
        }
        return resultString;
    }
}