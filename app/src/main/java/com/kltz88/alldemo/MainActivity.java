package com.kltz88.alldemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.edu.zafu.library.anim.Anim;
import cn.edu.zafu.library.switcher.SwitchBean;
import cn.edu.zafu.library.ui.BaseActivity;
import cn.edu.zafu.library.ui.BaseFragment;


public class MainActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openPage("test1", null, Anim.none, true);
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchBean bean=new SwitchBean("test2",null, Anim.slide,true,true);
                BaseFragment fragment=getActiveFragment();

                openPageForResult(bean, getActiveFragment());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Test", requestCode + " " + resultCode + " " + data.getStringExtra("test1"));
        super.onActivityResult(requestCode, resultCode, data);
    }
}
