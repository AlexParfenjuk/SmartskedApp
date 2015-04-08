package ua.od.macra.smartsked.api.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import ua.od.macra.smartsked.api.entitities.Faculty;
import ua.od.macra.smartsked.api.rest.Service;

/**
 * Created by Roodie on 08.04.2015.
 */
public class FetchFacultiesTask extends AsyncTask<Void, Void, ArrayList<Faculty> >{

    boolean networkError = false;
    private FetchFacultiesTaskCallback callback;

    public FetchFacultiesTask(FetchFacultiesTaskCallback callback) {
        this.callback = callback;
    }

    @Override
    protected ArrayList<Faculty> doInBackground(Void... params) {
        try {
            List<Faculty> facultyList = Service.facultyList();
            return (ArrayList<Faculty>) facultyList;
        } catch (Exception ex) {
            networkError = true;
            return null;
        }

    }

    @Override
    protected void onPostExecute(ArrayList<Faculty> faculties) {
        super.onPostExecute(faculties);
        try {
            if (callback != null){
                if (networkError)
                    callback.onNetworkError();
                else
                    callback.onFetchFacultiesTaskCallback(faculties);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public interface FetchFacultiesTaskCallback {

        public void onFetchFacultiesTaskCallback(ArrayList<Faculty> faculties);

        public void onNetworkError();
    }
}
