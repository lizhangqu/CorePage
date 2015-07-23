CorePage 一个基于Fragment页面跳转的基础框架
===========================================
用法
----
 1. 所有Activity继承BaseActivity,所有Frgment继承BaseFragment，在manifest中声明Application为cn.edu.zafu.library.base.BaseApplication。
 2. Activity中无需调用setContentView函数设置布局，一切布局由fragment进行设置
 3. 页面跳转全都通过openpage函数进行跳转，可设置显示动画类型，是否添加到返回栈，是否新开activity
 4. 需要页面返回结果的调用openPageForResult进行页面跳转，处理完后调用setFragmentResult设置返回结果，并调用popToBack返回。在请求方重写onFragmentResult获得返回结果
 5. 页面配置文件位于assets/page.json内，页面名，class名为必须，params可选，通过bundle传递