/*
@author: Shivang Bokolia
This application is developed to check the security system of an android device.
The security evaluation is provided in form of a score out of 4 where:
0 - NO Risk to the Device
1 - LOW Risk to the Device
2 - MEDIUM Risk to the Device
3 - HIGH Risk to the Device
4 - CRITICAL Risk to the Device
 */

package com.example.androidsecurity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private final HashSet<String> facts = new HashSet<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidOSVersion();
        developerOptions();
        deviceLockOptions();
        wifiCheck();
        nonPlayStoreApps();

        Button startButton = (Button) findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            // Clicking the Start Button
            public void onClick(View v) {
                Intent evaluation = new Intent(MainActivity.this, Evaluation.class);
                String[] factArray = new String[facts.size()];
                facts.toArray(factArray);
                final ArrayList<String> factList = new ArrayList<String>(Arrays.asList(factArray));
                evaluation.putStringArrayListExtra("facts", factList);
                startActivity(evaluation);
            }
        });
    }

    // This function checks the android OS version of the device.
    private void androidOSVersion(){
        String androidOSVersion = Build.VERSION.RELEASE;
        if (androidOSVersion.equals("11")) {
            facts.add("LATEST OS VERSION");
        }
        if (androidOSVersion.equals("10")){
            facts.add("PREVIOUS OS VERSION");
        }
        if (Integer.parseInt(androidOSVersion) < 10)
        {
            facts.add("OUTDATED OS VERSION");
        }
    }

    // This function checks the Developer Options of the device.
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void developerOptions(){
        int DO = Settings.Secure.getInt(this.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0);
        if (DO == 1){
            facts.add("DEVELOPER OPTIONS ENABLED");
        }
        if (DO == 0){
            facts.add("DEVELOPER OPTIONS DISABLED");
        }
    }

    // This function checks if the device has a lock or not.
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void deviceLockOptions(){
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isDeviceSecure()){
            facts.add("DEVICE LOCK ENABLED");
        }
        if (!keyguardManager.isDeviceSecure()){
            facts.add("DEVICE LOCK DISABLED");
        }
    }

    // This function checks if the device is connected to a bad network or not.
    private void wifiCheck(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
            facts.add("PUBLIC WiFi CONNECTED");
        }
        if (!connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected())
        {
            facts.add("WiFi NOT CONNECTED");
        }
    }

    // This function checks if non Google PlayStore apps are allowed on the device or not.
    private void nonPlayStoreApps(){
        try {
            if (Settings.Secure.getInt(getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS) == 1){
                facts.add("APPS ARE NOT VERIFIED");
            }
            if (Settings.Secure.getInt(getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS) == 0){
                facts.add("APPS ARE VERIFIED");
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }
}