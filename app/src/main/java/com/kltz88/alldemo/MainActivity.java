package com.kltz88.alldemo;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import cn.edu.zafu.library.page.PageManager;
import cn.edu.zafu.library.ui.BaseActivity;


public class MainActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //openPage("test1",null,false,true);
        Map<String, String> params = new HashMap<>();
        params.put("key1","value1");
        params.put("key2","value2");
        PageManager.getInstance().putPage("test3",TestFragment1.class,null);
    }

}
