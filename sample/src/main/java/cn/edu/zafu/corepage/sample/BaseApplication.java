package cn.edu.zafu.corepage.sample;

import android.app.Application;

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

    @Override
    public void onCreate() {
        super.onCreate();
        CoreConfig.init(this, pageJson);
        // or such as this
        //CoreConfig.init(this);
        //CoreConfig.readConfig(pageJson);
    }




}
