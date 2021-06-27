package com.intern.snowcone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    ProgressDialog PD;
    FirebaseAuth firebaseAuth;
    String s1,s2,s3,s4;
    DatabaseReference databaseReference;
    SignupPage1 signupPage1;
    EditText name,email,phno,pass;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.suname);
        email = findViewById(R.id.suemail);
        phno = findViewById(R.id.suphone);
        pass = findViewById(R.id.supass);
        b1 = findViewById(R.id.subutton);
        firebaseAuth = FirebaseAuth.getInstance();
        signupPage1 = new SignupPage1();
        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        s1 =name.getText().toString().trim();
        s2 =email.getText().toString().trim();
        s3 =phno.getText().toString().trim();
        s4 =pass.getText().toString().trim();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((name.getText().toString().isEmpty()) || (email.getText().toString().isEmpty()) ||
                        (pass.getText().toString().isEmpty())||
                        (phno.getText().toString().isEmpty())){
                    Toast.makeText(SignUp.this, "Please fill the details", Toast.LENGTH_SHORT).show();
                }
//                else if ((s4.length()<6)){
//                    pass.setError("Password must be of 6 character length.");
//                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()){
                                        Toast.makeText(SignUp.this.getApplicationContext(),
                                                "SignUp unsuccessful: " + task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }else{
                                        signupPage1.setName(name.getText().toString().trim());
                                        signupPage1.setEmail(email.getText().toString().trim());
                                        signupPage1.setPhonenumber(phno.getText().toString().trim());
                                        databaseReference.push().setValue(signupPage1);
                                        Intent i1 = new Intent(SignUp.this,EmailSchedule.class);
                                        startActivity(i1);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}