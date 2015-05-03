package com.roodie.domain;

import com.roodie.common.utils.BusProvider;
import com.roodie.model.entitities.LessonsWrapper;
import com.roodie.model.rest.RestScheduleSource;
import com.squareup.otto.Bus;

import java.util.List;

/**
 * Created by Roodie on 03.05.2015.
 */

/**
 * An implementation of class
 *
 * @link GetScheduleUsecase
 */
public class GetScheduleUsecaseController implements GetScheduleUsecase {

    private final RestScheduleSource scheduleSource;
    private final Bus mUiBus;
    private final String mScheduleId;
    private List<String> params;

    /**
     * Constructor of the class
     *
     * @param scheduleSource The data source to retrieve the schedule
     * @param mUiBus         The bus to communicate the domain module and the app module
     * @param mScheduleId    The id of the group to retrieve the schedule
     * @param params         The parameters(dates) of the schedule
     */

    public GetScheduleUsecaseController(RestScheduleSource scheduleSource, Bus mUiBus, String mScheduleId, List<String> params) {

        if (mScheduleId == null)
            throw new IllegalArgumentException("Id cannot be null");

        if (params == null)
            throw new IllegalArgumentException("Parameters cannot be null");

        if (scheduleSource == null)
            throw new IllegalArgumentException("ScheduleSource cannot be null");

        if (mUiBus == null)
            throw new IllegalArgumentException("Bus cannot be null");

        this.scheduleSource = scheduleSource;
        this.mUiBus = mUiBus;
        this.mScheduleId = mScheduleId;
        this.params = params;

        BusProvider.getRestBusInstance().register(this);
    }

    @Override
    public void requestSchedule(String id, List<String> params) {
        scheduleSource.getSchedule(id, params);
    }

    @Override
    public void onScheduleReceived(LessonsWrapper response) {
        sendScheduleToPresenter(response);
    }

    @Override
    public void sendScheduleToPresenter(LessonsWrapper response) {
        mUiBus.post(response);
    }

    @Override
    public void execute() {
        requestSchedule(mScheduleId, params);
    }

    @Override
    public void unRegister() {
        BusProvider.getRestBusInstance().unregister(this);
    }
}
