package cchao.org.weatherapp;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by chenchao on 15/11/27.
 */
public class MyBus extends Bus{
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public MyBus(){
        super();
    }

    public MyBus(String identifier) {
        super(identifier);
    }

    public MyBus(ThreadEnforcer enforcer) {
        super(enforcer);
    }

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    post(event);
                }
            });
        }
    }
}
