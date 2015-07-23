package cn.edu.zafu.library.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import cn.edu.zafu.library.base.config.Config;
import cn.edu.zafu.library.core.CorePageManager;

/**
 *
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 09:35
 */
public class BaseApplication extends Application {
    private static LocalBroadcastManager mLocalBroadcatManager;
    private static Context mContext;
    private static BaseApplication instance;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = this.getApplicationContext();
        CorePageManager.getInstance().init(this);
    }

    /**
     * 发送本地广播退出程序
     */
    public void exitApp() {
        Intent intent = new Intent();
        intent.setAction(Config.ACTION_EXIT_APP);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        BaseApplication.getLocalBroadcastManager().sendBroadcast(intent);
        BaseActivity.unInit();
    }

    public static LocalBroadcastManager getLocalBroadcastManager() {
        if (mLocalBroadcatManager == null) {
            mLocalBroadcatManager = LocalBroadcastManager.getInstance(mContext);
        }
        return mLocalBroadcatManager;
    }
}
