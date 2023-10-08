package com.ksapps.uvote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class VotingActivity extends AppCompatActivity {

    private Button btnB1, btnB2, btnB3, btnConfirm;
    private TextView tvResults, tvThank, tvWard;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference myRef, myRef1, myRef2;
    private FirebaseUser user;
    private int value, count;
    private String ward, candidate;
    private ArrayList<String> arrayList;
    private ArrayList<Integer> countList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        btnB1 = (Button) findViewById(R.id.btnB1);
        btnB2 = (Button) findViewById(R.id.btnB2);
        btnB3 = (Button) findViewById(R.id.btnB3);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        tvResults = (TextView) findViewById(R.id.tvResults);
        tvThank = (TextView) findViewById(R.id.tvThank);
        tvWard = (TextView) findViewById(R.id.tvWard);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("voters");
        myRef1 = database.getReference().child("candidates");
        myRef2 = database.getReference().child("count");
        user = auth.getCurrentUser();
        arrayList = new ArrayList<>();
        countList = new ArrayList<>();

        myRef.child(user.getDisplayName()).child("voted").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {
                            value = Integer.parseInt(snapshot.getValue().toString());
                            if (value == 0) {
                                btnB1.setEnabled(true);
                                btnB2.setEnabled(true);
                                btnB3.setEnabled(true);
                                tvThank.setVisibility(View.INVISIBLE);
                            } else {
                                btnB1.setEnabled(false);
                                btnB2.setEnabled(false);
                                btnB3.setEnabled(false);
                                tvThank.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("TAG", " it's null.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.child(user.getDisplayName()).child("ward").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ward = dataSnapshot.getValue().toString();
                tvWard.setText("Ward : " + ward);
                Log.e("U-Vote", "My ward is " + ward);
                myRef1.child(ward).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Log.e("U-Vote",Long.toString(dataSnapshot.getChildren()));
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            arrayList.add(child.getValue().toString());
                            //String count_no=dataSnapshot.child(child.getValue().toString()).getValue().toString();
                            Log.e("U-Vote", child.getValue() + " my ocunt");
                            //countList.add(Integer.parseInt(count_no));
                        }
                        for (int i = 0; i < arrayList.size(); i++) {
                            myRef2.child(ward).child(arrayList.get(i)).
                                    addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Log.e("U-Vote", "Count " + dataSnapshot.getValue().toString());
                                            countList.add(Integer.parseInt(dataSnapshot.getValue().toString()));
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                        }
                        Log.e("U-Vote", arrayList.toString());
                        btnB1.setText(arrayList.get(0));
                        btnB2.setText(arrayList.get(1));
                        btnB3.setText(arrayList.get(2));
                        doVote();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        myRef.child(user.getDisplayName()).child("ward").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                try {
//                    if (snapshot.getValue() != null) {
//                        try {
//                            ward = snapshot.getValue().toString();
//                            btnB1
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        Log.e("TAG", " it's null.");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

    private void doVote() {
        btnB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnB1.setEnabled(false);
                btnB2.setEnabled(false);
                btnB3.setEnabled(false);

                //Log.e("U-Vote",ward+" "+btnB3.getText().toString());

                myRef = database.getReference().child("count").child(ward).child(btnB1.getText().toString());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        count = Integer.parseInt(dataSnapshot.getValue().toString());
                        Log.e("U-Vote", dataSnapshot.getValue().toString() + " count");
                        candidate = btnB1.getText().toString();
                        btnConfirm.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        btnB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnB1.setEnabled(false);
                btnB2.setEnabled(false);
                btnB3.setEnabled(false);

                //Log.e("U-Vote",ward+" "+btnB3.getText().toString());

                myRef = database.getReference().child("count").child(ward).child(btnB2.getText().toString());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        count = Integer.parseInt(dataSnapshot.getValue().toString());
                        Log.e("U-Vote", dataSnapshot.getValue().toString() + " count");
                        candidate = btnB2.getText().toString();
                        btnConfirm.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        btnB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnB1.setEnabled(false);
                btnB2.setEnabled(false);
                btnB3.setEnabled(false);

                //Log.e("U-Vote",ward+" "+btnB3.getText().toString());

                myRef = database.getReference().child("count").child(ward).child(btnB3.getText().toString());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        count = Integer.parseInt(dataSnapshot.getValue().toString());
                        Log.e("U-Vote", dataSnapshot.getValue().toString() + " count");
                        candidate = btnB3.getText().toString();
                        btnConfirm.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                database.getReference().child("count").child(ward).child(candidate).setValue(Integer.toString(count));
                database.getReference().child("voters").child(user.getDisplayName()).child("voted").setValue("1");
                btnConfirm.setEnabled(false);
            }
        });

        tvResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VotingActivity.this,ResultsActivity.class);
                i.putExtra("countList",countList);
                i.putExtra("arrayList",arrayList);
                startActivity(i);
            }
        });
    }
}
