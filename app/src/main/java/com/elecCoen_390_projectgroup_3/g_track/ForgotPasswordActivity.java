package com.elecCoen_390_projectgroup_3.g_track;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class ForgotPasswordActivity extends AppCompatActivity  {
    private EditText emailToBeSent;
    private Button buttonResetPassword;
    private ProgressBar progressBarForgot;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailToBeSent=(EditText) findViewById(R.id.editTextForgotPassword);
        buttonResetPassword=(Button) findViewById(R.id.resetPassword);
        progressBarForgot=(ProgressBar) findViewById(R.id.progressBarForget);
        auth = FirebaseAuth.getInstance();
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }

            private void resetPassword() {
                String email= emailToBeSent.getText().toString().trim();

                if(email.isEmpty()){emailToBeSent.setError("E_mail is required ");emailToBeSent.requestFocus();return;}
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){emailToBeSent.setError("Please enter a valid E-mail");emailToBeSent.requestFocus();return;}
                progressBarForgot.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){makeText("Check your email to reset the password!");}
                        else {makeText("Try agin");}
                    }
                });

            }
        });


    }
    private void makeText(String s){
        Toast toast = Toast.makeText(this,s,Toast.LENGTH_LONG);
        toast.show();
    }
}