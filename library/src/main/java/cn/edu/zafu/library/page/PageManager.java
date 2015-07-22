package cn.edu.zafu.library.page;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cn.edu.zafu.library.R;
import cn.edu.zafu.library.ui.BaseFragment;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 09:34
 */
public class PageManager {
    private static final String TAG = PageManager.class.getSimpleName();
    private Context mContext;
    private Map<String, Page> mPageMap = new HashMap<String, Page>();
    private volatile static PageManager mInstance = null;

    /**
     * 获得单例
     * @return
     */
    public static PageManager getInstance() {
        if (mInstance == null) {
            synchronized (PageManager.class) {
                if (mInstance == null) {
                    mInstance = new PageManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 构造函数私有化
     */
    private PageManager() {

    }

    /**
     * 初始化配置
     * @param context
     */
    public void init(Context context) {
        try {
            mContext=context.getApplicationContext();
            readConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增新页面
     * @param name
     * @param clazz
     * @param params
     * @return
     */
    public boolean putPage(String name,Class< ? extends BaseFragment> clazz,Map<String,String> params){
        if (TextUtils.isEmpty(name) || clazz==null) {
            Log.d(TAG, "page Name is null or pageClass is null");
            return false;
        }
        if (mPageMap.containsKey(name)){
            Log.d(TAG, "page has already put!");
            return false;
        }
        Page page=new Page(name,clazz.getName(),buildParams(params));
        Log.d(TAG,"put a page:"+name);
        return true;
    }



    /**
     * 从配置文件中读取page
     */

    private void readConfig() {
        String content=readFileFromAssets(mContext,"page.json");
        JSONArray jsonArray = JSON.parseArray(content);
        Iterator<Object> iterator=jsonArray.iterator();
        JSONObject jsonPage=null;
        String pageName = null;
        String pageClazz = null;
        String pageParams = null;
        while (iterator.hasNext()) {
            jsonPage = (JSONObject) iterator.next();
            pageName = jsonPage.getString("name");
            pageClazz = jsonPage.getString("class");
            pageParams=jsonPage.getString("params");
            if (TextUtils.isEmpty(pageName) || TextUtils.isEmpty(pageClazz)) {
                Log.d(TAG, "page Name is null or pageClass is null");
                return;
            }
            mPageMap.put(pageName,new Page(pageName,pageClazz,pageParams));
            Log.d(TAG,"put a page:"+pageName);
        }
        Log.d(TAG, "finished get pages,page size：" + mPageMap.size());
    }

    /**
     * 从assets目录下读取文件
     * @param context
     * @param fileName
     * @return
     */
    private String readFileFromAssets(Context context , String fileName) {
        String result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                result += line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据page，从pageParams中获得bundle
     * @param page
     * @return
     */
    private Bundle buildBundle(Page page){
        Bundle bundle=new Bundle();
        String key=null;
        Object value=null;
        if (page!=null&&page.getParams()!=null){
            JSONObject j= JSON.parseObject(page.getParams());
            if(j!=null){
                Set<String> keySet=j.keySet();
                if(keySet!=null){
                    Iterator<String> ite=keySet.iterator();
                    while (ite.hasNext()){
                        key=ite.next();
                        value=j.get(key);
                        bundle.putString(key,value.toString());
                    }
                }
            }
        }
        return bundle;
    }

    /**
     * 从hashMap中得到bundle
     * @param params
     * @return
     */
    private String buildParams(Map<String, String> params) {
        if (params==null) {
            return "";
        }
        String result = JSON.toJSONString(params);
        Log.d(TAG, "params:" + result);
        return result;
    }

    /**
     * 打开一个fragemnt
     * @param fragmentManager
     * @param pageName
     * @param bundle
     * @param animations
     * @param addToBackStack
     * @return
     */
    public Fragment openPageWithNewFragmentManager(FragmentManager fragmentManager,String pageName,Bundle bundle,int[] animations,boolean addToBackStack){
        BaseFragment fragment=null;
        try {
            Page page=this.mPageMap.get(pageName);
            if (page == null) {
                Log.d(TAG, "Page:" + pageName + " is null");
                return null;
            }
            fragment=(BaseFragment)Class.forName(page.getClazz()).newInstance();
            Bundle pageBundle=buildBundle(page);
            if (bundle!=null){
                pageBundle.putAll(bundle);
            }
            fragment.setArguments(pageBundle);
            fragment.setPageName(pageName);

            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            if (animations!=null){
                fragmentTransaction.setCustomAnimations(animations[0], animations[1], animations[2], animations[3]);
                Fragment fragmentContainer=fragmentManager.findFragmentById(R.id.fragment_container);
                if (fragmentContainer!=null){
                    fragmentTransaction.hide(fragmentContainer);
                }
            }

            fragmentTransaction.add(R.id.fragment_container, fragment, pageName);
            if (addToBackStack){
                fragmentTransaction.addToBackStack(pageName);
            }

            fragmentTransaction.commitAllowingStateLoss();
            //fragmentTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Fragment.error:" + e.getMessage());
            return null;
        }

        return  fragment;
    }

    /**
     * 打开一个Fragement
     * @param fragmentManager
     * @param pageName
     * @param bundle
     * @param animations
     * @return
     */
    public Fragment gotoPage(FragmentManager fragmentManager,String pageName,Bundle bundle,int [] animations){
        Fragment fragment=null;
        if (fragmentManager!=null){
            fragment=fragmentManager.findFragmentByTag(pageName);
        }
        if (fragment!=null){
            fragmentManager.popBackStackImmediate(pageName,0);
        }else {
            fragment=this.openPageWithNewFragmentManager(fragmentManager, pageName, bundle, animations, true);
        }
        return fragment;

    }

    /**
     * 判断fragment是否位于栈顶
     * @param context
     * @param fragmentTag
     * @return
     */
    public boolean isFragmentTop(Context context,String fragmentTag){
        //待实现
        return false;
    }
}
