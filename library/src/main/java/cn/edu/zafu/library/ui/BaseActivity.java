package cn.edu.zafu.library.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zafu.library.Config;
import cn.edu.zafu.library.R;
import cn.edu.zafu.library.anim.Anim;
import cn.edu.zafu.library.app.App;
import cn.edu.zafu.library.page.PageManager;
import cn.edu.zafu.library.switcher.SwitchBean;
import cn.edu.zafu.library.switcher.Switcher;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 09:32
 */
public class BaseActivity extends FragmentActivity implements Switcher{
    private static final String TAG=BaseActivity.class.getSimpleName();
    private Handler mHandler=null;
    private WeakReference<BaseActivity> mCurrentInstance=null;
    private static List<WeakReference<BaseActivity>> mActivities=new ArrayList<WeakReference<BaseActivity>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (null != savedInstanceState) {
            loadActivitySavedData(savedInstanceState);
        }
        mHandler=new Handler(getMainLooper());
        mCurrentInstance = new WeakReference<BaseActivity>(this);
        mActivities.add(mCurrentInstance);
        printAllActivities();

        // 接收程序退出广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.ACTION_EXIT_APP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        App.getLocalBroadcastManager().registerReceiver(mExitReceiver, filter);
    }
    /**
     * 仅用于接受应用退出广播，程序退出时有机会做一些必要的清理工作
     */
    private BroadcastReceiver mExitReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Config.ACTION_EXIT_APP)) {
                finish();
            }
        }
    };
    private void printAllActivities() {
        Log.d(TAG, "------------BaseActivity print all------------" + mActivities.size());
        for (WeakReference<BaseActivity> ref : mActivities) {
            if (ref != null) {
                BaseActivity item = ref.get();
                if (item != null) {
                    Log.d(TAG, item.toString());
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (this.getSupportFragmentManager().getBackStackEntryCount()==1){
            this.finishActivity(this, true);
        }else{
            super.onBackPressed();
        }
    }
    public BaseFragment getActiveFragment() {
        if (this.isFinishing()){
            return null;
        }
        FragmentManager manager=this.getSupportFragmentManager();
        if (manager!=null){
            int count=manager.getBackStackEntryCount();
            if (count>0){
                String tag=manager.getBackStackEntryAt(count-1).getName();
                return (BaseFragment)manager.findFragmentByTag(tag);
            }
        }
        return null;
    }
    public static BaseActivity getTopActivity() {
        if (mActivities != null) {
            int size = mActivities.size();
            if (size >= 1) {
                WeakReference<BaseActivity> ref = mActivities.get(size - 1);
                if (ref != null) {
                    return ref.get();
                }
            }
        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        BaseFragment activeFragment = getActiveFragment();
        boolean isHanlde=false;
        if (activeFragment!=null){
            isHanlde=activeFragment.onKeyDown(keyCode,event);
        }
        if (!isHanlde) {
            return super.onKeyDown(keyCode, event);
        } else {
            return isHanlde;
        }
    }
    protected String getPageName() {
        BaseFragment frg = getActiveFragment();
        if (frg != null) {
            return frg.getPageName();
        }
        return "";
    }

    private boolean isMainThread() {
        return Thread.currentThread() == this.getMainLooper().getThread();
    }
    private void popOrFinishActivity(){
        if (this.isFinishing()){
            return;
        }
        if (this.getSupportFragmentManager().getBackStackEntryCount()>1){
            if (isMainThread()){
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
            finishActivity(this,true);
        }

    }

    protected boolean popFragmentInActivity(final String pageName, Bundle bundle, BaseActivity findAcitivity) {
        if (pageName == null || findAcitivity == null || findAcitivity.isFinishing()) {
            return false;
        } else {
            final FragmentManager fragmentManager = findAcitivity.getSupportFragmentManager();
            if (fragmentManager != null) {
                Fragment frg = fragmentManager.findFragmentByTag(pageName);
                if (frg != null && frg instanceof BaseFragment) {
                    if (fragmentManager.getBackStackEntryCount() > 1 && mHandler != null) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fragmentManager.popBackStack(pageName, 0);
                            }
                        }, 100);
                    }
                    //((BaseActivity) frg).onFragmentDataReset(bundle);
                    //待实现
                    return true;
                }
            }
        }
        return false;
    }
    private void finishActivity(BaseActivity activity,boolean showAnimation) {
        if (activity!=null){
            activity.finish();
        }
        if (showAnimation){
            //动画，待实现
        }
    }

    @Override
    public boolean isFragmentTop(String fragmentTag) {
        int size=mActivities.size();
        if (size>0){
            WeakReference<BaseActivity> ref=mActivities.get(size-1);
            BaseActivity item=ref.get();
            if (item!=null&&item==this){
                FragmentActivity activity=item;
                FragmentManager manager=activity.getSupportFragmentManager();
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
    public void popPage() {
        popOrFinishActivity();

    }


    @Override
    public boolean findPage(String pageName) {
        int size=mActivities.size();
        int j=size-1;
        boolean hasFind=false;
        for (;j>=0;j--){
            WeakReference<BaseActivity> ref=mActivities.get(j);
            if (ref!=null){
                BaseActivity item=ref.get();
                if (item==null){
                    Log.d(TAG, "item is null");
                    continue;
                }
                FragmentManager manager=item.getSupportFragmentManager();
                int count=manager.getBackStackEntryCount();
                for (int i = count - 1; i >= 0; i--) {
                    String name = manager.getBackStackEntryAt(i).getName();
                    if (name.equalsIgnoreCase(pageName)){
                        hasFind=true;
                        break;
                    }
                }
                if (hasFind){
                    break;
                }
            }
        }
        return hasFind;
    }

    @Override
    public Fragment gotoPage(SwitchBean page) {
        if (page==null){
            Log.e(TAG, "page name empty");
            return null;
        }
        String pageName=page.getPageName();
        if (!findPage(pageName)){
            Log.d(TAG, "Be sure you have the right pageName" + pageName);
            return this.openPage(page);
        }

        int size=mActivities.size();
        int i=size-1;
        for (;i>=0;i--) {
            WeakReference<BaseActivity> ref = mActivities.get(i);
            if (ref!=null){
                BaseActivity item=ref.get();
                if (item==null){
                    Log.d(TAG,"item null");
                    continue;
                }

                boolean findInActivity=popFragmentInActivity(pageName,page.getBundle(),item);
                if (findInActivity){
                    break;
                }else{
                    item.finish();
                    // 找不到就弹出
                }
            }
        }
        return null;
    }



    @Override
    public Fragment openPage(SwitchBean page) {
        boolean addToBackStack=page.isAddToBackStack();
        boolean newActivity=page.isNewActivity();
        Bundle bundle=page.getBundle();
        int [] animations=page.getAnim();
        if (newActivity){
            startActivity(page);
            return null;
        }else{
            String pageName=page.getPageName();
            return PageManager.getInstance().openPageWithNewFragmentManager(getSupportFragmentManager(), pageName, bundle, animations, addToBackStack);
        }

    }
    public  void startActivity(SwitchBean page){
        try{
            Intent intent=new Intent(this,BaseActivity.class);
            intent.putExtra("SwitchBean",page);
            this.startActivity(intent);
            int[] animations=page.getAnim();
            if (animations!=null&&animations.length>=2){
                this.overridePendingTransition(animations[0], animations[1]);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }
    public void startActivityForResult(SwitchBean page) {
        try {
            Intent intent = new Intent(this, BaseActivity.class);
            intent.putExtra("SwitchBean", page);
            intent.putExtra("startActivityForResult", "true");
            this.startActivityForResult(intent, page.getRequestCode());

            int[] animations = page.getAnim();
            if (animations != null && animations.length >= 2) {
                this.overridePendingTransition(animations[0], animations[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void removeUnlessFragment(List<String> fragmentLists) {
        if (this.isFinishing()){
            return;
        }
        FragmentManager manager=getSupportFragmentManager();
        if (manager!=null){
            FragmentTransaction transaction=manager.beginTransaction();
            for (String tag:fragmentLists){
                Fragment fragment=manager.findFragmentByTag(tag);
                if (fragment!=null){
                    transaction.remove(fragment);
                }
            }
            transaction.commitAllowingStateLoss();
            int count=manager.getBackStackEntryCount();
            if (count==0){
                this.finish();
            }
        }
    }

    @Override
    public Fragment openPageForResult(SwitchBean page, BaseFragment fragment) {
        if (page!=null){
            if (page.isNewActivity()){

            }
        }
        return null;
    }

    @Override
    public Fragment openPage(String pageName, Bundle bundle, Anim anim, boolean addToBackStack, boolean newActivity) {
        SwitchBean page = new SwitchBean(pageName, bundle, anim, addToBackStack, newActivity);
        return openPage(page);
    }

    @Override
    public Fragment openPage(String pageName, Bundle bundle, int[] anim, boolean addToBackStack, boolean newActivity) {
        SwitchBean page = new SwitchBean(pageName, bundle, anim, addToBackStack, newActivity);
        return openPage(page);
    }

    @Override
    public Fragment openPage(String pageName, Bundle bundle, Anim anim, boolean addToBackStack) {
        SwitchBean page = new SwitchBean(pageName, bundle, anim, addToBackStack);
        return openPage(page);
    }

    @Override
    public Fragment openPage(String pageName, Bundle bundle, int[] anim, boolean addToBackStack) {
        SwitchBean page = new SwitchBean(pageName, bundle, anim, addToBackStack);
        return openPage(page);
    }

    @Override
    public Fragment openPage(String pageName, Bundle bundle, Anim anim) {
        SwitchBean page = new SwitchBean(pageName, bundle, anim);
        return openPage(page);
    }

    @Override
    public Fragment openPage(String pageName, Bundle bundle, int[] anim) {
        SwitchBean page = new SwitchBean(pageName, bundle, anim);
        return openPage(page);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface SaveWithActivity {
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Field[] fields = this.getClass().getDeclaredFields();
        Field.setAccessible(fields, true);
        Annotation[] ans;
        for (Field f : fields) {
            ans = f.getDeclaredAnnotations();
            for (Annotation an : ans) {
                if (an instanceof SaveWithActivity) {
                    try {
                        Object o = f.get(this);
                        if (o == null) {
                            continue;
                        }
                        String fieldName = f.getName();
                        if (o instanceof Integer) {
                            outState.putInt(fieldName, f.getInt(this));
                        } else if (o instanceof String) {
                            outState.putString(fieldName, (String) f.get(this));
                        } else if (o instanceof Long) {
                            outState.putLong(fieldName, f.getLong(this));
                        } else if (o instanceof Short) {
                            outState.putShort(fieldName, f.getShort(this));
                        } else if (o instanceof Boolean) {
                            outState.putBoolean(fieldName, f.getBoolean(this));
                        } else if (o instanceof Byte) {
                            outState.putByte(fieldName, f.getByte(this));
                        } else if (o instanceof Character) {
                            outState.putChar(fieldName, f.getChar(this));
                        } else if (o instanceof CharSequence) {
                            outState.putCharSequence(fieldName, (CharSequence) f.get(this));
                        } else if (o instanceof Float) {
                            outState.putFloat(fieldName, f.getFloat(this));
                        } else if (o instanceof Double) {
                            outState.putDouble(fieldName, f.getDouble(this));
                        } else if (o instanceof String[]) {
                            outState.putStringArray(fieldName, (String[]) f.get(this));
                        } else if (o instanceof Parcelable) {
                            outState.putParcelable(fieldName, (Parcelable) f.get(this));
                        } else if (o instanceof Serializable) {
                            outState.putSerializable(fieldName, (Serializable) f.get(this));
                        } else if (o instanceof Bundle) {
                            outState.putBundle(fieldName, (Bundle) f.get(this));
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        super.onSaveInstanceState(outState);
    }
    private void loadActivitySavedData(Bundle savedInstanceState) {
        Field[] fields = this.getClass().getDeclaredFields();
        Field.setAccessible(fields, true);
        Annotation[] ans;
        for (Field f : fields) {
            ans = f.getDeclaredAnnotations();
            for (Annotation an : ans) {
                if (an instanceof SaveWithActivity) {
                    try {
                        String fieldName = f.getName();
                        @SuppressWarnings("rawtypes")
                        Class cls = f.getType();
                        if (cls == int.class || cls == Integer.class) {
                            f.setInt(this, savedInstanceState.getInt(fieldName));
                        } else if (String.class.isAssignableFrom(cls)) {
                            f.set(this, savedInstanceState.getString(fieldName));
                        } else if (Serializable.class.isAssignableFrom(cls)) {
                            f.set(this, savedInstanceState.getSerializable(fieldName));
                        } else if (cls == long.class || cls == Long.class) {
                            f.setLong(this, savedInstanceState.getLong(fieldName));
                        } else if (cls == short.class || cls == Short.class) {
                            f.setShort(this, savedInstanceState.getShort(fieldName));
                        } else if (cls == boolean.class || cls == Boolean.class) {
                            f.setBoolean(this, savedInstanceState.getBoolean(fieldName));
                        } else if (cls == byte.class || cls == Byte.class) {
                            f.setByte(this, savedInstanceState.getByte(fieldName));
                        } else if (cls == char.class || cls == Character.class) {
                            f.setChar(this, savedInstanceState.getChar(fieldName));
                        } else if (CharSequence.class.isAssignableFrom(cls)) {
                            f.set(this, savedInstanceState.getCharSequence(fieldName));
                        } else if (cls == float.class || cls == Float.class) {
                            f.setFloat(this, savedInstanceState.getFloat(fieldName));
                        } else if (cls == double.class || cls == Double.class) {
                            f.setDouble(this, savedInstanceState.getDouble(fieldName));
                        } else if (String[].class.isAssignableFrom(cls)) {
                            f.set(this, savedInstanceState.getStringArray(fieldName));
                        } else if (Parcelable.class.isAssignableFrom(cls)) {
                            f.set(this, savedInstanceState.getParcelable(fieldName));
                        } else if (Bundle.class.isAssignableFrom(cls)) {
                            f.set(this, savedInstanceState.getBundle(fieldName));
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
