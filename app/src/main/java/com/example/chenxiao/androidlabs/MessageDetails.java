package com.example.chenxiao.androidlabs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MessageDetails extends Activity {
    Bundle bundle;
    MessageFragment messageFragment;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        bundle = getIntent().getExtras();
        messageFragment = new MessageFragment();
        messageFragment.setArguments(bundle);
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,messageFragment);
        fragmentTransaction.commit();
    }
}
