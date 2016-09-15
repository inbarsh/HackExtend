package com.tt.hackextend.the23;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewOtherProfile extends AppCompatActivity {

    ImageView profilePic, callButton, messegeButton;
    TextView wantedSkillContent, myskillContent, userName, city;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_profile);
        initView();
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        initFields();

    }



    private void initView() {
        profilePic = (ImageView) findViewById(R.id.ImageViewProfilePicVP);
        userName = (TextView) findViewById(R.id.TextViewUserNameVp);
        city = (TextView) findViewById(R.id.textViewCityVP);
        myskillContent = (TextView) findViewById(R.id.textViewMySkillContentVP);
        wantedSkillContent = (TextView) findViewById(R.id.txtViewWantedSkillContentVP);
        messegeButton = (ImageView) findViewById(R.id.imageViewEmailVP);
        callButton = (ImageView) findViewById(R.id.imageViewCallVP);
    }

    public void clickedMail(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{user.email});
        i.putExtra(Intent.EXTRA_SUBJECT, "A Message From Time Bank");
        i.putExtra(Intent.EXTRA_TEXT, "I want to learn "+user.want_skill);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ViewOtherProfile.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickedPhone(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + user.phone_number));
        startActivity(intent);
    }


    private void initFields() {
        userName.setText(user.name);
        city.setText(user.city);
        myskillContent.setText(user.base_skill);
        wantedSkillContent.setText(user.want_skill);
    }
}

