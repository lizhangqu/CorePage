package cn.edu.zafu.corepage.core;

import android.support.v4.app.Fragment;

import java.util.List;

import cn.edu.zafu.corepage.base.BaseFragment;

/**
 * 页面跳转接口，用于控制页面跳转或启动新的activity
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 09:34
 */
public interface CoreSwitcher {
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
    Fragment gotoPage(CoreSwitchBean bean);

    /**
     * 打开一个新的页面
     *
     * @param bean
     * @return
     */
    Fragment openPage(CoreSwitchBean bean);

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
    public Fragment openPageForResult(final CoreSwitchBean page, final BaseFragment fragment);

}
