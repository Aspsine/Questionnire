package com.fang.chinaindex.questionnaire.ui.activity;


import android.os.Bundle;


import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.ui.fragment.SettingsFragment;

public class SettingsActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SettingsFragment(), SettingsFragment.TAG)
                    .commit();
        }
    }
}
