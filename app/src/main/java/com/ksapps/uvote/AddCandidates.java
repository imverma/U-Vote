package com.ksapps.uvote;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AddCandidates extends AppCompatActivity {

    AutoCompleteTextView textIn;
    Button buttonAdd,btnSubmit;
    LinearLayout container;
    ArrayList<String> arrayList = new ArrayList<>();
    String title;
    FirebaseAuth auth;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_candidates);
        textIn = (AutoCompleteTextView)findViewById(R.id.textin);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        buttonAdd = (Button)findViewById(R.id.add);
        container = (LinearLayout) findViewById(R.id.container);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);

        Intent i =getIntent();
        title = i.getStringExtra("roomId");
        myRef = database.getReference().child("room").child(title).child("candidates");

        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(textIn.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter Candidate Name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                final AutoCompleteTextView textOut = (AutoCompleteTextView)addView.findViewById(R.id.textout);
                textOut.setText(textIn.getText().toString());
                arrayList.add(textIn.getText().toString());
                Log.e("Errorr", arrayList.toString());
                textIn.setText("");
                Button buttonRemove = (Button)addView.findViewById(R.id.remove);

                final View.OnClickListener thisListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        arrayList.remove(textOut.getText().toString());
                        Log.e("Errorr", arrayList.toString());
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }
                };
                buttonRemove.setOnClickListener(thisListener);
                container.addView(addView);

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(textOut.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Add Candidate Name!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for(int i=0;i<arrayList.size();i++){
                            myRef.child(arrayList.get(i)).setValue("0");
                        }

                        Toast.makeText(AddCandidates.this, "Candidates Added", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(AddCandidates.this, EmailVoters.class);
                        i.putExtra("roomId",title);
                        startActivity(i);

                    }
                });
            }
        });

    }

    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
