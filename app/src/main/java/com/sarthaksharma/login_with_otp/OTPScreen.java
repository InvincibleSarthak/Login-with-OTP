package com.sarthaksharma.login_with_otp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

public class OTPScreen extends Activity {
    EditText otp;
    private FirebaseAuth mAuth;
    String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);
        mAuth = FirebaseAuth.getInstance();
        otp = findViewById(R.id.otp);
        String mobile_number = getIntent().getStringExtra("phone_number");
        sendVerificationCode(mobile_number);
    }
    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + number,60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, mCallBack
        );
    }
    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(OTPScreen.this,Profile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(OTPScreen.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String s = phoneAuthCredential.getSmsCode();
            if(s!=null){
                verifyCode(s);
            }
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OTPScreen.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };
    public void loginButton(View view) {
        String code = otp.getText().toString().trim();
        if(code.isEmpty()){
            otp.setError("Please Enter The OTP!");
            otp.requestFocus();
            return;
        }
        verifyCode(code);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent = new Intent(OTPScreen.this,Profile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
