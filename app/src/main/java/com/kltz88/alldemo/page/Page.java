package com.kltz88.alldemo.page;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-20
 * Time: 15:18
 */
public class Page{
    private String mName;
    private String mClazz;
    private String mParams;

    public Page(String name, String clazz, String params) {
        mName = name;
        mClazz = clazz;
        mParams = params;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getClazz() {
        return mClazz;
    }

    public void setClazz(String clazz) {
        mClazz = clazz;
    }

    public String getParams() {
        return mParams;
    }

    public void setParams(String params) {
        mParams = params;
    }
}
