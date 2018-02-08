package com.locateme;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class ActivityStarterModule extends ReactContextBaseJavaModule {
    ActivityStarterModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @ReactMethod
    public void startActivityForResult(String activityName) {
        try {
            getReactApplicationContext().startActivityForResult(
                    new Intent(getReactApplicationContext(), Class.forName(activityName)), 1, null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
