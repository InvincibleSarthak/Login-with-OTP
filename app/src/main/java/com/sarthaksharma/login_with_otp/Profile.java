package com.sarthaksharma.login_with_otp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Profile extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void LogOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Profile.this,MainActivity.class);
        startActivity(intent);
    }
}
