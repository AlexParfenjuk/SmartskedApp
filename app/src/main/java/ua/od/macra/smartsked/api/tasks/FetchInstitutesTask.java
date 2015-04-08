package ua.od.macra.smartsked.api.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import ua.od.macra.smartsked.api.entitities.Institute;
import ua.od.macra.smartsked.api.rest.Service;

/**
 * Created by Roodie on 08.04.2015.
 */
public class FetchInstitutesTask extends AsyncTask <Void, Void, ArrayList<Institute>> {

    boolean networkError = false;
    private FetchInstitutesTaskCallback callback;

    public FetchInstitutesTask(FetchInstitutesTaskCallback callback) {
        this.callback = callback;
    }

    @Override
    protected ArrayList<Institute> doInBackground(Void... params) {
        try {
            List<Institute> institutesList = Service.institutes();
            return (ArrayList<Institute>) institutesList;
        } catch (Exception ex) {
            networkError = true;
            return null;
        }

    }

    @Override
    protected void onPostExecute(ArrayList<Institute> institutes) {
        super.onPostExecute(institutes);
        try {
            if (callback != null){
                if (networkError)
                    callback.onNetworkError();
                else
                    callback.onFetchInstitutesTaskCallback(institutes);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public interface FetchInstitutesTaskCallback {

        public void onFetchInstitutesTaskCallback(ArrayList<Institute> institutes);

        public void onNetworkError();
    }
}
