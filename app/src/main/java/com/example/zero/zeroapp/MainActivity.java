package com.example.zero.zeroapp;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import ui.fragment.ContentFragment;

public class MainActivity extends FragmentActivity {
    private static final String TAG_CONTENT = "TAG_CONTENT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        WindowManager wm= (WindowManager) getSystemService(WINDOW_SERVICE);
        int width=wm.getDefaultDisplay().getWidth();
        int height=wm.getDefaultDisplay().getHeight();

        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();

        transaction.replace(R.id.fl_main, new ContentFragment(), TAG_CONTENT);
        transaction.commit();// 提交事务
    }
}
