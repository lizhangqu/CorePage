package com.kltz88.alldemo.page;

import android.app.Application;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-20
 * Time: 15:38
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PageManager.getInstance().init(this);
    }
}
