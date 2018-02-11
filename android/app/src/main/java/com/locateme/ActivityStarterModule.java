package com.locateme;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
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
    public void startActivityForResult(String activityName, final Callback activityResultCallback) {
        try {
            getReactApplicationContext().startActivityForResult(
                    new Intent(getReactApplicationContext(), Class.forName(activityName)), 1, null);
            getReactApplicationContext().addActivityEventListener(new ActivityEventListener() {
                @Override
                public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
                    if ((requestCode == 1) && (resultCode == 1)) {
                        activityResultCallback.invoke(data.getStringExtra("idToken"));
                    }
                }

                @Override
                public void onNewIntent(Intent intent) {

                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
