/*
@author: Shivang Bokolia
This class acts as the expert shell to apply rules on the facts that were generated from the
info from the device and using those facts more facts are generated in this class along with the
final score for the security of the device.
 */

package com.example.androidsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class Evaluation extends AppCompatActivity {

    private TextView thirdPartyTB;
    private CheckBox thirdPartyCB;
    private ArrayList<String> facts;
    private int totalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluation);

        Intent intent = getIntent();
        facts = intent.getStringArrayListExtra("facts");

        // Setting the description for all the info taken from the device

        // OS Description
        TextView androidOSTB = (TextView) findViewById(R.id.osTB);
        androidOSTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Evaluation.this, Description.class);
                i.putExtra("description", "2 - the OS of your device is outdated\n\n1 - the OS is the previous version\n\n0 - the latest OS is installed on device");
                startActivity(i);
            }
        });

        // Developer Options Description
        TextView developerTB = (TextView) findViewById(R.id.doTB);
        developerTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Evaluation.this, Description.class);
                i.putExtra("description", "0 - your developer options on the device are disabled\n\n1 - your developer options on the device are enabled");
                startActivity(i);
            }
        });

        // Device Lock Description
        TextView lockTB = (TextView) findViewById(R.id.dlTB);
        lockTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Evaluation.this, Description.class);
                i.putExtra("description", "0 - your device has a lock on\n\n1 - your device has no lock");
                startActivity(i);
            }
        });

        // Verification of Applications
        TextView vAppsTB = (TextView) findViewById(R.id.vaTB);
        vAppsTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Evaluation.this, Description.class);
                i.putExtra("description", "0 - your applications are verified for to not allow third party applications\n\n1 - your device allows third party applications");
                startActivity(i);
            }
        });

        // Network Description
        TextView netTB = (TextView) findViewById(R.id.wifiTB);
        netTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Evaluation.this, Description.class);
                i.putExtra("description", "0 - your device is not connected to Wi-Fi and is safe\n\n1 - your device is connected to Wi-Fi and might not be safe");
                startActivity(i);
            }
        });

        // EXPERT SHELL
        // Implementing rules to check for facts and create new facts

        // Setting Android OS Score
        TextView androidOSVersionTB = (TextView) findViewById(R.id.scoreOSTB);
        if (facts.contains("LATEST OS VERSION")){
            androidOSVersionTB.setText("0/2");
        }
        if (facts.contains("PREVIOUS OS VERSION")){
            androidOSVersionTB.setText("1/2");
        }
        if (facts.contains("OUTDATED OS VERSION")){
            androidOSVersionTB.setText("2/2");
            facts.add("SYSTEM SECURITY BREACHED");
        }

        // Setting Developer Options Score
        TextView developerOptionsTB = (TextView) findViewById(R.id.scoreDOTB);
        if (facts.contains("DEVELOPER OPTIONS ENABLED")){
            developerOptionsTB.setText("1/1");
            facts.add("SYSTEM SECURITY BREACHED");
        }
        if (facts.contains("DEVELOPER OPTIONS DISABLED")){
            developerOptionsTB.setText("0/1");
        }

        // Setting Device Lock Score
        TextView deviceLockTB = (TextView) findViewById(R.id.scoreDLTB);
        if (facts.contains("DEVICE LOCK ENABLED")){
            deviceLockTB.setText("0/1");
        }
        if (facts.contains("DEVICE LOCK DISABLED")){
            deviceLockTB.setText("1/1");
            facts.add("DEVICE SECURITY BREACHED");
        }

        // Setting Verified Application Check Score
        TextView verifiedAppTB = (TextView) findViewById(R.id.scoreVATB);
        if (facts.contains("APPS ARE NOT VERIFIED")){
            verifiedAppTB.setText("1/1");
            facts.add("APPLICATION SECURITY BREACHED");
        }
        if (facts.contains("APPS ARE VERIFIED")){
            verifiedAppTB.setText("0/1");
        }

        // Setting Network Score
        TextView networkTB = (TextView) findViewById(R.id.scoreNetTB);
        if (facts.contains("PUBLIC WiFi CONNECTED")){
            networkTB.setText("1/1");
            facts.add("NETWORK SECURITY BREACHED");
        }
        if (facts.contains("WiFi NOT CONNECTED")){
            networkTB.setText("0/1");
        }

        // Setting Third Party Application Score
        thirdPartyTB = (TextView) findViewById(R.id.scoreTPATB);
        thirdPartyTB.setText("0/1");
        thirdPartyCB = (CheckBox) findViewById(R.id.thirdpartyCB);
        thirdPartyCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thirdPartyCB.isChecked()){
                    thirdPartyTB.setText("1/1");
                    facts.add("APPLICATION SECURITY BREACHED");
                }
                if (!thirdPartyCB.isChecked()){
                    thirdPartyTB.setText("0/1");
                }
            }
        });

        // Checking the derived facts for Final Score
        if (facts.contains("SYSTEM SECURITY BREACHED")){
            totalScore += 1;
        }
        if (facts.contains("DEVICE SECURITY BREACHED")){
            totalScore += 1;
        }
        if (facts.contains("NETWORK SECURITY BREACHED")){
            totalScore += 1;
        }
        if (facts.contains("APPLICATION SECURITY BREACHED")){
            totalScore += 1;
        }

        //Clicking the Next Button
        Button nextButton = (Button) findViewById(R.id.checkSecurityButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            // Clicking the Check Button
            @Override
            public void onClick(View v) {
                Intent finalScore = new Intent(Evaluation.this, FinalScore.class);
                finalScore.putExtra("finalScore", totalScore);
                startActivity(finalScore);
            }
        });
    }
}