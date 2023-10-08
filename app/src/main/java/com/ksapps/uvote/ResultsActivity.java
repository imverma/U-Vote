package com.ksapps.uvote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ResultsActivity extends AppCompatActivity {

    private ArrayList<Integer> countList;
    private ArrayList<String> arrayList;
    private ArrayList<String> winner;
    private TextView tvWinner;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference myRef, myRef1, myRef2;
    private FirebaseUser user;
    private String ward;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        tvWinner = (TextView) findViewById(R.id.tvWinner);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("voters");
        user = auth.getCurrentUser();

        Intent intent = getIntent();
        arrayList = intent.getStringArrayListExtra("arrayList");
        countList = intent.getIntegerArrayListExtra("countList");
        winner = new ArrayList<>();

        myRef.child(user.getDisplayName()).child("ward").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ward = dataSnapshot.getValue().toString();
                for (int i = 0; i < arrayList.size(); i++) {
                    if (countList.get(i).equals(Collections.max(countList))) {
                        winner.add(arrayList.get(i));
                    }
                }

                count = countList.get(arrayList.indexOf(winner.get(0)));

                if (winner.size() == 1) {
                    tvWinner.setText("The winner of ward " + ward + " is " + winner.get(0) + " with " + count+" votes");
                } else {
                    StringBuffer sb = new StringBuffer();
                    sb.append("There is a tie in ward " + ward +" between ");
                    for(int i=0;i<winner.size()-2;i++){
                        sb.append(winner.get(i)+ ", ");
                    }
                    sb.append(winner.get(winner.size()-2)+" ");
                    sb.append("and "+winner.get(winner.size()-1)+" with "+Collections.max(countList)+" votes");
                    tvWinner.setText(sb.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
