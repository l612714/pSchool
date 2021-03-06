package com.peoit.android.online.pschool.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.peoit.android.online.pschool.R;
import com.peoit.android.online.pschool.config.CommonUtil;
import com.peoit.android.online.pschool.config.Constants;
import com.peoit.android.online.pschool.ui.Base.BaseActivity;
import com.peoit.android.online.pschool.ui.Presenter.LoginPresenter;

import java.util.Map;

/**
 * 登陆界面
 * <p/>
 * author:libo
 * time:2015/7/9
 * E-mail:boli_android@163.com
 * last: ...
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_user;
    private EditText et_pass;
    private TextView btn_login;
    private TextView tv_find;
    private String password;
    private String userName;
    private LoginPresenter presenter;

    public static void startThisActivity(Activity mAc) {
        Intent intent = new Intent(mAc, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mAc.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isMainUI = false;
        setContentView(R.layout.act_login);
        presenter = new LoginPresenter(this) {
            @Override
            protected void getUserNameAndPassword(Map<String, String> params) {
                LoginActivity.this.getUserNameAndPassword(params);
            }
        };
    }

    private void getUserNameAndPassword(Map<String, String> params) {
        if (match()) {
            params.put("userno", userName);
            params.put("password", password);
            getShare().put("pass", password);
            if("zj".equalsIgnoreCase(userName)){
                getShare().put(Constants.LOGIN_ISZHUANJIA, true);
            } else {
                getShare().put(Constants.LOGIN_ISZHUANJIA, false);
            }
        }
    }

    private boolean match() {
        userName = et_user.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            showToast("请输入用户名");
            return false;
        }
        password = et_pass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            showToast("请输入密码");
            return false;
        }
        return true;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        et_user = (EditText) findViewById(R.id.loge_et_username);
        et_pass = (EditText) findViewById(R.id.loge_et_password);
        btn_login = (TextView) findViewById(R.id.logb_btn_login);
        tv_find = (TextView) findViewById(R.id.logt_tv_find);
    }

    @Override
    public void initListener() {
        btn_login.setOnClickListener(this);
        tv_find.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_login) {
            presenter.toLogin();
        } else if (v == tv_find) {
            showToast("忘记密码");
        }
    }

    private boolean isBackPressed = false;

    @Override
    public void onBackPressed() {
        if (!isBackPressed) {
            CommonUtil.showToast("再按一次退出登录");
            isBackPressed = !isBackPressed;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isBackPressed = !isBackPressed;
                }
            }, 5 * 1000);
        } else
            finish();
    }
}
