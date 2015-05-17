package com.fang.chinaindex.questionnaire.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.ui.fragment.LoginFragment;


/**
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance(), LoginFragment.TAG)
                    .commit();
        }
    }

}
