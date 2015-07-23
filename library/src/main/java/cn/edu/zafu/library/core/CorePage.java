package cn.edu.zafu.library.core;

import java.io.Serializable;

/**
 * assets/page.json 页面属性类
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 09:34
 */
public class CorePage implements Serializable {
    private static final long serialVersionUID = 3736359137726536495L;

    private String mName;
    //页面名
    private String mClazz;
    //页面class
    private String mParams;
    //传入参数，json object结构

    public CorePage(String name, String clazz, String params) {
        mName = name;
        mClazz = clazz;
        mParams = params;
    }

    public String getClazz() {
        return mClazz;
    }

    public void setClazz(String clazz) {
        mClazz = clazz;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getParams() {
        return mParams;
    }

    public void setParams(String params) {
        mParams = params;
    }

    @Override
    public String toString() {
        return "Page{" +
                "mName='" + mName + '\'' +
                ", mClazz='" + mClazz + '\'' +
                ", mParams='" + mParams + '\'' +
                '}';
    }
}
