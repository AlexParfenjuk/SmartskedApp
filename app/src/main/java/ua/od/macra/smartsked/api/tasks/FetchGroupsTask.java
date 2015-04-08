package ua.od.macra.smartsked.api.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import ua.od.macra.smartsked.api.entitities.Group;
import ua.od.macra.smartsked.api.rest.Service;

/**
 * Created by Roodie on 08.04.2015.
 */
public class FetchGroupsTask extends AsyncTask<Void, Void, ArrayList<Group>> {

    boolean networkError = false;
    private FetchGroupsTaskCallback callback;

    public FetchGroupsTask(FetchGroupsTaskCallback callback) {
        this.callback = callback;
    }

    @Override
    protected ArrayList<Group> doInBackground(Void... params) {
        try {
            List<Group> groupList = Service.groups();
            return (ArrayList<Group>) groupList;
        } catch (Exception ex) {
            networkError = true;
            return null;
        }

    }

    @Override
    protected void onPostExecute(ArrayList<Group> groups) {
        super.onPostExecute(groups);
        try {
            if (callback != null){
                if (networkError)
                    callback.onNetworkError();
                else
                    callback.onFetchGroupsTaskCallback(groups);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public interface FetchGroupsTaskCallback {

        public void onFetchGroupsTaskCallback(ArrayList<Group> groups);

        public void onNetworkError();
    }
}
