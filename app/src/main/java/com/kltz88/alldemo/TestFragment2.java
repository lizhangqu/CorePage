package com.kltz88.alldemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.zafu.library.ui.BaseFragment;


/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-20
 * Time: 16:29
 */
public class TestFragment2 extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_test2, container, false);
        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //App.exitApp();
                //openPage("test1", null, Anim.slide, true);
                Intent in = new Intent();
                in.putExtra("test1", "========");
                in.putExtra("test2", "!!!!!!!!!!");
                in.putExtra("test3", "@@@@@@@@");

                getActivity().setResult(102, in);
                popToBack();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
