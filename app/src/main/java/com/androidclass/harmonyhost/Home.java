package com.androidclass.harmonyhost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    ListView listView;
    Button createSession_button;

    List<String> sessionsList;
    String userName = "";
    String sessionName = "";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference sessionRef;
    DatabaseReference sessionsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // Get the user name and assign it to session name
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        userName = preferences.getString("userName","");
        sessionName = userName;

        listView = findViewById(R.id.listView_sessionAvailable);
        createSession_button = findViewById(R.id.button_createSession);

        // all existing available sessions
        sessionsList = new ArrayList<>();

        createSession_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create session and add yourself as player
                createSession_button.setText("CREATING SESSION");
                createSession_button.setEnabled(false);
                sessionName = userName;
                sessionRef = firebaseDatabase.getReference("sessions/" + sessionName + "/user1");
                addSessionEventListener();
                sessionRef.setValue(userName);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Join an existing session and add yourself as a user
                sessionName = sessionsList.get(position);
                sessionRef = firebaseDatabase.getReference("sessions/" + sessionName + "/user1");
                addSessionEventListener();
                sessionRef.setValue(userName);
            }
        });

        // Display if new session is available
        addSessionsEventListener();
    }

    private void addSessionEventListener(){
        sessionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Join the session
                createSession_button.setText("CREATE SESSION");
                createSession_button.setEnabled(true);
                Intent intent = new Intent(getApplicationContext(), AccountPage.class);
                intent.putExtra("sessionName",sessionName);
                startActivity(intent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error
                createSession_button.setText("CREATE SESSION");
                createSession_button.setEnabled(true);
                Toast.makeText(Home.this,"Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addSessionsEventListener(){
        sessionsRef = firebaseDatabase.getReference("sessions");
        sessionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Display list of sessions
                sessionsList.clear();
                Iterable<DataSnapshot> sessions = dataSnapshot.getChildren();
                for(DataSnapshot snapshot: sessions){
                    sessionsList.add(snapshot.getKey());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Home.this, android.R.layout.simple_list_item_1, sessionsList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}