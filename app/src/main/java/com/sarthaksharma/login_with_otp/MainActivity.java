package com.sarthaksharma.login_with_otp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    EditText mobile_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mobile_number = findViewById(R.id.editText);
    }
    public void login(View view) {
        String number = mobile_number.getText().toString().trim();
        if(number.isEmpty()){
            mobile_number.setError("Mobile Number is required!");
            mobile_number.requestFocus();
            return;
        }
        if(number.length()<10){
            mobile_number.setError("Enter 10 digit mobile number!");
            mobile_number.requestFocus();
            return;
        }
        Intent intent = new Intent(MainActivity.this, OTPScreen.class);
        intent.putExtra("phone_number",number);
        startActivity(intent);
    }
}