package com.elecCoen_390_projectgroup_3.g_track;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText textEmail,textPassword;
    private Button loginBtn;
    private TextView forgotPassword,registerTextView;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    private ProgressBar progressBarLogin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBarLogin=(ProgressBar)findViewById(R.id.progressBarMain);
        textEmail= (EditText) findViewById(R.id.userNameId);
        textPassword = (EditText) findViewById(R.id.passwordId);
        loginBtn= (Button) findViewById(R.id.logInButton);
        loginBtn.setOnClickListener(this);
        registerTextView= (TextView) findViewById(R.id.registerTextView);
        registerTextView.setOnClickListener(this);
        mAuth= FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.registerTextView:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.logInButton:
                userLogin();
                break;
        }
    }

    private void userLogin() {

        String emailString= textEmail.getText().toString().trim();
        String passwordString= textPassword.getText().toString().trim();
        if(emailString.isEmpty()){textEmail.setError("Please enter the user Name!");textEmail.requestFocus();return;}
        if(!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){textEmail.setError("please enter a valid E-mail");textEmail.requestFocus();return;}
        if(passwordString.isEmpty()){textPassword.setError("Please enter the password!");textPassword.requestFocus();return;}
        if(passwordString.length() < 6){textPassword.setError("The password should be 6 char ");textPassword.requestFocus();return;}
        progressBarLogin.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //if you want to make a veyfing email uncomment the next code
//                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
//                    if(user.isEmailVerified()){ startActivity(new Intent(MainActivity.this,InfoActivity.class));}
//                    else {user.sendEmailVerification(); makeText("Please verify your Email!");}

                    //redirect to user profile

                    startActivity(new Intent(MainActivity.this,InfoActivity.class));

                }
                else
                    makeText("Please check your Email and Password!  ");
            }
        });
    }


    private void makeText(String s){
        Toast toast = Toast.makeText(this,s,Toast.LENGTH_LONG);
        toast.show();
    }
}