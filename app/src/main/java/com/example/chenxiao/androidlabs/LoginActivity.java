package com.example.chenxiao.androidlabs;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.button2);
        EditText emailText = (EditText)findViewById(R.id.loginEmail);
        SharedPreferences prefEmail = getApplicationContext().getSharedPreferences("preferenceEmail",MODE_PRIVATE);
        String initialEmail = prefEmail.getString("DefaultEmail","email@domain.com");
        emailText.setText(initialEmail);
        loginButton.setOnClickListener(e ->{
            SharedPreferences.Editor editor = prefEmail.edit();
            String inputEmail = emailText.getText().toString();
            editor.putString("DefaultEmail" ,inputEmail);
            editor.commit( );

            Intent intent = new Intent(LoginActivity.this, StartActivity.class);
            startActivity(intent);

        });

        Log.i(ACTIVITY_NAME,"In onCreate()");
    }
    @Override
    protected  void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    }
    @Override
    protected  void onStart(){
        super.onStart();




        Log.i(ACTIVITY_NAME,"In onStart()");
    }
    @Override
    protected  void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }
    @Override
    protected  void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }

}
