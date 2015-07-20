package com.kltz88.alldemo.page;


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
import com.kltz88.alldemo.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-20
 * Time: 15:20
 */
public class PageManager {
    private volatile static PageManager mInstance;
    private boolean hasInited=false;
    private Context mContext;
    private Map<String, Page> mPageMap = new HashMap<String, Page>();
    private PageManager(){
    }

    public static PageManager getInstance(){
        if(mInstance==null){
            synchronized (PageManager.class){
                if(mInstance==null){
                    mInstance=new PageManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        if(hasInited)
            return;
        try {
            mContext=context.getApplicationContext();
            parseJson();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void parseJson() {
        try {
            //页面配置
            String content = readFileFromAssets(mContext, "page.json");

            JSONArray jsonArray = JSONArray.parseArray(content);
            Iterator<Object> iterator = jsonArray.iterator();
            JSONObject jsonPage = null;
            while (iterator.hasNext()) {
                jsonPage = (JSONObject) iterator.next();
                String pageName = jsonPage.getString("name");
                String clazz = jsonPage.getString("class");
                String params = jsonPage.getString("params");
                if (TextUtils.isEmpty(pageName) || TextUtils.isEmpty(clazz)) {
                    return;
                }

                mPageMap.put(pageName, new Page(pageName, clazz, params));
                Log.d("PageManager","put a page! name:"+pageName+ " class:"+clazz+" params:"+params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
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

    public Fragment openPageWithNewFragmentManager(FragmentManager manager,String pageName,Bundle bundle,boolean addToBackStack){
        BaseFragment fragment=null;
        try{
            Page page = this.mPageMap.get(pageName);
            if(page==null){
                Log.d("PageManager","Page:"+pageName+"is null.");
                return null;
            }
            fragment= (BaseFragment) Class.forName(page.getClazz()).newInstance();
            Bundle pageBundle=buildBundle(page);
            if (bundle!=null){
                pageBundle.putAll(bundle);
            }

            fragment.setArguments(pageBundle);
            fragment.setPageName(pageName);
            FragmentTransaction fragmentTransaction=manager.beginTransaction();

            //设置动画

            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left,R.anim.slide_in_left,R.anim.slide_out_right);
            Fragment fragmentOld=manager.findFragmentById(R.id.fragment_container);
            if(fragmentOld!=null){
                fragmentTransaction.hide(fragmentOld);
            }

            fragmentTransaction.add(R.id.fragment_container, fragment, page.getName());
            if (addToBackStack){
                fragmentTransaction.addToBackStack(page.getName());
            }
            fragmentTransaction.commitAllowingStateLoss();
        }catch(Exception e){
            e.printStackTrace();
            Log.d("PageManager", "Fragment error:" + e.getMessage());
        }
        return fragment;
    }

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
}
