package ua.od.macra.smartsked;

import android.app.Activity;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSaver extends Thread {

    private static final String LOG_TAG = FileSaver.class.getSimpleName();

    String targetString, fileName;
    Activity mContext;

    FileSaver(Activity context, String targetString, String fileName) {
        this.targetString = targetString;
        this.fileName = fileName;
        mContext = context;
    }

    @Override
    public void run() {
        try {
            FileOutputStream fos = mContext.openFileOutput(fileName, Activity.MODE_PRIVATE);
            fos.write(targetString.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(LOG_TAG, "File not found");
        } catch (IOException e) {
            Log.d(LOG_TAG, "Error while opening output stream");
        }
    }
}