package com.roodie.domain;

import com.roodie.model.entitities.LessonsWrapper;

import java.util.List;

/**
 * Created by Roodie on 03.05.2015.
 */

/*
 * Representetion of an use case to get the schedule of the group
 */
public interface GetScheduleUsecase extends Usecase {

    /**
     * Request datasources the schedule of the group
     *
     * @param id     The id of the group
     * @param params Parameters(dates) of the schedule
     */
    public void requestSchedule(String id, List<String> params);

    /**
     * Callback used to be notified when the Schedule has been
     * received
     *
     * @param response The response, containing the schedule of the group
     */
    public void onScheduleReceived(LessonsWrapper response);

    /**
     * Sends the ScheduleResponse thought the communication system
     * to be received by the presenter
     *
     * @param response the response containing the shedule
     */
    public void sendScheduleToPresenter(LessonsWrapper response);

    public void unRegister();
}
