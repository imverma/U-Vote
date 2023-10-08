package com.ksapps.uvote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainPageActivity extends AppCompatActivity {


    Button btnGovt,btnPriv;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        t1=(TextView)findViewById(R.id.MainPageText1);
        btnGovt=(Button)findViewById(R.id.btnGovtElectn);
        btnPriv=(Button)findViewById(R.id.btnPrivElectn);

    btnGovt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mainToGovt =new Intent(MainPageActivity.this,GovtElectionActivity.class);
            startActivity(mainToGovt);
        }
    });

    btnPriv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mainToPriv =new Intent(MainPageActivity.this,PrivateElectionActivity.class);
            startActivity(mainToPriv);

        }
    });

    }


}
