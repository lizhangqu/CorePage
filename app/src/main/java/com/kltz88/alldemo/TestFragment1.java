package com.kltz88.alldemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.zafu.library.anim.Anim;
import cn.edu.zafu.library.ui.BaseFragment;


/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-20
 * Time: 16:29
 */
public class TestFragment1 extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_test1,container,false);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //openPage("test2",null, Anim.slide,true);
                openPageForResult("test2",null, Anim.slide,101);
            }
        });

        return view;
    }
    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        Log.d("Test", requestCode + " " + resultCode + " " + data.getStringExtra("test1"));
        super.onFragmentResult(requestCode, resultCode, data);
    }
}
