package cn.edu.zafu.corepage.sample;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import cn.edu.zafu.corepage.core.CoreConfig;

/**
 * 全局Application基类，用于初始化Page
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 09:35
 */
public class BaseApplication extends Application {
    private String pageJson="[" +
            "  {" +
            "    'name': 'test4'," +
            "    'class': 'cn.edu.zafu.corepage.sample.TestFragment4'," +
            "    'params': ''" +
            "  }]";
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        CoreConfig.init(this, pageJson);
        refWatcher=LeakCanary.install(this);
        // or such as this
        //CoreConfig.init(this);
        //CoreConfig.readConfig(pageJson);
    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }


}
