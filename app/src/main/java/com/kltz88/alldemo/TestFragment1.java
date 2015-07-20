package com.kltz88.alldemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kltz88.alldemo.page.BaseFragment;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-20
 * Time: 16:29
 */
public class TestFragment1 extends BaseFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_test1,container,false);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage(false,"test2",null,true);
            }
        });
        return view;
    }
}
