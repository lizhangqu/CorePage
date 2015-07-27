CorePage 一个基于Fragment页面跳转的基础框架
====================================

[ ![Download](https://api.bintray.com/packages/lizhangqu/maven/corepage/images/download.svg) ](https://bintray.com/lizhangqu/maven/corepage/_latestVersion)  
A page jump based Fragment, you can't open page use just a method.It can be used in both Activity andFragment if you make your custom Activity extends BaseActivity and your custom Fragment extends  BaseFragment.And use BaseApplication as your Application

Changelog
---------

Current version 0.01 released on 27th July 2015

https://github.com/loopj/android-async-http/blob/1.4.8/CHANGELOG.md


Features
--------
 - 所有你的Activity必须继承BaseActivity,所有Frgment必须继承BaseFragment，在Manifest中声明Application为cn.edu.zafu.library.base.BaseApplication。
 - 你的入口Activity中无需调用setContentView函数设置布局，一切布局由fragment进行设置
 - 页面跳转全都通过openpage函数进行跳转，可设置显示动画类型，是否添加到返回栈，是否新开activity
 - 需要页面返回结果的调用openPageForResult进行页面跳转，处理完后调用setFragmentResult设置返回结果，并调用popToBack返回。在请求方Fragment中重写onFragmentResult获得返回结果
 - 页面配置文件位于assets/page.json内，页面名，class名为必须，params可选，参数会通过bundle传递
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
  compile 'cn.edu.zafu:corepage:0.0.1'
}
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

