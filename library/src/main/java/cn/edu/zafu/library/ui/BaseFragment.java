package cn.edu.zafu.library.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;

import cn.edu.zafu.library.anim.Anim;
import cn.edu.zafu.library.page.PageManager;
import cn.edu.zafu.library.switcher.SwitchBean;
import cn.edu.zafu.library.switcher.Switcher;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 09:33
 */
public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();
    protected Activity mActivity;
    //所在activity
    private String mPageName;
    //页面名
    private int mRequestCode;
    //用于startForResult
    private Switcher mPageSwitcher;
    //页面跳转接口
    public interface OnFragmentFinishListener {
        void onFragmentResult(int requestCode, int resultCode, Intent intent);
    }
    //openPageForResult接口，用于传递返回结果
    private OnFragmentFinishListener mFragmentFinishListener;

    /**
     * 设置该接口用于返回结果
     * @param listener
     */
    public void setFragmentFinishListener(OnFragmentFinishListener listener) {
        this.mFragmentFinishListener = listener;
    }

    /**
     * 设置openPageForResult打开的页面的返回结果
     * @param resultCode
     * @param intent
     */
    public void setFragmentResult(int resultCode, Intent intent) {
        if (mFragmentFinishListener != null) {
            mFragmentFinishListener.onFragmentResult(mRequestCode, resultCode, intent);
        }
    }

    /**
     * 得到requestCode
     * @return
     */
    public int getRequestCode() {
        return this.mRequestCode;
    }

    /**
     * 设置requestCode
     * @param code
     */
    public void setRequestCode(int code) {
        this.mRequestCode = code;
    }

    /**
     * 将Activity中onKeyDown在Fragment中实现，
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    /**
     * 数据设置，回调
     * @param bundle
     */
    public void onFragmentDataReset(Bundle bundle) {

    }
    /**
     * 弹出栈顶的Fragment。如果Activity中只有一个Fragemnt时，Acitivity也退出。
     */
    public void popToBack() {
        this.popToBack(null, null);
    }

    /**
     * 如果在fragment栈中找到，则跳转到该fragment中去，否则弹出栈顶
     * @param pageName
     * @param bundle
     */
    public final void popToBack(String pageName, Bundle bundle) {
        Switcher switcher = getSwitcher();
        if (switcher != null) {
            if (pageName == null) {
                switcher.popPage();
            } else {
                if (this.findPage(pageName)) {
                    SwitchBean page = new SwitchBean(pageName, bundle);
                    switcher.gotoPage(page);
                } else {
                    switcher.popPage();
                }
            }
        } else {
            Log.d(TAG, "pageSwitcher null");
        }
    }

    /**
     * 得到页面切换Switcher
     * @return
     */
    public Switcher getSwitcher() {
        synchronized (BaseFragment.this) {// 加强保护，保证pageSwitcher 不为null
            if (mPageSwitcher == null) {
                if (this.mActivity != null && this.mActivity instanceof Switcher) {
                    mPageSwitcher = (Switcher) this.mActivity;
                }
                if (mPageSwitcher == null) {
                    BaseActivity topActivity = BaseActivity.getTopActivity();
                    if (topActivity != null && topActivity instanceof Switcher) {
                        mPageSwitcher = (Switcher) topActivity;
                    }
                }
            }
        }
        return mPageSwitcher;
    }

    public void setSwitcher(Switcher pageSwitcher) {
        this.mPageSwitcher = pageSwitcher;
    }

    /**
     * 查找fragment是否存在
     * @param pageName
     * @return
     */
    public boolean findPage(String pageName) {
        if (pageName == null) {
            Log.d(TAG, "pageName is null");
            return false;
        }
        Switcher switcher = getSwitcher();
        if (switcher != null) {
            return switcher.findPage(pageName);
        } else {
            Log.d(TAG, "pageSwitch is null");
            return false;
        }

    }

    /**
     * 对应fragment是否位于栈顶
     * @param fragmentTag
     * @return
     */
    public boolean isFragmentTop(String fragmentTag) {
        Switcher pageSwitcher = this.getSwitcher();
        if (pageSwitcher != null) {
            return pageSwitcher.isFragmentTop(fragmentTag);

        } else {
            Log.d(TAG, "pageSwitcher is null");
            return false;
        }
    }

    /**
     * openPageWithNewFragmentManager
     * @param mFragmentManager
     * @param pageName
     * @param bundle
     * @param anim
     * @param addToBackStack
     * @return
     */
    public final Fragment openPageWithNewFragmentManager(FragmentManager mFragmentManager, String pageName, Bundle bundle, Anim anim, boolean addToBackStack) {
        return PageManager.getInstance().openPageWithNewFragmentManager(mFragmentManager, pageName, bundle, SwitchBean.convertAnimations(anim), addToBackStack);
    }

    /**
     * openPageWithNewFragmentManager
     * @param mFragmentManager
     * @param pageName
     * @param bundle
     * @param animations
     * @param addToBackStack
     * @return
     */
    public final Fragment openPageWithNewFragmentManager(FragmentManager mFragmentManager, String pageName, Bundle bundle, int[] animations, boolean addToBackStack) {
        return PageManager.getInstance().openPageWithNewFragmentManager(mFragmentManager, pageName, bundle, animations, addToBackStack);
    }

    /**
     * openPageForResultWithNewFragmentManager
     * @param mFragmentManager
     * @param pageName
     * @param bundle
     * @param anim
     * @param addToBackStack
     * @param requestCode
     * @return
     */
    public final Fragment openPageForResultWithNewFragmentManager(FragmentManager mFragmentManager, String pageName, Bundle bundle, Anim anim, boolean addToBackStack, int requestCode) {
        return this.openPageForResultWithNewFragmentManager(mFragmentManager, pageName, bundle, SwitchBean.convertAnimations(anim), addToBackStack, requestCode);
    }

    /**
     * openPageForResultWithNewFragmentManager
     * @param mFragmentManager
     * @param pageName
     * @param bundle
     * @param anim
     * @param addToBackStack
     * @param requestCode
     * @return
     */
    public final Fragment openPageForResultWithNewFragmentManager(FragmentManager mFragmentManager, String pageName, Bundle bundle, int[] anim, boolean addToBackStack, int requestCode) {
        BaseFragment frg = (BaseFragment) this.openPageWithNewFragmentManager(mFragmentManager, pageName, bundle, anim, addToBackStack);
        if (frg == null) {
            return frg;
        }
        final BaseFragment opener = this;
        frg.setRequestCode(requestCode);
        frg.setFragmentFinishListener(new OnFragmentFinishListener() {
            @Override
            public void onFragmentResult(int requestCode, int resultCode, Intent intent) {
                opener.onFragmentResult(requestCode, resultCode, intent);
            }
        });
        return frg;
    }

    /**
     * 重新该方法用于获得返回的数据
     * @param requestCode 请求码
     * @param resultCode 返回结果码
     * @param data 返回数据
     */
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
    }

    /**
     * 在当前activity中打开一个fragment，并添加到返回栈中
     * @param pageName Fragemnt 名，在page.json中配置。
     * @param bundle 页面跳转时传递的参数
     * @param anim  指定的动画理性 none/slide(左右平移)/present(由下向上)/fade(fade 动画)
     * @return
     */
    public final Fragment openPage(String pageName, Bundle bundle, Anim anim) {
        return this.openPage(pageName, bundle, SwitchBean.convertAnimations(anim), true);
    }

    /**
     * 在当前activity中打开一个fragment，并添加到返回栈中
     * @param pageName Fragemnt 名，在page.json中配置。
     * @param bundle 页面跳转时传递的参数
     * @param anim 指定的动画理性 none/slide(左右平移)/present(由下向上)/fade(fade 动画)
     * @return
     */
    public final Fragment openPage(String pageName, Bundle bundle, int[] anim) {
        return this.openPage(pageName, bundle, anim, true);
    }

    /**
     * 在当前activity中打开一个fragment，并设置是否添加到返回栈
     * @param pageName Fragemnt 名，在page.json中配置。
     * @param bundle 页面跳转时传递的参数
     * @param anim 指定的动画理性 none/slide(左右平移)/present(由下向上)/fade(fade 动画)
     * @param addToBackStack 是否添加到用户操作栈中
     * @return
     */
    public final Fragment openPage(String pageName, Bundle bundle, Anim anim, boolean addToBackStack) {
        return this.openPage(pageName, bundle, SwitchBean.convertAnimations(anim), addToBackStack);
    }
    /**
     * 在当前activity中打开一个fragment，并设置是否添加到返回栈
     * @param pageName Fragemnt 名，在page.json中配置。
     * @param bundle 页面跳转时传递的参数
     * @param anim 指定的动画理性 none/slide(左右平移)/present(由下向上)/fade(fade 动画)
     * @param addToBackStack 是否添加到用户操作栈中
     * @return
     */
    public final Fragment openPage(String pageName, Bundle bundle, int[] anim, boolean addToBackStack) {
        return this.openPage(pageName, bundle, anim, addToBackStack, false);
    }

    /**
     * 打开一个fragment并设置是否新开activity，设置是否添加返回栈
     * @param pageName Fragemnt 名，在page.json中配置。
     * @param bundle 页面跳转时传递的参数
     * @param anim 指定的动画理性 none/slide(左右平移)/present(由下向上)/fade(fade 动画)
     * @param addToBackStack  是否添加到用户操作栈中
     * @param newActivity 该页面是否新建一个Activity
     * @return
     */
    public final Fragment openPage(String pageName, Bundle bundle, int[] anim, boolean addToBackStack, boolean newActivity) {
        if (pageName == null) {
            Log.d(TAG, "pageName is null");
            return null;
        }
        Switcher switcher = this.getSwitcher();
        if (switcher != null) {
            SwitchBean page = new SwitchBean(pageName, bundle, anim, addToBackStack,newActivity);
            return switcher.openPage(page);
        } else {
            Log.d(TAG, "pageSwitcher is null");
            return null;
        }
    }


    /**
     * 打开一个fragment并设置是否新开activity，设置是否添加返回栈
     * @param pageName Fragemnt 名，在page.json中配置。
     * @param bundle 页面跳转时传递的参数
     * @param anim 指定的动画理性 none/slide(左右平移)/present(由下向上)/fade(fade 动画)
     * @param addToBackStack  是否添加到用户操作栈中
     * @param newActivity 该页面是否新建一个Activity
     * @return
     */
    public final Fragment openPage(String pageName, Bundle bundle, Anim anim, boolean addToBackStack, boolean newActivity) {
        return this.openPage(pageName, bundle, SwitchBean.convertAnimations(anim), addToBackStack, newActivity);
    }

    /**
     * 新建或跳转到一个页面（Fragment）。找不到pageName Fragment时，就新建Fragment。找到pageName
     * Fragment时,则弹出该Fragement到栈顶上的所有actvity和fragment
     *
     * @param pageName    Fragemnt 名，在在configure.zip 的pageContext.txt中配置。
     * @param bundle      页面跳转时传递的参数
     * @param anim        指定的动画理性 none/slide(左右平移)/present(由下向上)/fade(fade 动画)
     * @param newActivity 该页面是否新建一个Activity
     * @return
     */
    public Fragment gotoPage(String pageName, Bundle bundle, Anim anim, boolean newActivity) {
        Switcher pageSwitcher = this.getSwitcher();
        if (pageSwitcher != null) {
            SwitchBean page = new SwitchBean(pageName, bundle, anim, true, newActivity);
            return pageSwitcher.gotoPage(page);
        } else {

            Log.d(TAG, "pageSwitcher is null");
            return null;
        }
    }

    /**
     *
     * @param pageName
     * @param bundle
     * @param anim
     * @return
     */
    public Fragment gotoPage(String pageName, Bundle bundle, Anim anim) {
        return this.gotoPage(pageName, bundle,anim,false);

    }
    /**
     * 打开fragment并请求获得返回值
     * @param pageName
     * @param bundle
     * @param anim
     * @param requestCode 请求码
     * @return
     */
    public final Fragment openPageForResult(String pageName, Bundle bundle, Anim anim, int requestCode) {
        return this.openPageForResult(false, pageName, bundle, anim, requestCode);
    }

    /**
     * 打开fragment并请求获得返回值,并设置是否在新activity中打开
     * @param newActivity
     * @param pageName
     * @param bundle
     * @param anim
     * @param requestCode
     * @return
     */
    public final Fragment openPageForResult(boolean newActivity, String pageName, Bundle bundle, Anim anim, int requestCode) {

        Switcher pageSwitcher = this.getSwitcher();
        if (pageSwitcher != null) {
            SwitchBean page = new SwitchBean(pageName, bundle, anim, true, newActivity);
            page.setRequestCode(requestCode);

            return pageSwitcher.openPageForResult(page, this);
        } else {
            Log.d(TAG, "pageSwitcher is null");
            return null;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    //页面跳转接口
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getPageName() != null) {
            Log.d(TAG, "====Fragment.onCreate====" + getPageName());
        }
    }

    public String getPageName() {
        return mPageName;
    }

    public void setPageName(String pageName) {
        mPageName = pageName;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }


}
