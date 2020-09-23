package com.QiSheng.ShopBoss.welcome;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;

import com.QiSheng.ShopBoss.R;
import com.QiSheng.ShopBoss.base.BaseAvtivity;
import com.QiSheng.ShopBoss.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseAvtivity {


    @BindView(R.id.bt_getphone)
    Button btGetphone;

    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void getData() {
        btGetphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}