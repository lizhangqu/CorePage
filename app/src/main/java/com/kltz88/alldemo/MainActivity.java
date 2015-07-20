package com.kltz88.alldemo;

import android.os.Bundle;

import com.kltz88.alldemo.page.BaseActivity;

public class MainActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openPage("test1",null,false,true);

    }

}
