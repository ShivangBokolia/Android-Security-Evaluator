/*
@author: Shivang Bokolia
This class is used to show the description of all the factors and info retrieved from the the data.
 */

package com.example.androidsecurity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Description extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);

        Intent intent = getIntent();
        String description = intent.getStringExtra("description");

        // Setting OS description
        TextView androidDesc = (TextView) findViewById(R.id.value1TB);
        androidDesc.setText(description);

        // Back Button
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
