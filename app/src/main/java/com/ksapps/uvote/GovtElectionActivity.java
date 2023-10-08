package com.ksapps.uvote;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GovtElectionActivity extends AppCompatActivity {
    Button btnTimeSlot, btnCandInfo, btnVote, btnResult, btnHelp;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_govt_election);
        t1=(TextView)findViewById(R.id.GovtText1);
        btnTimeSlot=(Button)findViewById(R.id.gBtnTimeSlot);
        btnCandInfo=(Button)findViewById(R.id.gBtnCandInfo);
        btnVote=(Button)findViewById(R.id.gBtnVote);
        btnResult=(Button)findViewById(R.id.gBtnResult);
        btnHelp=(Button)findViewById((R.id.gBtnHelp));

        btnTimeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GovtElectToTimeSlot=new Intent(GovtElectionActivity.this,TimeSlotActivity.class);
                startActivity(GovtElectToTimeSlot);
            }
        });

        btnCandInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GovtElectToCandInfo=new Intent(GovtElectionActivity.this,CandInfoActivity.class);
                startActivity(GovtElectToCandInfo);
            }
        });

        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GovtElectToVote=new Intent(GovtElectionActivity.this,GovtVoteActivity.class);
                startActivity(GovtElectToVote);
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GovtElectToResult=new Intent(GovtElectionActivity.this,GovtResultActivity.class);
                startActivity(GovtElectToResult);
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GovtElectToHelp=new Intent(GovtElectionActivity.this,HelpActivity.class);
                startActivity(GovtElectToHelp);
            }
        });

    }

}
