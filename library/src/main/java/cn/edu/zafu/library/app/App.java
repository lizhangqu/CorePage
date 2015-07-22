package cn.edu.zafu.library.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import cn.edu.zafu.library.Config;
import cn.edu.zafu.library.page.PageManager;
import cn.edu.zafu.library.ui.BaseActivity;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 09:35
 */
public class App extends Application {
    private static LocalBroadcastManager mLocalBroadcatManager;
    private static Context mContext;
    private static App instance;

    public static Context getContext() {
        return mContext;
    }

    public static LocalBroadcastManager getLocalBroadcastManager() {
        if (mLocalBroadcatManager == null) {
            mLocalBroadcatManager = LocalBroadcastManager.getInstance(mContext);
        }
        return mLocalBroadcatManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = this.getApplicationContext();
        PageManager.getInstance().init(this);
    }

    /**
     * 发送本地广播退出程序
     */
    public static void exitApp() {
        Intent intent = new Intent();
        intent.setAction(Config.ACTION_EXIT_APP);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        App.getLocalBroadcastManager().sendBroadcast(intent);
        BaseActivity.unInit();
    }
}
