package com.kltz88.alldemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.edu.zafu.library.base.BaseActivity;
import cn.edu.zafu.library.core.CoreAnim;


public class MainActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openPage("test1", null, CoreAnim.none, true);
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* SwitchBean bean=new SwitchBean("test2",null, Anim.slide,true,true);
                bean.setRequestCode(100);
                BaseFragment fragment=getActiveFragment();

                openPageForResult(bean, getActiveFragment());*/
                Bundle b = new Bundle();
                b.putString("t", "111111");
                openPage("test1", b, CoreAnim.slide);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("Test from activity", requestCode + " " + resultCode + " ");
        super.onActivityResult(requestCode, resultCode, data);
    }
}
