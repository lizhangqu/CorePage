/**
 * Copyright 2015 ZhangQu Li
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.zafu.corepage.core;

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

import cn.edu.zafu.corepage.R;
import cn.edu.zafu.corepage.base.BaseFragment;

/**
 * 跳转页面管理
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 09:34
 */
public class CorePageManager {
    private static final String TAG = CorePageManager.class.getSimpleName();
    //日志TAG
    private volatile static CorePageManager mInstance = null;
    //单例
    private Context mContext;
    //Context上下文
    private Map<String, CorePage> mPageMap = new HashMap<String, CorePage>();
    //保存page的map

    /**
     * 构造函数私有化
     */
    private CorePageManager() {

    }

    /**
     * 获得单例
     *
     * @return PageManager 单例
     */
    public static CorePageManager getInstance() {
        if (mInstance == null) {
            synchronized (CorePageManager.class) {
                if (mInstance == null) {
                    mInstance = new CorePageManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化配置
     *
     * @param context 上下文
     */
    public void init(Context context) {
        try {
            mContext = context.getApplicationContext();

            String content = readFileFromAssets(mContext, "page.json");
            readConfig(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void init(Context context,String pageJson) {
        this.init(context);
        readConfig(pageJson);
    }
    /**
     * 从配置文件中读取page
     */

    public void readConfig(String content) {
        Log.d(TAG, "readConfig from json");
        JSONArray jsonArray = JSON.parseArray(content);
        Iterator<Object> iterator = jsonArray.iterator();
        JSONObject jsonPage = null;
        String pageName = null;
        String pageClazz = null;
        String pageParams = null;
        while (iterator.hasNext()) {
            jsonPage = (JSONObject) iterator.next();
            pageName = jsonPage.getString("name");
            pageClazz = jsonPage.getString("class");
            pageParams = jsonPage.getString("params");
            if (TextUtils.isEmpty(pageName) || TextUtils.isEmpty(pageClazz)) {
                Log.d(TAG, "page Name is null or pageClass is null");
                return;
            }
            mPageMap.put(pageName, new CorePage(pageName, pageClazz, pageParams));
            Log.d(TAG, "put a page:" + pageName);
        }
        Log.d(TAG, "finished read pages,page size：" + mPageMap.size());
    }

    /**
     * 从assets目录下读取文件
     *
     * @param context 上下文
     * @param fileName 文件名
     * @return
     */
    private String readFileFromAssets(Context context, String fileName) {
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
     * 新增新页面
     *
     * @param name 页面名
     * @param clazz 页面class
     * @param params 页面参数
     * @return 是否新增成功
     */
    public boolean putPage(String name, Class<? extends BaseFragment> clazz, Map<String, String> params) {
        if (TextUtils.isEmpty(name) || clazz == null) {
            Log.d(TAG, "page Name is null or pageClass is null");
            return false;
        }
        if (mPageMap.containsKey(name)) {
            Log.d(TAG, "page has already put!");
            return false;
        }
        CorePage corePage = new CorePage(name, clazz.getName(), buildParams(params));
        Log.d(TAG, "put a page:" + name);
        return true;
    }

    /**
     * 从hashMap中得到参数的json格式
     *
     * @param params 页面map形式参数
     * @return json格式参数
     */
    private String buildParams(Map<String, String> params) {
        if (params == null) {
            return "";
        }
        String result = JSON.toJSONString(params);
        Log.d(TAG, "params:" + result);
        return result;
    }

    /**
     * 页面跳转核心函数之一
     * 打开一个Fragement,如果返回栈中有则出栈，否则新建
     *
     * @param fragmentManager FragmentManager管理类
     * @param pageName 页面别名
     * @param bundle 参数
     * @param animations 动画
     * @return 成功跳转到的fragment
     */
    public Fragment gotoPage(FragmentManager fragmentManager, String pageName, Bundle bundle, int[] animations) {
        Log.d(TAG, "gotoPage:" + pageName);
        Fragment fragment = null;
        if (fragmentManager != null) {
            fragment = fragmentManager.findFragmentByTag(pageName);
        }
        if (fragment != null) {
            fragmentManager.popBackStackImmediate(pageName, 0);
        } else {
            fragment = this.openPageWithNewFragmentManager(fragmentManager, pageName, bundle, animations, true);
        }
        return fragment;

    }

    /**
     * 页面跳转核心函数之一
     * 打开一个fragemnt
     *
     * @param fragmentManager FragmentManager管理类
     * @param pageName  页面名
     * @param bundle 参数
     * @param animations 动画类型
     * @param addToBackStack 是否添加到返回栈
     * @return 打开的Fragment对象
     */
    public Fragment openPageWithNewFragmentManager(FragmentManager fragmentManager, String pageName, Bundle bundle, int[] animations, boolean addToBackStack) {
        BaseFragment fragment = null;
        try {
            CorePage corePage = this.mPageMap.get(pageName);
            if (corePage == null) {
                Log.d(TAG, "Page:" + pageName + " is null");
                return null;
            }
            /**
             * Atlas的支持 start
             */
            if (CoreConfig.isOpenAtlas()){
                ClassLoader bundleClassLoader = CoreConfig.getBundleClassLoader();
                if(bundleClassLoader==null){
                    Log.d(TAG, "OpenAtlas bundle ClassLoader is null!");
                    return null;
                }
                fragment = (BaseFragment) CoreConfig.getBundleClassLoader().loadClass(corePage.getClazz()).newInstance();
            }else{
                fragment = (BaseFragment) Class.forName(corePage.getClazz()).newInstance();
            }
            /**
             * Atlas的支持 end
             */

            Bundle pageBundle = buildBundle(corePage);
            if (bundle != null) {
                pageBundle.putAll(bundle);
            }
            fragment.setArguments(pageBundle);
            fragment.setPageName(pageName);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (animations != null && animations.length >= 4) {
                fragmentTransaction.setCustomAnimations(animations[0], animations[1], animations[2], animations[3]);
            }
            Fragment fragmentContainer = fragmentManager.findFragmentById(R.id.fragment_container);
            if (fragmentContainer != null) {
                fragmentTransaction.hide(fragmentContainer);
            }


            fragmentTransaction.add(R.id.fragment_container, fragment, pageName);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(pageName);
            }

            fragmentTransaction.commitAllowingStateLoss();
            //fragmentTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Fragment.error:" + e.getMessage());
            return null;
        }
        return fragment;
    }
    /**
     * 根据page，从pageParams中获得bundle
     *
     * @param corePage 页面
     * @return 页面的参数
     */
    private Bundle buildBundle(CorePage corePage) {
        Bundle bundle = new Bundle();
        String key = null;
        Object value = null;
        if (corePage != null && corePage.getParams() != null) {
            JSONObject j = JSON.parseObject(corePage.getParams());
            if (j != null) {
                Set<String> keySet = j.keySet();
                if (keySet != null) {
                    Iterator<String> ite = keySet.iterator();
                    while (ite.hasNext()) {
                        key = ite.next();
                        value = j.get(key);
                        bundle.putString(key, value.toString());
                    }
                }
            }
        }
        return bundle;
    }

    /**
     * 判断fragment是否位于栈顶
     *
     * @param context 上下文
     * @param fragmentTag fragment的tag
     * @return 是否是栈顶Fragment
     */
   /* public boolean isFragmentTop(Context context, String fragmentTag) {
        if (context != null && context instanceof Switcher) {
            return ((Switcher) context).isFragmentTop(fragmentTag);
        } else {
            BaseActivity topActivity = BaseActivity.getTopActivity();
            if (topActivity != null) {
                return topActivity.isFragmentTop(fragmentTag);
            } else {
                return false;
            }
        }
    }*/
}
