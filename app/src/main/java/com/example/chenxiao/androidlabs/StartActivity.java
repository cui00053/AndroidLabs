package com.example.chenxiao.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button imaButton = (Button) findViewById(R.id.button1);
        imaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent,50);
            }
        });

        Log.i(ACTIVITY_NAME,"In onCreate()");
    }
    @Override
    protected void onActivityResult(int requestCode,int responseCode, Intent data){
        if(requestCode == 50){
            Log.i(ACTIVITY_NAME,"Returned to StartActivity.onActivityResult");
        }
        if(responseCode == Activity.RESULT_OK){
           String messagePassed = data.getStringExtra("Response");
           Toast.makeText(this, "ListItemsActivity passe: " + messagePassed, Toast.LENGTH_LONG ).show();

        }
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
