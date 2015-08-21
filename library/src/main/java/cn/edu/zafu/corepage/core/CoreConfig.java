package cn.edu.zafu.corepage.core;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import cn.edu.zafu.corepage.base.BaseActivity;

/**
 * 全局配置类
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 12:24
 */
public class CoreConfig {
    /**
     * Atlas支持 start
     */
    private static boolean isOpenAtlas=false;
    private static ClassLoader mBundleClassLoader =null;

    public static boolean isOpenAtlas() {
        return isOpenAtlas;
    }

    public static void setIsOpenAtlas(boolean isOpenAtlasFlag) {
        isOpenAtlas = isOpenAtlasFlag;
    }

    public static ClassLoader getBundleClassLoader() {
        return mBundleClassLoader;
    }

    public static void setBundleClassLoader(ClassLoader classLoader) {
        mBundleClassLoader = classLoader;
    }
    /**
     * Atlas支持 end
     */
    public final static String ACTION_EXIT_APP = "cn.edu.zafu.library.exit";
    //本地广播退出

    private static LocalBroadcastManager mLocalBroadcatManager;
    private static Context mContext;
    private boolean hasInited=false;
    public static void init(Context context){
        mContext=context.getApplicationContext();
        CorePageManager.getInstance().init(mContext);
    }
    /**
     * 发送本地广播退出程序
     */
    public void exitApp() {
        Intent intent = new Intent();
        intent.setAction(CoreConfig.ACTION_EXIT_APP);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        getLocalBroadcastManager().sendBroadcast(intent);
        BaseActivity.unInit();
    }
    /**
     * 获得LocalBroadcastManager对象
     * @return LocalBroadcastManager对象
     */
    public static LocalBroadcastManager getLocalBroadcastManager() {
        if (mLocalBroadcatManager == null) {
            mLocalBroadcatManager = LocalBroadcastManager.getInstance(mContext);
        }
        return mLocalBroadcatManager;
    }
}
