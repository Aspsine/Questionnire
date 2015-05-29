package com.fang.chinaindex.questionnaire.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fang.chinaindex.questionnaire.App;
import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Login;
import com.fang.chinaindex.questionnaire.model.UserInfo;
import com.fang.chinaindex.questionnaire.repository.Repository;
import com.fang.chinaindex.questionnaire.ui.activity.MainActivity;
import com.fang.chinaindex.questionnaire.util.SharedPrefUtils;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LoginFragment.class.getSimpleName();

    private ImageView ivBanner;
    private EditText etUserName;
    private EditText etPassWord;

    private Button btnLogin;

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivBanner = (ImageView) view.findViewById(R.id.ivBanner);
        etUserName = (EditText) view.findViewById(R.id.etUserName);
        etPassWord = (EditText) view.findViewById(R.id.etPassWord);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        //TODO delete
        etUserName.setText("adminF2");
        etPassWord.setText("admin123");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //1.判断userId是否为空
        //2.判断权限是否过期
        //3.如果没过期则登陆
        //4.否则重新登陆
        String userId = SharedPrefUtils.getUserId(getActivity());
        String permissionEndTime = SharedPrefUtils.getUserPermissionEndTime(getActivity());

        if (!TextUtils.isEmpty(userId)) {
            if (!TextUtils.isEmpty(permissionEndTime) && hasPermission(new Date(permissionEndTime))) {
                intentToMain();
            } else {
                Toast.makeText(getActivity(), "账号权限过期，请重新登陆.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean hasPermission(Date date) {
        return date.after(new Date(System.currentTimeMillis()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        String userName = String.valueOf(etUserName.getText());
        String password = String.valueOf(etPassWord.getText());
        check(userName, password);
    }

    /**
     * @param userName
     * @param passWord
     */
    private void check(String userName, String passWord) {
        if (TextUtils.isEmpty(userName.trim())) {
            etUserName.setError(getString(R.string.login_et_error_username_empty));
            return;
        }
        if (TextUtils.isEmpty(passWord.trim())) {
            etPassWord.setError(getString(R.string.login_et_error_password_empty));
            return;
        }
        login(userName, passWord);
    }

    private void intentToMain() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * save user info to db and sharedPref
     * @param login
     */
    private void saveUserInfo(Login login) {
        UserInfo userInfo = login.getUserInfo();
        SharedPrefUtils.saveUserInfo(getActivity(), userInfo);
        App.getDaoSession().getUserDao().save(userInfo);
    }

    /**
     * @param userName
     * @param passWord
     */
    private void login(String userName, String passWord) {
        show(getString(R.string.login_progressbar_msg));
        App.getRepository().Login(userName, passWord, new Repository.Callback<Login>() {
            @Override
            public void success(Login login) {
                dismiss();
                Activity activity = getActivity();
                if (activity == null || activity.isFinishing()) {
                    return;
                }
                if (login == null) {
                    Toast.makeText(activity, getString(R.string.login_error_exception), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(login.getErrorMessage())) {
                    saveUserInfo(login);
                    intentToMain();
                } else {
                    //TODO
                    Toast.makeText(activity, login.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(Exception e) {
                dismiss();
                Activity activity = getActivity();
                if (activity == null || activity.isFinishing()) {
                    return;
                }
                Toast.makeText(activity, getString(R.string.login_error_exception), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
