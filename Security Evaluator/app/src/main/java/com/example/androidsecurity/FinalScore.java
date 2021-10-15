/*
@author: Shivang Bokolia
This class is used to show the final score and description of the security risk to the user.
 */

package com.example.androidsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class FinalScore extends AppCompatActivity {

    private String scoreDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        int totalScore = intent.getIntExtra("finalScore", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalscore);

        // Setting the final Score
        // 0 - NO Risk to the Device
        // 1 - LOW Risk to the Device
        // 2 - MEDIUM Risk to the Device
        // 3 - HIGH Risk to the Device
        // 4 - CRITICAL Risk to the Device
        if (totalScore == 0){
            scoreDescription = "The device is at NO Risk";
        }
        if (totalScore == 1){
            scoreDescription = "The device is at LOW Risk";
        }
        if (totalScore == 2){
            scoreDescription = "The device is at MEDIUM Risk";
        }
        if (totalScore == 3){
            scoreDescription = "The device is at HIGH Risk";
        }
        if (totalScore == 4){
            scoreDescription = "The device is at CRITICAL Risk";
        }

        // Setting the final Score
        TextView finalTB = (TextView) findViewById(R.id.scoreValueTB);
        finalTB.setText(String.valueOf(totalScore));

        // Setting the final Score Description
        TextView finalDescriptionTB = (TextView) findViewById(R.id.finalScoreTextTB);
        finalDescriptionTB.setText(scoreDescription);
    }
}