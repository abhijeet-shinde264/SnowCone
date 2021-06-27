package com.intern.snowcone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import de.cketti.mailto.EmailIntentBuilder;

public class EmailSchedule extends AppCompatActivity {
    EditText sub,to,mess,bcc,cc;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_schedule);
        sub = findViewById(R.id.et_subject);
        to = findViewById(R.id.et_to);
        mess = findViewById(R.id.et_message);
        send = findViewById(R.id.bt_send);
        bcc = findViewById(R.id.et_bcc);
        cc = findViewById(R.id.et_cc);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailsend = to.getText().toString();
                String emailsubject = sub.getText().toString();
                String emailbody = mess.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,
                        new String[] {emailsend});
                intent.putExtra(Intent.EXTRA_BCC,
                        new String[]{bcc.getText().toString()});
                intent.putExtra(Intent.EXTRA_CC,
                        new String[]{cc.getText().toString()});
                intent.putExtra(Intent.EXTRA_SUBJECT, emailsubject);
                intent.putExtra(Intent.EXTRA_TEXT, emailbody);
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
//                Intent i1 = new Intent(Intent.ACTION_VIEW
//                , Uri.parse("mail to:"+emailsend));
//                i1.putExtra(Intent.EXTRA_SUBJECT, emailsubject);
//                i1.putExtra(Intent.EXTRA_TEXT, emailbody);
//                startActivity(i1);

//                boolean success = EmailIntentBuilder.from(EmailSchedule.this)
//                        .to(emailsend)
//                        .bcc(bcc.getText().toString())
//                        .subject(emailsubject)
//                        .body(emailbody)
//                        .start();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(EmailSchedule.this, MainActivity.class);
                startActivity(I);
                Toast.makeText(getApplicationContext(),"Login to continue", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}