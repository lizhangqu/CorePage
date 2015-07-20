package com.kltz88.alldemo.page;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-20
 * Time: 15:46
 */
public class BaseFragment extends Fragment {
    private String mPageName;
    protected Activity mActivity;
    private PageSwitcher mPageSwitcher;
    public PageSwitcher getPageSwitcher() {
        synchronized (BaseFragment.this) {// 加强保护，保证pageSwitcher 不为null
            if (mPageSwitcher == null) {
                if (this.mActivity != null && this.mActivity instanceof PageSwitcher) {
                    mPageSwitcher = (PageSwitcher) this.mActivity;
                }
                /*if (mPageSwitcher == null) {
                    AppStubActivity topActivity = BaseActivity.getTopActivity();
                    if (topActivity != null && topActivity instanceof IPageSwitcher) {
                        mPageSwitcher = (IPageSwitcher) topActivity;
                    }
                }*/
            }
        }
        return mPageSwitcher;
    }

    public String getPageName() {
        return mPageName;
    }

    public void setPageName(String pageName) {
        mPageName = pageName;
    }

    public Fragment openPage(boolean newActivity,String pageName,Bundle bundle,boolean addToBackStack){
        if (pageName==null){
            Log.d("BaseFragement","pageName is null");
            return null;
        }
        PageSwitcher pageSwitcher=getPageSwitcher();
        if (pageSwitcher!=null){
            PageSwitchBean pageSwitchBean=new PageSwitchBean(pageName,bundle,newActivity,addToBackStack);
            return  pageSwitcher.openPage(pageSwitchBean);
        }else{
            Log.d("BaseFragment", "pageSwitcher is null");
            return null;
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mActivity = (BaseActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must be a BaseActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }
}
