package cchao.org.weatherapp.utils;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import cchao.org.weatherapp.MyBus;

/**
 * Created by chenchao on 15/11/27.
 */
public class BusUtil {
    private static MyBus bus;

    public synchronized static Bus getBus() {
        if (bus == null) {
            bus = new MyBus(ThreadEnforcer.ANY);
        }
        return bus;
    }
}