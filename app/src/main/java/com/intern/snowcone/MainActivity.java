package com.intern.snowcone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    //FirebaseUser user;
    private FirebaseAuth.AuthStateListener authStateListener;
    EditText email,pass;
    Button b1;
    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.lgemail);
        pass = findViewById(R.id.lgpass);
        b1 = findViewById(R.id.loginbutton);
        t1 = findViewById(R.id.signuplg);
        t2 = findViewById(R.id.fp);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ForgotPassword.class));
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(MainActivity.this, "Welcome Back ", Toast.LENGTH_SHORT).show();
                    Intent I = new Intent(MainActivity.this, EmailSchedule.class);
                    startActivity(I);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Login to continue", Toast.LENGTH_SHORT).show();
                }
            }
        };
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Pl enter the details",Toast.LENGTH_LONG).show();
                }
                else if (!(email.getText().toString().isEmpty() && pass.getText().toString().isEmpty())){
//                    PD.show();
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Not sucessfull", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent i1 = new Intent(MainActivity.this, EmailSchedule.class);
                                        startActivity(i1);
                                        finish();
                                    }
                                }
                            });
                    //PD.dismiss();
                }
                else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}