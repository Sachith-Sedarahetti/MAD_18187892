package com.example.guideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class DashBoardCat extends AppCompatActivity {

    Button Dgbook,Dsetting,Daccomo,Dsteps,Dtrans,Dprofile,Dhelp,Dassist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_cat);

        Dgbook = findViewById(R.id.gbook);
        Dsetting = findViewById(R.id.setting);
        Daccomo = findViewById(R.id.accomodation);
        Dsteps = findViewById(R.id.step);
        Dtrans = findViewById(R.id.transport);
        Dprofile = findViewById(R.id.userprof);
        Dhelp = findViewById(R.id.help);
        Dassist = findViewById(R.id.assistant);

        Dgbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GuideBook.class);
                startActivity(intent);
            }
        });
        Dsteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StepCounter.class));
            }
        });
        Dsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Settings.class));
            }
        });
        Dhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Support_Us.class));
            }
        });

        Dassist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MessageListActivity.class));
            }
        });


    }
}