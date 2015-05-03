package com.roodie.common.utils;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Roodie on 29.04.2015.
 */
public class BusProvider {

    private static final Bus REST_BUS = new Bus(ThreadEnforcer.ANY);
    private static final Bus UI_BUS = new Bus();

    public BusProvider() {
    }

    ;

    public static Bus getRestBusInstance() {
        return REST_BUS;
    }

    public static Bus getUiBusInstance() {
        return UI_BUS;
    }

}
