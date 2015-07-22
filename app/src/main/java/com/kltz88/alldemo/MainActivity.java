package com.kltz88.alldemo;

import android.os.Bundle;

import cn.edu.zafu.library.anim.Anim;
import cn.edu.zafu.library.ui.BaseActivity;


public class MainActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openPage("test1", null, Anim.none, true);
         //openPageForResult()
    }

}
