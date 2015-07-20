package com.kltz88.alldemo.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-20
 * Time: 16:39
 */
public interface PageSwitcher {
    void popPage();
    boolean findPage(String pageName);
    Fragment gotoPage(PageSwitchBean pageSwitchBean);
    Fragment openPage(PageSwitchBean pageSwitchBean);
    Fragment openPage(String pageName,Bundle bundle,boolean newActivity, boolean addToBackStack);
    boolean isFragmentTop(String fragmentTag);
    void removeUselessFragement(ArrayList<String> list);
}
