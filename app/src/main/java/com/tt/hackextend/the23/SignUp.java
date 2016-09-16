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

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPasssword, editTextPhone,
            editTextCity, editTextSkill, editTextSkillWant,editTextName;
    Button signup;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, Search.class));
        }
        signup.setOnClickListener(this);

    }

    private void initView() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPasssword = (EditText) findViewById(R.id.editTextPasswordSI);
        signup = (Button) findViewById(R.id.btnsubmit);
        progressDialog = new ProgressDialog(this);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextCity = (EditText) findViewById(R.id.editTextCity);
        editTextSkill = (EditText) findViewById(R.id.editTextSkillGot);
        editTextSkillWant = (EditText) findViewById(R.id.editTextSkillWant);
        editTextName = (EditText) findViewById(R.id.editTextName);
        user=new User();
    }

    @Override
    public void onClick(View view) {
     if (view == signup) {
            registerUser();
        }
    }

    private void registerUser() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPasssword.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String skill = editTextSkill.getText().toString().trim();
        String skillWant = editTextSkillWant.getText().toString().trim();
        String Name=editTextName.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(SignUp.this, "please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(SignUp.this, "please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(SignUp.this, "please enter phone", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(city)) {
            Toast.makeText(SignUp.this, "please enter city", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(skill)) {
            Toast.makeText(SignUp.this, "please enter your skill", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(skillWant)) {
            Toast.makeText(SignUp.this, "please enter skill desired", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("registering .. give me a sec..");
        progressDialog.show();
        user.name=Name;
        user.city=city;
        user.base_skill=skill;
        user.email=email;
        user.want_skill=skillWant;
        user.phone_number=phone;

        UserClient client = new UserClient(this);
        client.createUser(user);


        firebaseAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "user created successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(SignUp.this, Search.class));
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(SignUp.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }


}
