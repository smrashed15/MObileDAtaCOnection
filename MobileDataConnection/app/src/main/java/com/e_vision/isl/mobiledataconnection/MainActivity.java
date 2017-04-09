package com.e_vision.isl.mobiledataconnection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {


    ImageButton tBMobileData;
    boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tBMobileData = (ImageButton) findViewById(R.id.tBMobileData);
        mobilecheack();

        tBMobileData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                mobilecheack();

                if (state) {
                    toggleMobileDataConnection(false);
                    tBMobileData.setImageResource(R.drawable.dataoff);
                } else {
                    toggleMobileDataConnection(true);
                    tBMobileData.setImageResource(R.drawable.dataon);
                }
            }
        });

    }

    private void mobilecheack() {

        state = isMobileDataEnable();

        if (state) {
            tBMobileData.setImageResource(R.drawable.dataon);
        } else {
            tBMobileData.setImageResource(R.drawable.dataoff);
        }

    }

    public void updateUI1(boolean state) {

        if (state) {
            tBMobileData.setImageResource(R.drawable.dataoff);


        } else {
            tBMobileData.setImageResource(R.drawable.dataon);

        }
    }

    public boolean isMobileDataEnable() {

        boolean mobileDataEnabled = false;
        ConnectivityManager cm = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true);
            mobileDataEnabled = (Boolean) method.invoke(cm);
        } catch (Exception e) {
        }
        return mobileDataEnabled;
    }

    public boolean toggleMobileDataConnection(boolean ON) {
        try {

            final ConnectivityManager conman = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            final Class conmanClass = Class.forName(conman.getClass().getName());
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);
            setMobileDataEnabledMethod.invoke(iConnectivityManager, ON);

        } catch (Exception e) {

        }
        return true;
    }

}
