package com.tt.hackextend.the23;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    EditText editTextEmail, editTextPasssword;
    Button signIn;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initView();
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(this,Search.class));}
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();

            }
        });
    }

    private void signin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPasssword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LogIn.this, "please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LogIn.this, "please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("signing in .. give me a sec..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                finish();
                startActivity(new Intent(LogIn.this,Search.class));
            }
        });

    }

    private void initView() {
        editTextEmail= (EditText) findViewById(R.id.editTextEmailSI);
        editTextPasssword= (EditText) findViewById(R.id.editTextPasswordSI);
        signIn= (Button) findViewById(R.id.btnSignIn);
        progressDialog=new ProgressDialog(this);


    }
}
