package com.focusstudios.android.colouring;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.batch.android.Batch;
import com.batch.android.Config;

public class ColouRing extends Application{

    @Override
    public void onCreate()
    {
        super.onCreate();

        Batch.Push.setGCMSenderId("419890870481");
        //Batch.setConfig(new Config("DEV586FD0FA59D82402AEF40BA97B4")); //development
        Batch.setConfig(new Config("586FD0FA59A1328D867581DE74B316")); //live
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
