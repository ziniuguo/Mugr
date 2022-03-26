package com.example.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.models.ThreadClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class PostActivity extends AppCompatActivity {
    EditText contentText;
    EditText titleText;
    Button buttonPush;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://android-firebase-9538d-default-rtdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference myRef = database.getReference("Threads");

    // set toolbar
    Toolbar postToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Intent intent = getIntent();
        String userId = intent.getStringExtra(MainActivity.USERID);

        // set toolbar back arrow
        postToolbar = findViewById(R.id.postToolbar);
        setSupportActionBar(postToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        contentText = findViewById(R.id.threadContent);
        titleText = findViewById(R.id.threadTitle);
        buttonPush = findViewById(R.id.pushData);
        buttonPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleText.getText().toString().equals("") || contentText.getText().toString().equals("")) {
                    Toast.makeText(PostActivity.this, R.string.empty_title_or_content, Toast.LENGTH_LONG).show();
                } else if (!titleText.getText().toString().equals("Posts")) {
                    ThreadClass threadClassObject = new ThreadClass(userId, titleText.getText().toString(), contentText.getText().toString());
                    DatabaseReference pushRef = myRef.child(Objects.requireNonNull(myRef.push().getKey()));
                    pushRef.child("userId").setValue(threadClassObject.getUserId());
                    pushRef.child("status").setValue(threadClassObject.getStatus());
                    pushRef.child("rating").setValue(threadClassObject.getRating());
                    pushRef.child("title").setValue(threadClassObject.getTitle());
                    pushRef.child("thread").setValue(threadClassObject.getThread());
                    finish();
                } else {
                    Toast.makeText(PostActivity.this, R.string.cannot_name_title_as_posts, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    // control the back arrow
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // onBackPressed();
            // i think onBackPressed also can lah
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
