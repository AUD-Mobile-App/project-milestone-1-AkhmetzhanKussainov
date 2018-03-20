package com.example.akhmet.audbucketlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    String login,password;
    String testLogin, testPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText txtLogin=findViewById(R.id.txtLogin);
        final EditText txtPassword=findViewById(R.id.txtPassword);
        Button btnSign=findViewById(R.id.btnSignIn);


        testLogin ="admin";
        testPassword ="password";


        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login=txtLogin.getText().toString();
                password=txtPassword.getText().toString();
                if(login.equals(testLogin) && (password.equals(testPassword)))
                {
                    finish();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
            }
        });



    }
}
