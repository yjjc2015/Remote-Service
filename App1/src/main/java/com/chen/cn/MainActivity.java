package com.chen.cn;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.chen.cn.databinding.ActivityMainBinding;
import com.cl.cn.MyStub;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private MyStub mMyStub;
    private ServiceConnection mConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName pComponentName, IBinder pIBinder) {
                mMyStub = MyStub.Stub.asInterface(pIBinder);
            }
            @Override
            public void onServiceDisconnected(ComponentName pComponentName) {
                mMyStub = null;
            }
        };
        initViews();
    }

    private void initViews() {
        mBinding.btnBindSerivcec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Intent in = new Intent("com.yjjc.cn.SERVICE");
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    in.setPackage("along.chen.com.chenlong");
                }
                bindService(in, mConnection, Context.BIND_AUTO_CREATE);
            }
        });
        mBinding.btnGetStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                try {
                    mBinding.tvShowStr.setText(mMyStub.getString());
                } catch (RemoteException pE) {
                    pE.printStackTrace();
                }
            }
        });
        mBinding.tvSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                String str1 = mBinding.etNum1.getText().toString().trim();
                String str2 = mBinding.etNum2.getText().toString().trim();
                if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)) {
                    Toast.makeText(MainActivity.this, "请输入数字", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int num1 = Integer.parseInt(str1);
                    int num2 = Integer.parseInt(str2);
                    int res = mMyStub.calculate(num1, num2);
                    mBinding.tvResult.setText(String.valueOf(res));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
