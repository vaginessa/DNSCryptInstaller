package io.github.otakuchiyan.dnscryptinstaller;

import android.app.Activity;
import android.os.Bundle;

public class InstallActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle(R.string.action_install);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_install);
    }
}
