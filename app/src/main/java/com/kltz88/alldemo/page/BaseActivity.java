package com.kltz88.alldemo.page;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.kltz88.alldemo.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-20
 * Time: 15:47
 */
public class BaseActivity extends FragmentActivity implements PageSwitcher{
    private Handler mHandler=null;
    private static ArrayList<WeakReference<BaseActivity>> mCustomActivities = new ArrayList<WeakReference<BaseActivity>>();
    protected WeakReference<BaseActivity> mCurrentInstance = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        mHandler=new Handler(getMainLooper());
        setContentView(R.layout.activity_base);
        mCurrentInstance = new WeakReference<BaseActivity>(this);
        mCustomActivities.add(mCurrentInstance);
        init(intent);
    }

    private void init(Intent intent) {
        try {
            PageSwitchBean pageSwitchBean = intent.getParcelableExtra("PageSwitchBean");
            if(pageSwitchBean!=null){
                BaseFragment fragment=null;
                boolean addToBackStack=pageSwitchBean.isAddToBackStack();
                String pageName=pageSwitchBean.getPageName();
                Bundle bundle=pageSwitchBean.getBundle();
                fragment= (BaseFragment) PageManager.getInstance().openPageWithNewFragmentManager(getSupportFragmentManager(),pageName,bundle,addToBackStack);
                if (fragment!=null){
                    //待处理
                }else{
                    finish();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.d("BaseActivity", e.getMessage());
            finish();
        }
    }

    private boolean isMainThread(){
        return Thread.currentThread()==this.getMainLooper().getThread();
    }
    private void popOrFinishActivity(){
        if (this.isFinishing()){
            return ;
        }
        if(this.getSupportFragmentManager().getBackStackEntryCount()>1){
            if(isMainThread()){
                this.getSupportFragmentManager().popBackStackImmediate();
            }else{
                this.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager().popBackStackImmediate();
                    }
                });
            }
        }else{
            finish();
        }
    }
    @Override
    public void popPage() {
        popOrFinishActivity();
    }

    @Override
    public boolean findPage(String pageName) {
        int size=mCustomActivities.size();
        int j=size-1;
        boolean hasFound=false;
        for (;j>=0;j--){
            WeakReference<BaseActivity> ref=mCustomActivities.get(j);
            if(ref!=null){
                BaseActivity item=ref.get();
                if (item==null){
                    Log.d("BaseActivity","item is null");
                    continue;
                }
                FragmentManager fragmentManager=item.getSupportFragmentManager();
                int count=fragmentManager.getBackStackEntryCount();
                for(int i=count-1;i>=0;i--){
                    String name=fragmentManager.getBackStackEntryAt(i).getName();
                    if(name.equalsIgnoreCase(pageName)){
                        hasFound=true;
                        break;
                    }

                }
                if (hasFound){
                    break;
                }
            }
        }
        return hasFound;
    }

    @Override
    public Fragment gotoPage(PageSwitchBean pageSwitchBean) {
        if (pageSwitchBean==null){
            popOrFinishActivity();
        }else {
            //待处理
        }
        return null;
    }

    @Override
    public Fragment openPage(PageSwitchBean pageSwitchBean) {
        if (pageSwitchBean==null) {
            Log.d("BaseActivity", "pageswitchbean is null");
            return null;
        }
        boolean addToBackStack=pageSwitchBean.isAddToBackStack();
        boolean newActivity=pageSwitchBean.isNewActivity();
        Bundle bundle=pageSwitchBean.getBundle();
        if (newActivity){
            startActivity(pageSwitchBean);
            return null;
        }else{
            String pageName=pageSwitchBean.getPageName();
            return PageManager.getInstance().openPageWithNewFragmentManager(getSupportFragmentManager(),pageName,bundle,addToBackStack);
        }
    }
    public void startActivity(PageSwitchBean pageSwitchBean){
        try{
            Intent intent=new Intent(this,BaseActivity.class);
            intent.putExtra("PageSwitchBean",pageSwitchBean);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }catch (Exception e){
            e.printStackTrace();
            Log.d("BaseActivity",e.getMessage());
        }
    }
    @Override
    public Fragment openPage(String pageName, Bundle bundle, boolean newActivity, boolean addToBackStack) {
        PageSwitchBean bean=new PageSwitchBean(pageName,bundle,newActivity,addToBackStack);
        return openPage(bean);
    }

    @Override
    public boolean isFragmentTop(String fragmentTag) {
        int size=mCustomActivities.size();
        if(size>0){
            WeakReference<BaseActivity> ref=mCustomActivities.get(size-1);
            BaseActivity item=ref.get();
            if(item!=null&&item==this){
                FragmentManager manager=this.getSupportFragmentManager();
                if (manager!=null){
                    int count=manager.getBackStackEntryCount();
                    if (count>=1){
                        FragmentManager.BackStackEntry entry=manager.getBackStackEntryAt(count-1);
                        if (entry.getName().equalsIgnoreCase(fragmentTag)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void removeUselessFragement(ArrayList<String> list) {
        FragmentManager manager=getSupportFragmentManager();
        if (manager!=null) {
            for (String tag : list) {
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = manager.findFragmentByTag(tag);
                if (fragment != null) {
                    transaction.remove(fragment);
                }
                transaction.commitAllowingStateLoss();
            }
            int count = manager.getBackStackEntryCount();
            if (count == 0) {
                this.finish();
            }
        }

    }
}
