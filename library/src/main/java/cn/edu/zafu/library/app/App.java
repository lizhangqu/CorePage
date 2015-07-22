package cn.edu.zafu.library.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import cn.edu.zafu.library.page.PageManager;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 09:35
 */
public class App extends Application {
    private static LocalBroadcastManager mLocalBroadcatManager;
    private static Context mContext;
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        mContext = this.getApplicationContext();
        PageManager.getInstance().init(this);
    }
    public static Context getContext() {
        return mContext;
    }
    public static LocalBroadcastManager getLocalBroadcastManager() {
        if (mLocalBroadcatManager == null) {
            mLocalBroadcatManager = LocalBroadcastManager.getInstance(mContext);
        }
        return mLocalBroadcatManager;
    }
}
