package com.example.finalprojectsecurities;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MCT5.initHelper();
    }
}
