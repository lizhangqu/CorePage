package cn.edu.zafu.library.switcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import cn.edu.zafu.library.anim.Anim;
import cn.edu.zafu.library.ui.BaseFragment;

/**
 * 页面跳转接口，用于控制页面跳转或启动新的activity
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 09:34
 */
public interface Switcher {
    /**
     * 返回到某一个页面（只有一个fragment时会关闭Activityt）
     */
    void popPage();

    /**
     * fragmentTag 是否在当前顶上activity上的最顶上的fragment
     *
     * @param fragmentTag
     * @return
     */
    boolean isFragmentTop(String fragmentTag);


    /**
     * 是否查找到某个page
     *
     * @param pageName
     * @return
     */
    boolean findPage(final String pageName);

    /**
     * 跳转到某一个页面。
     *
     * @param bean
     * @return
     */
    Fragment gotoPage(SwitchBean bean);

    /**
     * 打开一个新的页面
     *
     * @param bean
     * @return
     */
    Fragment openPage(SwitchBean bean);

    /**
     * 打开一个新的页面
     *
     * @param pageName
     * @param bundle
     * @param anim
     * @param addToBackStack
     * @param newActivity
     * @return
     */
    Fragment openPage(String pageName, Bundle bundle, Anim anim, boolean addToBackStack, boolean newActivity);

    /**
     * 打开一个新的页面
     *
     * @param pageName
     * @param bundle
     * @param anim
     * @param addToBackStack
     * @param newActivity
     * @return
     */
    Fragment openPage(String pageName, Bundle bundle, int[] anim, boolean addToBackStack, boolean newActivity);


    /**
     * 打开一个新的页面,不开启新activity
     *
     * @param pageName
     * @param bundle
     * @param anim
     * @param addToBackStack
     * @return
     */
    Fragment openPage(String pageName, Bundle bundle, Anim anim, boolean addToBackStack);

    /**
     * 打开一个新的页面,不开启新activity
     *
     * @param pageName
     * @param bundle
     * @param anim
     * @param addToBackStack
     * @return
     */
    Fragment openPage(String pageName, Bundle bundle, int[] anim, boolean addToBackStack);


    /**
     * 打开一个新的页面,不开启新activity，加到返回栈
     *
     * @param pageName
     * @param bundle
     * @param anim
     * @return
     */
    Fragment openPage(String pageName, Bundle bundle, Anim anim);

    /**
     * 打开一个新的页面,不开启新activity，加到返回栈
     *
     * @param pageName
     * @param bundle
     * @param anim
     * @return
     */
    Fragment openPage(String pageName, Bundle bundle, int[] anim);

    /**
     * 移除当前Acitivity不需要的fragment
     *
     * @param fragmentLists
     */
    void removeUnlessFragment(List<String> fragmentLists);

    /**
     * 页面跳转，支持跨Activity进行传递数据
     *
     * @param page
     * @param fragment
     * @return
     */
    public Fragment openPageForResult(final SwitchBean page, final BaseFragment fragment);

}
