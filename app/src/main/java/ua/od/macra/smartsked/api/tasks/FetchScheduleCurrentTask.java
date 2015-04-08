package ua.od.macra.smartsked.api.tasks;

import android.os.AsyncTask;

import ua.od.macra.smartsked.api.entitities.ScheduleCurrent;
import ua.od.macra.smartsked.api.rest.Service;

/**
 * Created by Roodie on 08.04.2015.
 */
public class FetchScheduleCurrentTask extends AsyncTask <Void, Void,ScheduleCurrent> {

    boolean networkError = false;
    private FetchScheduleCurrentTaskCallback callback;

    public FetchScheduleCurrentTask(FetchScheduleCurrentTaskCallback callback) {
        this.callback = callback;
    }

    @Override
    protected ScheduleCurrent doInBackground(Void... params) {
        try {
            ScheduleCurrent event = Service.scheduleCurrent();
            return event;
        } catch (Exception ex) {
            networkError = true;
            return null;
        }

    }

    @Override
    protected void onPostExecute(ScheduleCurrent event) {
        super.onPostExecute(event);
        try {
            if (callback != null){
                if (networkError)
                    callback.onNetworkError();
                else
                    callback.onFetchScheduleCurrentTaskCallback(event);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public interface FetchScheduleCurrentTaskCallback {

        public void onFetchScheduleCurrentTaskCallback(ScheduleCurrent event);

        public void onNetworkError();
    }


}
