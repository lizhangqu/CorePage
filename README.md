Corepage is a page switch framework based on Fragment
====================================

[ ![Download](https://api.bintray.com/packages/lizhangqu/maven/corepage/images/download.svg) ](https://bintray.com/lizhangqu/maven/corepage/_latestVersion)  
A page switch framework based on Fragment, you can open a page use just a method.It can be used in both Activity andFragment if you make your custom Activity extends BaseActivity and your custom Fragment extends  BaseFragment.And use BaseApplication as your Application.

Changelog
---------

Current version 0.0.5 released on 21th August 2015

See details in [CHANGELOG](https://github.com/lizhangqu/CorePage/blob/master/CHANGELOG.md)


Notices
--------
 - All your Activity must **extends BaseActivity**,All your Fragment must **extends BaseFragment**,use **package.YourApplication** as your Application name and use **CoreConfig.init()** in the override method **onCreate()** to init.
 - Your entry Activity doesn't need  use setContentView method to set a view,all your views should be set in your Fragments.
 - Page jump based on method **openpage**,you can set an animation type ,weather add to back stack,open a new activity or not.
 - If you need the page return a result ,you should use **openPageForResult** to open page,then use **setFragmentResult** to set result and use **popToBack** to retuen. Override **onFragmentResult** method to get the return result.
 - All your fragment pages need configed in **assets/page.json**,**page name** and **class** is **required**,**page params** is **option**,the params will be used through **Bundle**
 - Full details and documentation can be found in the library project

Examples
--------

I have provided a sample .  
See samples [here on Github](https://github.com/lizhangqu/CorePage/tree/master/sample)  
To run Sample application, simply clone the repository and use android studio to compile,  install it on connected device  



Usage
-----


**Gradle**

```
dependencies {
  compile 'cn.edu.zafu:corepage:0.0.5'
}
```

**config in assets/page.json**

```
[
  {
    "name": "page1",
    "class": "cn.edu.zafu.corepage.sample.PageFragment1",
    "params": ""
  },
  {
    "name": "page2",
    "class": "cn.edu.zafu.corepage.sample.PageFragment2",
    "params": {
      "key1":"value1",
      "key2":"value2"
    }
  }
]
```

**init config**
```
public class YourApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CoreConfig.init(this);
    }
}
```

```
 <application
        android:name=".YourApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme" >
    </application>
```


**open a page**

```
openPage(pageName,bundle,coreAnim);
// CoreAnim.slide, CoreAnim.fade, CoreAnim.present, CoreAnim.none
openPage(pageName,bundle,coreAnim,isAddToBackStack);
openPage(pageName,bundle,coreAnim,isAddToBackStack,isNewActivity);
```

**open a page for result**

```
//call this in the opener fragment
openPageForResult(pageName,bundle,coreAnim,requestCode);

//call this in the opened fragment to set result and return
setFragmentResult(500,intent);
popToBack(); 

//Override this method in the opener fragment to get the result
public void onFragmentResult(int requestCode, int resultCode, Intent data) {
 super.onFragmentResult(requestCode, resultCode, data);
} 
```

**support OpenAtlas**

if you need to support OpenAtlas,before you call the method such as **openPage**，you need to set a bundle classloader
```
ClassLoader bundleClassLoader = Atlas.getInstance().getBundleClassLoader(bundlepkgName);
CoreConfig.setIsOpenAtlas(true);
CoreConfig.setBundleClassLoader(bundleClassLoader);
```


**custom animation**

if you need to support custom animation,you can use it this way
```
int anim[]= {R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right};
openPage("test1",null, anim);
```

**read extra page json**

```
private String pageJson="[" +
            "  {" +
            "    'name': 'test4'," +
            "    'class': 'cn.edu.zafu.corepage.sample.TestFragment4'," +
            "    'params': ''" +
            "  }]";
CoreConfig.readConfig(pageJson);
```

or init with the json,this will read both the **page.json** in directory assets and the String you given

```
private String pageJson="[" +
            "  {" +
            "    'name': 'test4'," +
            "    'class': 'cn.edu.zafu.corepage.sample.TestFragment4'," +
            "    'params': ''" +
            "  }]";
CoreConfig.init(this, pageJson);
```


## License

    Copyright 2015 ZhangQu Li

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

