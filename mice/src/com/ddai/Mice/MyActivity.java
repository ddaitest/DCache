package com.ddai.mice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

public class MyActivity extends FragmentActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.ddai.mice.R.layout.main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag("ImageList");
                fragment = (fragment == null ? new ImageListFragment() : fragment);
                getSupportFragmentManager().beginTransaction().add(R.id.base_layout, fragment, "ImageList").addToBackStack(null).commit();
            }
        });
    }
}
